package cs.unbroomfinder.ClassView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs.unbroomfinder.DBManager;
import cs.unbroomfinder.MapView.BuildingMapActivity;
import cs.unbroomfinder.R;

import static cs.unbroomfinder.MainActivity.DEBUG;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;

public class SettingActivity extends AppCompatActivity {
    public static final String COURSE_NAME = "courseName";
    // TODO: Fix this
    public static final String ROOM_NUMBER = "testing";
    public static final String ROOM_NAME = "room_name";

    Button btn_goto;
    Button btn_edit;
    Button btn_delete;
    Button btn_goback;
    TextView text_course_name;
    TextView text_room_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();

        // TODO add listeners
        // TODO Add cancel button

        final String courseName = intent.getStringExtra(COURSE_NAME);
        final String courseRoom = intent.getStringExtra(ROOM_NAME);
        final String courseRoomNum = intent.getStringExtra(ROOM_NUMBER);

        if(DEBUG) Log.d(DEBUG_TAG, "COURSE NAME: " + courseName);
        if(DEBUG) Log.d(DEBUG_TAG, "COURSE ROOM: " + courseRoom);

        text_course_name = (TextView) findViewById(R.id.text_class_name);
        text_room_name = (TextView) findViewById(R.id.text_roomnumber);

        text_course_name.setText(courseName);
        text_room_name.setText(courseRoom);

        btn_goto = (Button) findViewById(R.id.btn_goto);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_goback = (Button) findViewById(R.id.btn_goback);

        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildingMapActivity.setPath(0, Integer.parseInt(courseRoom));
                Intent intent = new Intent(SettingActivity.this, BuildingMapActivity.class);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Toast.makeText(MapsFragment.this,"No Application available to viewPDF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager db = DBManager.getInstance(getApplicationContext());
                db.deleteCourse(courseName, courseRoomNum);
                onBackPressed();
            }
        });

    }
}
