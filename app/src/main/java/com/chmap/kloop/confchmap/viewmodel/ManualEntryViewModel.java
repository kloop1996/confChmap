package com.chmap.kloop.confchmap.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.databinding.ObservableField;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.controller.BackgroundDefinePolution;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.DowngradeableSafeParcel;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kloop1996 on 27.06.2016.
 */
public class ManualEntryViewModel implements ViewModel, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private LocationRequest mLocationRequest;
    private Context context;
    private GoogleApiClient mGoogleApiClient;

    private ObservableField<String> latitudeField;
    private ObservableField<String> longitudeField;

    public ManualEntryViewModel(Context context) {
        this.context = context;

        latitudeField = new ObservableField<String>();
        longitudeField = new ObservableField<String>();
    }

    public ObservableField<String> getLongitudeField() {
        return longitudeField;
    }

    public void setLongitudeField(ObservableField<String> longitudeField) {
        this.longitudeField = longitudeField;
    }

    public ObservableField<String> getLatitudeField() {
        return latitudeField;
    }

    public void setLatitudeField(ObservableField<String> latitudeField) {
        this.latitudeField = latitudeField;
    }

    public void notifyActivityOnStart() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.connect();
    }

    public void notifyActivityOnStop() {
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

    }

    @Override
    public void destroy() {
        context = null;
        mGoogleApiClient.disconnect();
    }


    public void onClickSearch(View view) {
        BackgroundDefinePolution backgroundDefinePolution = new BackgroundDefinePolution();
        backgroundDefinePolution.setContext(context);

        backgroundDefinePolution.execute(new Coordinate(Double.parseDouble(latitudeField.get()), Double.parseDouble(longitudeField.get())));

    }

    public void onClickGps(View view) {
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }

        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();

            mLocationRequest.setInterval(Constants.UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setFastestInterval(Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

        if (mGoogleApiClient.isConnected())
            startLocationUpdates();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitudeField.set(String.valueOf(location.getLatitude()));
        longitudeField.set(String.valueOf(location.getLongitude()));
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
}
