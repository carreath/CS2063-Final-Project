package cs.unbroomfinder.ClassView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.LinkedList;

import cs.unbroomfinder.MapView.BuildingMapActivity;
import cs.unbroomfinder.MapView.Map;
import cs.unbroomfinder.MapView.PriorityNode;
import cs.unbroomfinder.R;

import static cs.unbroomfinder.MainActivity.DEBUG;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;
import static cs.unbroomfinder.MainActivity.map;

public class CourseSettingActivity extends AppCompatActivity {
    public static final String COURSE_NAME = "courseName";
    // TODO: Fix this
    public static final String ROOM_NUMBER = "testing";

    Button btn_goto;
    Button btn_edit;
    Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        // TODO add listeners
        // TODO Add cancel button

        String courseName = intent.getStringExtra(COURSE_NAME);
        final String courseRoom = intent.getStringExtra(ROOM_NUMBER);
        if(DEBUG) Log.d(DEBUG_TAG, "COURSE NAME: " + courseName);
        if(DEBUG) Log.d(DEBUG_TAG, "COURSE ROOM: " + courseRoom);

        btn_goto = (Button) findViewById(R.id.btn_goto);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_delete = (Button) findViewById(R.id.btn_delete);

        btn_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuildingMapActivity.setPath(0, Integer.parseInt(courseRoom));
                Intent intent = new Intent(CourseSettingActivity.this, BuildingMapActivity.class);
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    // Toast.makeText(MapsFragment.this,"No Application available to viewPDF", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
