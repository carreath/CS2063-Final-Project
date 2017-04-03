package cs.unbroomfinder.MapView;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;

import cs.unbroomfinder.R;

import static cs.unbroomfinder.MainActivity.DEBUG;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;
import static cs.unbroomfinder.MainActivity.map;
import static java.lang.Math.min;
import static java.lang.StrictMath.max;

public class BuildingMapActivity extends AppCompatActivity {
    public static final int PATH_RADIUS = 8;
    public static PriorityNode shortestPath = null;
    public static Point endNode = null;
    Button btnUp;
    Button btnDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_map);
        btnUp = (Button) findViewById(R.id.btnUp);
        btnDown = (Button) findViewById(R.id.btnDown);

        MyView view = new MyView(this);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.activity_building_map);
        layout.addView(view);
    }

    //TODO USE THESE METHODS TO SET MAP POINTS
    public static void setPath(int start, int end) {
        endNode = map.graph.getCoords(end);
        shortestPath = map.getShortestPath(start, end);
    }

    public static void setLast(int end) {
        endNode = map.graph.getCoords(end);
    }

    class MyView extends View implements View.OnTouchListener {
        private ScaleGestureDetector mScaleDetector;
        private float mScaleFactor = 0.5f;
        Bitmap mBitmap;
        int width, height, s_width, s_height;
        float x=0, y=0;
        int selectedImage = 2;
        int maxImage = 4;
        int[] images = {R.drawable.ic_head_hall_a, R.drawable.ic_head_hall_b, R.drawable.ic_head_hall_c, R.drawable.ic_head_hall_d, R.drawable.ic_head_hall_e};

        public MyView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            mBitmap = BitmapFactory.decodeResource(getResources(), images[selectedImage], options);

            height = mBitmap.getHeight();
            width = mBitmap.getWidth();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            s_width = size.x;
            s_height = size.y;


            btnUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    selectedImage = min(maxImage, selectedImage + 1);
                    mBitmap = BitmapFactory.decodeResource(getResources(), images[selectedImage], options);

                    invalidate();
                }
            });

            btnDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    selectedImage = max(0, selectedImage - 1);
                    mBitmap = BitmapFactory.decodeResource(getResources(), images[selectedImage], options);

                    invalidate();
                }
            });

            setOnTouchListener(this);

        }

        @Override
        public void onDraw(Canvas canvas) {
            if(selectedImage == 0) btnDown.setEnabled(false);
            else btnDown.setEnabled(true);
            if(selectedImage == maxImage) btnUp.setEnabled(false);
            else btnUp.setEnabled(true);

            super.onDraw(canvas);

            x = min(x, 0);
            y = min(y, 0);
            x = max(x, -mScaleFactor * width + s_width);

            canvas.save();
            canvas.translate(x , y);
            canvas.scale(mScaleFactor, mScaleFactor);

            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#3d87ff"));
            if(mBitmap != null) canvas.drawBitmap(mBitmap, 0,0, paint);

            if(shortestPath != null) {
                Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvasOverlay = new Canvas(bmOverlay);

                HashMap<Integer, GraphNode> currentFloor = map.graph.floors[selectedImage];

                PriorityNode current = shortestPath;
                while (current != null) {
                    if (current.prev == null) break;

                    if(currentFloor.containsKey(current.cur.id) && currentFloor.containsKey(current.prev.cur.id)) {
                        drawLine(canvas, current.cur.coords.x, current.cur.coords.y, current.prev.cur.coords.x, current.prev.cur.coords.y);
                    }
                    else {
                        drawLine(canvasOverlay, current.cur.coords.x, current.cur.coords.y, current.prev.cur.coords.x, current.prev.cur.coords.y);
                    }
                    current = current.prev;
                }

                paint.setColor(Color.parseColor("#50FFFFFF"));
                canvas.drawBitmap(bmOverlay, 0,0, paint);
            }
            if(endNode != null && 0 == 1) {
                canvas.drawCircle(endNode.x, endNode.y, PATH_RADIUS, paint);
            }

            canvas.restore();
        }

        public void drawLine(Canvas canvas, int x1, int y1, int x2, int y2) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setColor(Color.parseColor("#3d87ff"));

            paint.setStrokeWidth(2 * (PATH_RADIUS + 4));
            paint.setColor(Color.parseColor("#609dff"));
            canvas.drawLine(x1,y1,x2,y2, paint);

            paint.setStrokeWidth(2 * PATH_RADIUS);
            canvas.drawLine(x1,y1,x2,y2, paint);
        }

        float grabbedX = 0, grabbedY = 0;

        boolean scaled = false;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getPointerCount() == 2) {
                mScaleDetector.onTouchEvent(event);
                scaled = true;
                return true;
            }

            int action = MotionEventCompat.getActionMasked(event);

            switch (action) {
                case (MotionEvent.ACTION_DOWN):
                    if(DEBUG) Log.d(DEBUG_TAG, "Action was DOWN");

                    grabbedX = event.getX();
                    grabbedY = event.getY();
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    if(DEBUG) Log.d(DEBUG_TAG, "Action was MOVE " + event.getX() + " " + (event.getX() - grabbedX) + " " + event.getY() + " " + (event.getY() - grabbedY));

                    x = x + event.getX() - grabbedX;

                    y = y + event.getY() - grabbedY;
                    grabbedX = event.getX();
                    grabbedY = event.getY();
                    invalidate();
                    return true;
                case (MotionEvent.ACTION_UP):
                    if(DEBUG) Log.d(DEBUG_TAG, "Action was UP " + event.getX() + " " + (event.getX() - grabbedX) + " " + event.getY() + " " + (event.getY() - grabbedY));
                    if(!scaled) {
                        x = x + event.getX() - grabbedX;
                        y = y + event.getY() - grabbedY;
                    }
                    invalidate();
                    return true;
                case (MotionEvent.ACTION_CANCEL):
                    if(DEBUG) Log.d(DEBUG_TAG, "Action was CANCEL");
                    return true;
                case (MotionEvent.ACTION_OUTSIDE):
                    if(DEBUG) Log.d(DEBUG_TAG, "Movement occurred outside bounds " + "of current screen element");
                    return true;
                default:
                    return super.onTouchEvent(event);
            }
        }

        private class ScaleListener
                extends ScaleGestureDetector.SimpleOnScaleGestureListener {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();

                // Don't let the object get too small or too large.
                mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 5.0f));

                invalidate();
                return true;
            }
        }
    }
}
