package com.chmap.kloop.confchmap.view.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.controller.BackgroundDefinePolution;
import com.chmap.kloop.confchmap.entity.Coordinate;

import java.security.spec.ECField;

/**
 * Created by android on 09.04.2015.
 */
public class FragmentGpsSearch extends Fragment implements View.OnClickListener {

    private final static String LONGITUDE_VALUE = "longitude";
    private final static String LATITUDE_VALUE = "latitude";

    private static Resources mRes;
    private EditText edtLat;
    private EditText edtLong;
    private LocationManager locationManager;
    private Button definePolution;
    private Button getGps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gps_search, container, false);

        definePolution = (Button) rootView.findViewById(R.id.btnSearch);
        getGps = (Button) rootView.findViewById(R.id.btnGpsSearch);
        edtLat = (EditText) rootView.findViewById(R.id.edtLat);
        edtLong = (EditText) rootView.findViewById(R.id.edtLong);

        if (savedInstanceState != null) {
            edtLat.setText(savedInstanceState.getString(LATITUDE_VALUE));
            edtLong.setText(savedInstanceState.getString(LONGITUDE_VALUE));
        }

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        getGps.setOnClickListener(this);
        definePolution.setOnClickListener(this);
        mRes = getResources();
        return rootView;
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(LONGITUDE_VALUE, edtLong.getText().toString());
        savedInstanceState.putString(LATITUDE_VALUE, edtLat.getText().toString());

    }

    public static Resources mGetRes() {
        return mRes;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.btnSearch:
                Coordinate coordinate = null;
                try {
                    coordinate = new Coordinate();
                    coordinate.setLongitude(Double.parseDouble(edtLong.getText().toString()));
                    coordinate.setLatitude(Double.parseDouble(edtLat.getText().toString()));
                } catch (Exception ex) {
                    Toast.makeText(getActivity(), "Введите корректные координаты", Toast.LENGTH_LONG).show();
                }

                BackgroundDefinePolution mt = new BackgroundDefinePolution();
                mt.execute(coordinate);
                break;
            case R.id.btnGpsSearch:
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        1000 * 10, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        1000 * 10, 0, locationListener);
                break;
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {

            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {

            }
        }
    };

    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            edtLat.setText(Double.toString(location.getLatitude()));
            edtLong.setText(Double.toString(location.getLongitude()));
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            edtLong.setText(Double.toString(location.getLongitude()));
            edtLat.setText(Double.toString(location.getLatitude()));
            ;
        }
    }


    private void checkEnabled() {
        ;
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    ;

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("GPS не включен. Желаете включить?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}
