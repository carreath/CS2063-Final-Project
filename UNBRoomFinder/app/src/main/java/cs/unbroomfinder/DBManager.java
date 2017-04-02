package cs.unbroomfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by kylebrideau on 2017-04-01.
 */

public class DBManager extends SQLiteOpenHelper {

    // If you ever change anything about the database schema, you need to upgrade the version
    // number so that the database can update correctly.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "courses.db";

    public static final String TABLE_COURSE = "courses";
    public static final String TABLE_ROOM = "room";

    // Column Names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ROOM_NUMBER = "rmnumber";
    public static final String COLUMN_CURRENT_CLASS = "current_class";
    public static final String COLUMN_IMPORTED = "imported";

    // Constructor to create the DBManager
    public DBManager(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    // When we create the DB this will run and create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_COURSE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " CHARACTER, " +
                COLUMN_ROOM_NUMBER + " CHARACTER " +
                COLUMN_CURRENT_CLASS  + "TINYINT" +
                COLUMN_IMPORTED + "TINYINT" +
                ");";
        db.execSQL(query);

        //String query = "CREATE TABLE " + TABLE_ROOM + "(" +
    }

    // When we upgrade the version of the DB, we will need to do some housekeeping.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_COURSE);
        db.execSQL("DROP_TABLE_IF_EXISTS " + TABLE_ROOM);
        onCreate(db);
    }

    public void addCourse(Course course) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, course.getName());
        //values.put(COLUMN_ROOM_NUMBER, course.getRmNumber());
        values.put(COLUMN_CURRENT_CLASS, course.getCurrentClass());
        values.put(COLUMN_IMPORTED, course.getImported());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_COURSE, null, values);
        db.close();
    }

    public void deleteCourse(Course course) {

    }
}
