package cs.unbroomfinder;

/**
 * Created by kylebrideau on 2017-04-01.
 */

public class Course {

    private int _id;
    private String name;
    private Room rmnumber;
    private int current_class;
    private int imported;

    public Course() {}

    public Course(String name, Room rmnumber, int current_class, int imported) {
        this.name = name;
        this.rmnumber = rmnumber;
        this.current_class = current_class;
        this.imported = imported;
    }

    // Getters and setters

    public void setName(String name) {
        this.name = name;
    }

    public void setRmnumber(Room rmnumber) {
        this.rmnumber = rmnumber;
    }

    public void setCurrentClass(int isCurrent) {
        current_class = isCurrent;
    }

    public void setImported(int imported) {
        this.imported = imported;
    }

    public int getID() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public Room getRmNumber() {
        return rmnumber;
    }

    public int getCurrentClass() {
        return current_class;
    }

    public int getImported() {
        return imported;
    }
}
