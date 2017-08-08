package com.chmap.kloop.confchmap.viewmodel;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.databinding.ObservableField;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.controller.BackgroundDefinePolution;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.view.activity.ManualEntryActivity;
import com.chmap.kloop.confchmap.view.activity.ResultActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.DowngradeableSafeParcel;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kloop1996 on 27.06.2016.
 */
public class ManualEntryViewModel implements ViewModel, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private OnStopPulsatorListener mOnStopPulsatorListener;
    private Context context;

    private ObservableField<String> latitudeField;
    private ObservableField<String> longitudeField;

    public ManualEntryViewModel(Context context) {
        this.context = context;
        this.mOnStopPulsatorListener = (OnStopPulsatorListener)context;
        latitudeField = new ObservableField<String>("");
        longitudeField = new ObservableField<String>("");
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
        if (mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
    }


    public void onClickSearch(View view) {

        if ((longitudeField.get().equals("")) | (latitudeField.get().equals(""))) {
            Toast.makeText(context, context.getResources().getString(R.string.message_coordinate_enter), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, ResultActivity.class);

        intent.putExtra(Constants.LATITUDE, latitudeField.get());
        intent.putExtra(Constants.LONGITUDE, longitudeField.get());

        context.startActivity(intent);

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

            checkLocationSettings();

        }

        ((ManualEntryActivity)context).startAnimation();

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

        //mOnStopPulsatorListener.stopAnimation();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    public TextWatcher getLatitudeEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                latitudeField.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }


    public TextWatcher getLongitudeEditTextWatcher() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                longitudeField.set(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates locationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult((Activity) ManualEntryViewModel.this.context, 1);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:


                        break;

                }
            }
        });
    }




}
