package com.chmap.kloop.confchmap.viewmodel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.ObservableField;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.entity.TimeEntity;
import com.chmap.kloop.confchmap.util.TimeConverUtil;
import com.chmap.kloop.confchmap.view.activity.LghpInfoActivity;
import com.chmap.kloop.confchmap.view.activity.ManualEntryActivity;
import com.chmap.kloop.confchmap.view.activity.ResultActivity;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by kloop1996 on 14.04.2017.
 */

public class LghpActivityViewModel implements ViewModel,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,RoutingListener,GoogleMap.OnMarkerClickListener {
    private Context context;
    private DataListener dataListener;

    public ObservableField<String> distanse = new ObservableField<String>();


    private List<Lghp> lghpList;

    private Lghp currentLghp;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;

    private Marker markerFrom;
    private Marker markerTo;

    private Polyline polyline;

    private LatLng from;
    private LatLng to;


    public LghpActivityViewModel(Context context, DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;

        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        }

        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();

            mLocationRequest.setInterval(Constants.UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setFastestInterval(Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            //checkLocationSettings();
        }

        //dataListener.onLghpChanged();
    }
    @Override
    public void destroy() {
        context = null;

        if (mGoogleApiClient!=null){
            mGoogleApiClient.disconnect();
        }

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
        from = new LatLng(location.getLatitude(),location.getLongitude());

        if (markerFrom != null) {
            markerFrom.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());

        markerOptions.position(latLng);
        markerOptions.title(context.getResources().getString(R.string.from));
        markerOptions.draggable(true);
        markerFrom = mGoogleMap.addMarker(markerOptions);
        markerFrom.showInfoWindow();

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    private void initAutcompleteView(){
        ;
    }

    public void notifyMapLoad(GoogleMap googleMap){
        this.mGoogleMap = googleMap;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setOnMarkerClickListener(this);
    }

    public void notifyToSelect(int lghpId){
        Lghp lghp = lghpList.get(lghpId);
        currentLghp = lghp;
        to= new LatLng(lghp.getCoordinate().getLatitude(),lghp.getCoordinate().getLongitude());

        if (markerTo != null) {
            markerTo.remove();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng = new LatLng(lghp.getCoordinate().getLatitude(),lghp.getCoordinate().getLongitude());

        markerOptions.position(latLng);
        markerOptions.title("ЛГХП "+ lghp.getName());
        markerOptions.draggable(true);
        markerTo = mGoogleMap.addMarker(markerOptions);
        markerTo.showInfoWindow();

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        Routing routing = new Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .withListener(this)
                .waypoints(to, from)
                .key(context.getString(R.string.google_direction_key))
                .build();
        routing.execute();

    }

    public void setLghpList(List<Lghp> lghpList){
        this.lghpList = lghpList;
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        e.getMessage();
    }

    @Override
    public void onRoutingStart() {

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int i) {
        List<Polyline> polylines = new ArrayList<>();
        //add route(s) to the map.
        LatLngBounds.Builder latLngBuilder = new LatLngBounds.Builder();
        latLngBuilder.include(from);
        latLngBuilder.include(to);

        PolylineOptions polyOptions = new PolylineOptions();
        polyOptions.width(25);
        polyOptions.color(R.color.polyline_color);
        polyOptions.addAll(route.get(i).getPoints());
        for (LatLng latLng : route.get(i).getPoints()) {
            latLngBuilder.include(latLng);
        }

        if (polyline != null)
            polyline.remove();


        polyline = mGoogleMap.addPolyline(polyOptions);

        polylines.add(polyline);
        route.get(i);

        setDistance(route.get(0).getDistanceValue());


        moveMapCamera(latLngBuilder.build());
    }

    private void moveMapCamera(LatLngBounds latLngBounds) {
        int size = context.getResources().getDisplayMetrics().widthPixels;

        CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);

        mGoogleMap.moveCamera(track);

    }

    public void setDistance(int distance){

        this.distanse.set(String.format(Locale.ENGLISH,"%.1f",(distance/1000.0))+"Км");
    }


    @Override
    public void onRoutingCancelled() {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(markerTo)){
            Intent intent = new Intent(context, LghpInfoActivity.class);
            intent.putExtra(Constants.LGHP,currentLghp);

            context.startActivity(intent);
        }

        return true;
    }

    public interface DataListener{
        void onLghpChanged();
    }


}
