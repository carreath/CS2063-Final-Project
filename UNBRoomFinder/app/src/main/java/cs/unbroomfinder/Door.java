package cs.unbroomfinder;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;

/**
 * Created by carre on 2017-04-03.
 */

public class Door {
    public Door(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
/*
        LocationListener locationListener = new MyLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        } catch(Exception e) {
            e.printStackTrace();
        }
*/    }
}
