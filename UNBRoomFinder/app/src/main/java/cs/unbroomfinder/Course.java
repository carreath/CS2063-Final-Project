package cs.unbroomfinder;

/**
 * Created by kylebrideau on 2017-04-01.
 */

public class Course {

    private int _id;
    private String name;
    private int rmnumber;

    public Course() {}

    public Course(String name, int rmnumber) {
        this.name = name;
        this.rmnumber = rmnumber;
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
}
