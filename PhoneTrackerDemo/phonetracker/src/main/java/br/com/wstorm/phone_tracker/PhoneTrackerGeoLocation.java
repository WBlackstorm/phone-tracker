package br.com.wstorm.phone_tracker;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * Created by wperon on 12/15/15.
 */
public class PhoneTrackerGeoLocation implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback{

    public interface PhoneTrackerLocationListener {
        void OnLocationChanged(double latitude, double longitude);
    }

    private static PhoneTrackerGeoLocation instance;

    public static final int PERMISSION_CODE = 100;

    private Activity activity;

    private GoogleApiClient googleApiClient;

    private Location mLocation;

    private double latitude;

    private double longitude;

    private LocationRequest mLocationRequest;

    private int fastIntervalLocation;
    private int longIntervalLocation;

    private int priority;

    private PhoneTrackerLocationListener listener;

    public Location getLocation() {
        return mLocation;
    }

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public static PhoneTrackerGeoLocation getInstance(Activity activity,
                                                int fastIntervalLocation,
                                                int longIntervalLocation,
                                                int priority,
                                                PhoneTrackerLocationListener listener) {

        if (instance == null) {

            instance = new PhoneTrackerGeoLocation(activity, fastIntervalLocation, longIntervalLocation, priority, listener);

        }

        return instance;

    }

    private PhoneTrackerGeoLocation(Activity activity,
                             int fastIntervalLocation,
                             int longIntervalLocation,
                             int priority,
                             PhoneTrackerLocationListener listener) {

        this.activity = activity;
        this.latitude = 0.0;
        this.longitude = 0.0;
        this.fastIntervalLocation = fastIntervalLocation;
        this.longIntervalLocation = longIntervalLocation;
        this.priority = priority;
        this.listener = listener;

        initLocationService(activity);

    }

    private void initLocationService(Activity activity) {

        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    PERMISSION_CODE);
            return;


        }

        googleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(longIntervalLocation);
        mLocationRequest.setFastestInterval(fastIntervalLocation);
        mLocationRequest.setPriority(priority);

    }

    public void init() {
        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    public void stop() {
        if (googleApiClient != null && !googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case PERMISSION_CODE: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    initLocationService(activity);

                }

            }

        }

    }

    @Override
    public void onConnected(Bundle bundle) {

        try {

            mLocation = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, mLocationRequest, new com.google.android.gms.location.LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                            mLocation = location;
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            if (listener != null) {
                                listener.OnLocationChanged(latitude, longitude);
                            }

                        }
                    });

        } catch (SecurityException e) {
            Log.e("PhoneTrackerGeoLocation", "User does not allow to get location");
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

}
