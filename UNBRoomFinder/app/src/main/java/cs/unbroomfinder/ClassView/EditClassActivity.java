package cs.unbroomfinder.ClassView;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import cs.unbroomfinder.Course;
import cs.unbroomfinder.DBManager;
import cs.unbroomfinder.R;

public class EditClassActivity extends AppCompatActivity {

    Spinner spinner;
    EditText course_name;
    Button btn_edit;
    Button btn_goback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        spinner = (Spinner) findViewById(R.id.spinner_edit);
        course_name = (EditText) findViewById(R.id.text_coursename);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_goback = (Button) findViewById(R.id.btn_goback);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.room_numbers, android.R.layout.simple_spinner_item);
        // Apply the adapter to the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        int course_id = getIntent().getIntExtra(SettingActivity.COURSE_ID, 0);
        DBManager db = DBManager.getInstance(getApplicationContext());
        final Course course = db.getCourse(course_id);

        spinner.setSelection(getIndex(spinner, course.getRoomName()));
        course_name.setText(course.getCourseName());

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager db = DBManager.getInstance(getApplicationContext());
                String new_room = spinner.getSelectedItem().toString();
                String new_name = course_name.getText().toString();
                db.updateCourse(new_name, new_room, course.getID());
                finish();
            }
        });

        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
}
