package cs.unbroomfinder;

import android.content.Context;

/**
 * Created by kylebrideau on 2017-04-01.
 */

public class Course {

    private int _id;
    private int room_number;
    private String course_name;
    private String room_name;

    // Constructor that takes in all the information and stores it into private variables
    public Course(int _id, int room_number, String course_name, String room_name) {
        this._id = _id;
        this.room_number = room_number;
        this.course_name = course_name;
        this.room_name = room_name;
    }

    public Course() {}

    public void setRoomNumber(int room_number) {
        this.room_number = room_number;
    }

    public void setCourseName(String course_name) {
        this.course_name = course_name;
    }

    public void setRoomName(String room_name) {
        this.room_name = room_name;
    }

    public int getID() {
        return _id;
    }

    public int getRoomNumber() {return room_number; }

    public String getCourseName() { return course_name; }

    public String getRoomName() { return room_name; }
}
