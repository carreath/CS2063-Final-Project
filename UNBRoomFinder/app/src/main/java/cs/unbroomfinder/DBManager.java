package cs.unbroomfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import static cs.unbroomfinder.MainActivity.DEBUG;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;

/**
 * Created by kylebrideau on 2017-04-01.
 */

public class DBManager extends SQLiteOpenHelper {
    Context context;
    private static DBManager sInstance;

    // If you ever change anything about the database schema, you need to upgrade the version
    // number so that the database can update correctly.
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "courses.db";

    public static final String TABLE_COURSE = "courses";
    public static final String TABLE_ROOM = "room";

    // Column Names for Course table
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_COURSE_NAME = "name";
    public static final String COLUMN_ROOM_NUMBER = "rmnumber";

    // Column Names for room table
    public static final String COLUMN_ROOM_NAME = "room_name";
    public static final String COLUMN_NODE_ID = "node_id";

    public static synchronized DBManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBManager(context, null);
        }
        return sInstance;
    }

    // Constructor to create the DBManager
    private  DBManager(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        this.context = context;
    }


    // When we create the DB this will run and create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_course = "CREATE TABLE " + TABLE_COURSE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_COURSE_NAME + " CHARACTER(40), " +
                COLUMN_ROOM_NUMBER + " INTEGER, " +
                " FOREIGN KEY ("+ COLUMN_ROOM_NUMBER +") REFERENCES " + TABLE_ROOM + "(" + COLUMN_ID + "));";
        db.execSQL(query_course);
        String query_room = "CREATE TABLE " + TABLE_ROOM + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_ROOM_NAME  + " CHARACTER(7)," +
                COLUMN_NODE_ID + " INTEGER); ";
        db.execSQL(query_room);

        populateRooms(db);
    }

    private void populateRooms(SQLiteDatabase db) {
        InputStream rooms = null;
        try {
            rooms = context.getAssets().open("room_numbers.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(rooms);

        String[] temp = null;
        String room_number;
        int node;
        while(sc.hasNextLine()) {
            temp = sc.nextLine().split(" ");

            room_number = temp[0];
            node = Integer.parseInt(temp[1]);

            db.execSQL("INSERT INTO " + TABLE_ROOM + " (" + COLUMN_ROOM_NAME + ", " + COLUMN_NODE_ID + ")" +
                    " VALUES ('" + room_number + "', " + node + ");");
            System.out.println("ADDED ROW");
        }
    }

    // When we upgrade the version of the DB, we will need to do some housekeeping.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_COURSE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_ROOM);
        onCreate(db);
    }

    // This function is used to add a course to the database
    public void addCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();
        System.out.println("ROOM: " + course.getRoomName());
        String[] column_values = {course.getRoomName()};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_ROOM + " WHERE " + COLUMN_ROOM_NAME + " = ?", column_values);

        cursor.moveToFirst();

        int node = 0;
        if(cursor != null && cursor.moveToFirst()) {
            node = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            if(DEBUG) Log.d(DEBUG_TAG, "NODE IS: " + node);
        } else {
            System.out.println("Cursor did not return anything");
        }
        ContentValues values = new ContentValues();
        values.put(COLUMN_COURSE_NAME, course.getCourseName());
        values.put(COLUMN_ROOM_NUMBER, node);


        long row = db.insert(TABLE_COURSE, null, values);
        System.out.println("ADDED COURSE: " + row);
        cursor.close();
    }

    public void deleteCourse(int course_id) {
        String query = "DELETE FROM " + TABLE_COURSE + " WHERE " + COLUMN_ID + " = " + course_id +  ";";

        System.out.println(query);
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }

    public LinkedList<Course> getAllClasses() {
        SQLiteDatabase db = getWritableDatabase();
        LinkedList<Course> courses = new LinkedList<Course>();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_COURSE, null);
        String course_name;
        int room_number;
        int _id;
        if(c != null && c.moveToFirst()) {
            // parse the courses
            do {
                course_name = c.getString(c.getColumnIndex(COLUMN_COURSE_NAME));
                room_number = c.getInt(c.getColumnIndex(COLUMN_ROOM_NUMBER));
                _id = c.getInt(c.getColumnIndex(COLUMN_ID));
                System.out.println("VALUE OF ID: " + _id);
                courses.add(new Course(_id, room_number, course_name, findRoomName(room_number)));
            } while(c.moveToNext());
        } else {
            System.out.println("NOT WORKING");
        }

        return courses;
    }

    public Course getCourse(int course_id) {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_COURSE + " WHERE " + COLUMN_ID + " = " + course_id + ";";

        Cursor c = db.rawQuery(query, null);
        Course course = null;

        if(c != null && c.moveToFirst()) {
            String course_name = c.getString(c.getColumnIndex(COLUMN_COURSE_NAME));
            int room_number = c.getInt(c.getColumnIndex(COLUMN_ROOM_NUMBER));
            String room_name = findRoomName(room_number);
            course = new Course(course_id, room_number, course_name, room_name);
        }

        return course;
    }

    // Function findRoomName will take in an id and output the String for the room
    public String findRoomName(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ROOM + " WHERE _id = " + id, null);

        String name = null;
        if(c != null && c.moveToFirst()) {
            name = c.getString(c.getColumnIndex(COLUMN_ROOM_NAME));
        }
        return name;
    }

    // Function findRoomNumber takes in the room name and returns the id of the room.
    public int findRoomNumber(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ROOM + " WHERE " + COLUMN_ROOM_NAME + " = '" + name + "'", null);

        int value = 0;
        if(c != null && c.moveToFirst()) {
            value = c.getInt(c.getColumnIndex(COLUMN_ID));
        }

        return value;
    }

    public int getNodeNumber(int room_id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_ROOM + " WHERE " + COLUMN_ID + " = " + room_id + ";", null);

        int node_id = -1;
        if(c != null && c.moveToFirst()) {
            node_id = c.getInt(c.getColumnIndex(COLUMN_NODE_ID));
        }
        return node_id;
    }

    public void updateCourse(String name, String room, int course_id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "UPDATE " + TABLE_COURSE + " SET " + COLUMN_COURSE_NAME + " = '" + name + "' , " +
                                COLUMN_ROOM_NUMBER + " = " + findRoomNumber(room) + " WHERE " + COLUMN_ID +
                                " = " + course_id + ";";
        System.out.println(query);
        db.execSQL(query);
    }
}
