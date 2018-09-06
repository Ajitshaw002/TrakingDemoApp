package com.example.ajit_wgt.trackingsystemdemo;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String email;
    private DatabaseReference location;
    private Double lat, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //reference to firebase
        location = FirebaseDatabase.getInstance().getReference("Locations");

        //getIntent data
        if (getIntent() != null) {
            email = getIntent().getStringExtra("email");
            lat = getIntent().getDoubleExtra("lat", 0);
            lng = getIntent().getDoubleExtra("lng", 0);

        }
        if (!TextUtils.isEmpty(email)) {
            loadUsersLocation(email);
        }

    }

    private void loadUsersLocation(final String email) {
        Query user_location = location.orderByChild("email").equalTo(email);
        user_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Tracking tracking = dataSnapshot1.getValue(Tracking.class);
                    //add marker to other user location
                    LatLng friendLatlng = new LatLng(Double.parseDouble(tracking.getLat()), Double.parseDouble(tracking.getLng()));

                    //create location for user coordinates
                    Location currentUserLocation = new Location("");
                    currentUserLocation.setLatitude(lat);
                    currentUserLocation.setLongitude(lng);

                    //create location for friend cordinate
                    Location friendLocation = new Location("");
                    friendLocation.setLatitude(Double.parseDouble(tracking.getLat()));
                    friendLocation.setLongitude(Double.parseDouble(tracking.getLng()));

                    //clear all old marker
                    mMap.clear();

                    //add friend marker on map
//                    mMap.addMarker(new MarkerOptions()
//                            .position(friendLatlng)
//                            .title(tracking.getEmail())
//                            //.snippet("Distance " + new DecimalFormat("#.#").format((currentUserLocation.distanceTo(friendLocation)) / 1000+" km"))
//                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                    //add friend circle on map
                    mMap.addCircle(new CircleOptions()
                            .center(friendLatlng)
                            .radius(10)
                            .strokeColor(Color.BLUE)
                            .fillColor(0x220000FF)
                            .strokeWidth(5)
                    );
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 12.0f));

                    //create marker to current user
                    LatLng currentuserLatlng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(currentuserLatlng)
                            .title(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                    );


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double distance(Location currentUserLocation, Location friendLocation) {
        double theta = currentUserLocation.getLongitude() - friendLocation.getLongitude();
        double dist = Math.sin(deg2rad(currentUserLocation.getLatitude()))
                * Math.sin(deg2rad(friendLocation.getLatitude()))
                * Math.cos(deg2rad(currentUserLocation.getLatitude()))
                * Math.cos(deg2rad(friendLocation.getLatitude()))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);

    }

    private double rad2deg(double dist) {
        return dist * 180 / Math.PI;
    }

    private double deg2rad(double latitude) {
        return (latitude * Math.PI / 180.0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}
