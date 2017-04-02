package cs.unbroomfinder.MapView;

import android.content.Context;
import android.content.res.AssetManager;
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
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import cs.unbroomfinder.R;

import static java.lang.Math.min;
import static java.lang.StrictMath.max;

public class BuildingMapActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = "DEGUG";
    public static final int PATH_RADIUS = 10;

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



    class myview extends View implements View.OnTouchListener {
        Bitmap mBitmap;
        int width, height, s_width, s_height;
        float x=0, y=0;
        Map map;
        Graph graph;
        private LinkedList<Integer[]> shortestPath;

        public myview(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());

            //map = new Map(context.getAssets().open("headhall.txt"));
            //AssetManager am = context.getAssets();
            //System.out.println(am == null);
            try {
            map = new Map(context.getAssets().open("headhall.txt"));
            //    InputStream is = am.open("headhall.txt");
            //    System.out.println(is == null);
            }
            catch(IOException e){}

            graph = map.graph;
            System.out.println();
            System.out.println();
            System.out.println();

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
            //matrix.postRotate(-90);
            mBitmap = Bitmap.createBitmap(mBitmap , 0, 0, width, height, matrix, true);
            setOnTouchListener(this);

            shortestPath = map.getShortestPath(0, 7);

            Log.i("FFFFFF", height + " " + width + " " + s_height + " " + s_width);
        }

        private ScaleGestureDetector mScaleDetector;
        private float mScaleFactor = 0.5f;

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            x = min(x, 0);
            y = min(y, 0);
            x = max(x, -mScaleFactor * width + s_width);
            Log.i("FFFFFFFF", mScaleFactor + " " + x + " " + y);

            canvas.save();
            canvas.translate(x , y);
            canvas.scale(mScaleFactor, mScaleFactor);

            int xc = 80;
            int yc = 80;
            int radius = 40;
            Paint paint = new Paint();
            // Use Color.parseColor to define HTML colors
            paint.setColor(Color.parseColor("#CD5C5C"));
            if(mBitmap != null) canvas.drawBitmap(mBitmap, 0,0, paint);

            paint.setStrokeWidth(2 * PATH_RADIUS);

            for(Integer[] arr: shortestPath) {
                if(arr[1] == -1) break;
                Point coord1 = graph.getCoords(arr[0]);
                Point coord2 = graph.getCoords(arr[1]);
                canvas.drawLine(coord1.x, coord1.y, coord2.x, coord2.y, paint);
            }

            /*
            int count = 0;
            for(int i=0; i<graph.nC; i++) {
                int[] neighbours = graph.getNeighbours(i);
                for(int j=0; j<neighbours.length; j++) {
                    Point coord1 = graph.getCoords(i);
                    Point coord2 = graph.getCoords(neighbours[j]);
                    canvas.drawLine(coord1.x, coord1.y, coord2.x, coord2.y, paint);
                    System.out.println(i + " " + graph.edges[count++]);
                }
            }
            for(int i=0; i<graph.getNumNodes(); i++) {
                Point coord = graph.getCoords(i);
                canvas.drawCircle(coord.x, coord.y, PATH_RADIUS, paint);
            }
            */

            canvas.restore();
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
                    Log.d(DEBUG_TAG, "Action was DOWN");
                    grabbedX = event.getX();
                    grabbedY = event.getY();
                    return true;
                case (MotionEvent.ACTION_MOVE):
                    Log.d(DEBUG_TAG, "Action was MOVE " + event.getX() + " " + (event.getX() - grabbedX) + " " + event.getY() + " " + (event.getY() - grabbedY));

                    x = x + event.getX() - grabbedX;

                    y = y + event.getY() - grabbedY;
                    grabbedX = event.getX();
                    grabbedY = event.getY();
                    invalidate();
                    return true;
                case (MotionEvent.ACTION_UP):
                    Log.d(DEBUG_TAG, "Action was UP " + event.getX() + " " + (event.getX() - grabbedX) + " " + event.getY() + " " + (event.getY() - grabbedY));
                    if(!scaled) {
                        x = x + event.getX() - grabbedX;
                        y = y + event.getY() - grabbedY;
                    }
                    invalidate();
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
