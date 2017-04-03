package cs.unbroomfinder.MapView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;

import cs.unbroomfinder.R;

import static cs.unbroomfinder.MainActivity.DEBUG;
import static cs.unbroomfinder.MainActivity.DEBUG_TAG;

public class MapsFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_view_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);

        if(DEBUG) Log.d(DEBUG_TAG, "DONE LOADING MAPS FRAGMENT");

        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;

        LatLng UNB = new LatLng(45.949783, -66.641719);
        mMap.addMarker(new MarkerOptions().position(UNB).title("Head Hall"));
        mMap.setOnMarkerClickListener(this);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(UNB).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public boolean onMarkerClick(Marker target) {
        if(DEBUG) Log.d(DEBUG_TAG, target.getId() + " " + target.getTitle());

        Intent intent = new Intent(getActivity(), BuildingMapActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Toast.makeText(MapsFragment.this,"No Application available to viewPDF", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}