package cs.unbroomfinder.MapView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import cs.unbroomfinder.R;

public class BuildingMapActivity extends AppCompatActivity implements View.OnTouchListener {
    public static final String DEBUG_TAG = "DEGUG";

    private int grabX, grabY, offsetX = 0, offsetY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_building_map);
        setContentView(new myview(this));

        /*
        ImageView mImageView;
        mImageView = (ImageView) findViewById(R.id.imageViewId);
        mImageView.setImageResource(R.drawable.ic_head_hall_c_1);

        BitmapFactory.Options dimensions = new BitmapFactory.Options();
        dimensions.inJustDecodeBounds = true;

        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_head_hall_c_1, dimensions);
        int height = dimensions.outHeight;
        int width = dimensions.outWidth;

        View myView = findViewById(R.id.activity_building_map);
        myView.setOnTouchListener(this);


        Log.i("FFFFFF", height + " " + width);*/
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    class myview extends View {
        Bitmap mBitmap;
        int width, height, s_width, s_height;
        public myview(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;

            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_head_hall_c_1, dimensions);
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_head_hall_c_1);

            height = dimensions.outHeight;
            width = dimensions.outWidth;

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            s_width = size.x;
            s_height = size.y;

            Matrix matrix = new Matrix();
            matrix.postRotate(-90);
            mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, width, height, matrix, true);

            Log.i("FFFFFF", height + " " + width + " " + s_height + " " + s_width);
        }

        private ScaleGestureDetector mScaleDetector;
        private float mScaleFactor = 1.f;

        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            // Let the ScaleGestureDetector inspect all events.
            mScaleDetector.onTouchEvent(ev);
            return true;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.save();
            canvas.translate((s_width / 2 - width / 2) , (s_height / 2 - height) );
            canvas.scale(mScaleFactor, mScaleFactor);

            Log.i("FFFFFF", mScaleFactor + "");

            int x = 80;
            int y = 80;
            int radius = 40;
            Paint paint = new Paint();
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#CD5C5C"));
            if(mBitmap != null) canvas.drawBitmap(mBitmap, 0,0, paint);


            canvas.restore();
        }

        private class ScaleListener
                extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();

                // Don't let the object get too small or too large.
               // mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

                invalidate();
                return true;
            }
        }
    }
}
