package com.chmap.kloop.confchmap.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.view.activity.MapActivity;
import com.chmap.kloop.confchmap.view.activity.ResultActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by kloop1996 on 10.08.2016.
 */
public class MapViewModel implements ViewModel, OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private Context context;
    private GoogleMap mGoogleMap;

    public MapViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context = null;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(Constants.LATITUDE,String.valueOf(latLng.latitude));
        intent.putExtra(Constants.LONGITUDE,String.valueOf(latLng.longitude));
        context.startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setOnMapLongClickListener(this);

        if (!(ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            mGoogleMap.setMyLocationEnabled(true);
        }

    }

    public void moveMapCamera(LatLng latLng) {
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }
}
