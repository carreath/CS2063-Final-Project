package cs.unbroomfinder.ClassView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import cs.unbroomfinder.Course;
import cs.unbroomfinder.DBManager;
import cs.unbroomfinder.MainActivity;
import cs.unbroomfinder.MapView.BuildingMapActivity;
import cs.unbroomfinder.R;

public class AddClassActivity extends AppCompatActivity {
    Button btn_cancel;
    Button btn_addClass;
    EditText class_name;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_addClass = (Button) findViewById(R.id.btn_ok);
        btn_addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addClass();
            }
        });

        spinner = (Spinner) findViewById(R.id.spin_room_number);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.room_numbers, android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    // Add class is used to manually add a class
    // for the class object you will need
    // 1. Class Name (String)
    // 2. Class Room Number (String)
    private void addClass() {
        class_name = (EditText) findViewById(R.id.input_course_name);
        Spinner mySpinner=(Spinner) findViewById(R.id.spin_room_number);
        String room = mySpinner.getSelectedItem().toString();

        String name = class_name.getText().toString();
        //String room = class_room.getText().toString();

        // Create a course object
        Course new_course = new Course();
        new_course.setCourseName(name);
        new_course.setRoomName(room);

        DBManager db = DBManager.getInstance(this);
        db.addCourse(new_course);
        db.close();

        onBackPressed();
    }
}
