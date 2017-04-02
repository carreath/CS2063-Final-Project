package cs.unbroomfinder.ClassView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs.unbroomfinder.Course;
import cs.unbroomfinder.DBManager;
import cs.unbroomfinder.R;

public class AddClassActivity extends AppCompatActivity {

    Button btn_cancel;
    Button btn_addClass;
    EditText class_name;
    EditText class_room;

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
    }

    // Add class is used to manually add a class
    // for the class object you will need
    // 1. Class Name (String)
    // 2. Class Room Number (String)
    // 3. Flag Current Class (1 or 0)
    // 4. Imported Class (1 or 0) - Would be 0 in this case.
    private void addClass() {
        class_name = (EditText) findViewById(R.id.input_course_name);
        class_room = (EditText) findViewById(R.id.input_room_number);

        String name = class_name.getText().toString();
        String room = class_room.getText().toString();

        // Create a course object
        Course new_course = new Course();
        new_course.setName(name);
        //new_course.setRmnumber(room);
        new_course.setImported(0); // Since it is not part of the import
        new_course.setCurrentClass(1); // Since the user specifically added it themselves

        DBManager db = new DBManager(this, null);
        db.addCourse(new_course);
        db.close();

        onBackPressed();
    }
}
