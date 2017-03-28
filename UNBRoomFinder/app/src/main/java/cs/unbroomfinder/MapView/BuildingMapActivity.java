package cs.unbroomfinder.MapView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import cs.unbroomfinder.R;

public class BuildingMapActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_building_map);

            ImageView mImageView;
            mImageView = (ImageView) findViewById(R.id.imageViewId);
            mImageView.setImageResource(R.drawable.ic_head_hall_c_1);

            BitmapFactory.Options dimensions = new BitmapFactory.Options();
            dimensions.inJustDecodeBounds = true;

            Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_head_hall_c_1, dimensions);
            int height = dimensions.outHeight;
            int width =  dimensions.outWidth;

            Log.i("FFFFFF", height + " " + width);
        }
    }
