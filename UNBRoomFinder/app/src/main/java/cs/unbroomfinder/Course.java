package cs.unbroomfinder;

import android.content.Context;

/**
 * Created by kylebrideau on 2017-04-01.
 */

public class Course {

    private int _id;
    private String name;
    private int rmnumber;
    private String room_name;

    public Course() {}

    public Course(String name, int rmnumber, Context context) {
        this.name = name;
        this.rmnumber = rmnumber;

        DBManager db = DBManager.getInstance(context);
        room_name = db.findRoomName(rmnumber);
    }

    // Getters and setters

    public void setName(String name) {
        this.name = name;
    }

    public void setRmnumber(int rmnumber) {
        this.rmnumber = rmnumber;
    }

    public int getID() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public int getRmNumber() {
        return rmnumber;
    }

    public String getRoomName() { return room_name; }
}
