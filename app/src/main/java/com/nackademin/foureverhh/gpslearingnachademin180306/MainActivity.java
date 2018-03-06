package com.nackademin.foureverhh.gpslearingnachademin180306;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient providerClient;
    private static final int REQUEST_LOCATION = 1;
    LocationRequest locationRequest;
    LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        providerClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            Log.d("MainActivity", "No permission");

            //ask for permission

        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            providerClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Log.d("MainActivity", "lat:" + latitude + " long: " + longitude);

                    }
                }
            });
        }

        //update the location service
        createLocationRequest();
        //show the updated position
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    location.getLatitude();
                    location.getLatitude(); //And many different functions
                    Log.d("MainActivity", "Lat : " + location.getLatitude() + " Long :" + location.getLongitude());
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        providerClient.requestLocationUpdates(locationRequest, locationCallback, null);

    }

    private void stopLocationUpdates(){
        providerClient.removeLocationUpdates(locationCallback);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResult){
        if(requestCode == REQUEST_LOCATION){
            if(grantResult.length == 1 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                //get location first time app in run after get permission

            }else{
                //Permission denied by user

            }
        }

    }

    void createLocationRequest(){
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

}
