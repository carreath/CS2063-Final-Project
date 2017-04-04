package cs.unbroomfinder;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOError;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import cs.unbroomfinder.MapView.GraphNode;
import cs.unbroomfinder.MapView.PriorityNode;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.Context.LOCATION_SERVICE;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;

/**
 * Created by carre on 2017-04-03.
 */

public class Door {
    Context context;
    LinkedList<Double[]> doors = new LinkedList<Double[]>();

    public Door(Context context) {
        this.context = context;

        Scanner sc = null;
        try {
            sc = new Scanner(context.getAssets().open("doors.txt"));
        }catch(IOException e) {
            e.printStackTrace();
        }

        while (sc.hasNext()) {
            Double[] node = new Double[3];
            node[0] = sc.nextDouble();
            node[1] = sc.nextDouble();
            node[2] = sc.nextDouble();
            doors.add(node);
        }
    }
    public int getNearestDoor() {
        LatLng location = getLocation();

        double smallestDistance = 1000;
        int index = -1;
        for(Double[] door : doors) {
            double latDif = location.latitude - door[1];
            double longDif = location.latitude - door[1];

            double distance = Math.pow(longDif, 2) + Math.pow(latDif, 2);
            if(distance < smallestDistance) {
                smallestDistance = distance;
                index = (int)(double) door[0];
            }
        }

        return index;
    }
    public LatLng getLocation()
    {
        // Get the location manager
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        Log.d(DEBUG_TAG, (locationManager == null) + "");
        Location location = null;

        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        Log.i("ISENABLED", isGPSEnabled + "");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context.getApplicationContext(), ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context.getApplicationContext(), ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                Log.i("WRONG API VERSION", "Permissions granted");
            } else {

                Log.i("WRONG API VERSION", "API VERSION ABOVE 23");
                return new LatLng(45.949790, -66.641709);
            }
        }
        else
        {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                Log.i("Provider Available", "FFFFF");
            }
            else {
                Log.i("Provider Unavailable", "FFFFF");
                return new LatLng(45.949790, -66.641709);
            }
        }

        Double lat,lon;
        try {
            lat = location.getLatitude ();
            lon = location.getLongitude ();
            return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            return null;
        }
    }
}
