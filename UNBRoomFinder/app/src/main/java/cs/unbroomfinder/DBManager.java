package cs.unbroomfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by kylebrideau on 2017-04-01.
 */

public class DBManager extends SQLiteOpenHelper {
    Context context;

    // If you ever change anything about the database schema, you need to upgrade the version
    // number so that the database can update correctly.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courses.db";

    public static final String TABLE_COURSE = "courses";
    public static final String TABLE_ROOM = "room";

    // Column Names for Course table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ROOM_NUMBER = "rmnumber";

    // Column Names for room table
    public static final String COLUMN_ROOM_NAME = "room_name";
    public static final String COLUMN_NODE_ID = "node_id";

    // Constructor to create the DBManager
    public DBManager(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }


    // When we create the DB this will run and create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_course = "CREATE TABLE " + TABLE_COURSE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " CHARACTER(40), " +
                COLUMN_ROOM_NUMBER + " INTEGER, " +
                " FOREIGN KEY ("+ COLUMN_ROOM_NUMBER +") REFERENCES " + TABLE_ROOM + "(" + COLUMN_ID + "));";
        db.execSQL(query_course);
        String query_room = "CREATE TABLE " + TABLE_ROOM + "(" +
                COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ROOM_NAME  + "CHARACTER(7)," +
                COLUMN_NODE_ID + " INTEGER; ";
        db.execSQL(query_room);

        populateRooms();
    }

    private void populateRooms() {
        InputStream rooms = null;
        try {
            rooms = context.getAssets().open("room_numbers.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(rooms);

        String[] temp = null;
        String room_number;
        String node;
        while(sc.hasNextLine()) {
            temp = sc.nextLine().split(",");

            room_number = temp[0];
            node = temp[1];

            addRoom(room_number, node);
        }
    }

    private void addRoom(String room_number, String node) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ROOM_NAME, room_number);
        values.put(COLUMN_NODE_ID, Integer.parseInt(node));
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_ROOM, null, values);
    }

    // When we upgrade the version of the DB, we will need to do some housekeeping.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_COURSE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_ROOM);
        onCreate(db);
    }

    public void addCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();

        String[] return_columns = {COLUMN_ID};
        String[] column_values = {course.getRmNumber() + ""};
        Cursor cursor = db.query(TABLE_ROOM,
                                return_columns,
                                COLUMN_ROOM_NUMBER + "= ?",
                                column_values,
                                null,
                                null,
                                COLUMN_NAME + " DESC");

        int room_number = cursor.getInt(0);
        System.out.println("ROOM VALUE IS: " + room_number);

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, course.getName());
        values.put(COLUMN_ROOM_NUMBER, room_number);


        db.insert(TABLE_COURSE, null, values);
        db.close();
    }

    // TODO: implement removing a course
    public void deleteCourse(Course course) {

    }
}
