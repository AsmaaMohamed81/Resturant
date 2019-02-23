package com.alatheer.menu.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.alatheer.menu.models.LocationModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by elashry on 27/09/2018.
 */

public class LocationService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private final int displacement = 10;

    private void iniGoogleApi() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void initLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(displacement);

    }


    @Override
    public void onCreate() {
        super.onCreate();
        iniGoogleApi();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        initLocationRequest();
        startLocationUpdate();

    }

    private void startLocationUpdate() {

        Log.e("looooc","looooooc");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

        }
        Log.e("looooc2","looooooc2");

        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                onLocationChanged(locationResult.getLastLocation());
            }
        }, Looper.myLooper());
    }

    @Override
    public void onConnectionSuspended(int i) {

        if (googleApiClient!=null)
        {
            googleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.e("lat",location.getLatitude()+"_");
        Log.e("lng",location.getLongitude()+"_");

        EventBus.getDefault().post(new LocationModel(location.getLatitude(),location.getLongitude()));
    }


    @Override
    public void onDestroy() {
        if (googleApiClient!=null)
        {
            googleApiClient.disconnect();
        }
        LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(new LocationCallback());
        super.onDestroy();
    }
}
