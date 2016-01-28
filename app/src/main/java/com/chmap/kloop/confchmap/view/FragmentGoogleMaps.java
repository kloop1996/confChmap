package com.chmap.kloop.confchmap.view;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.chmap.kloop.confchmap.MainActivity;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.controller.BackgroundDefinePolution;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class FragmentGoogleMaps extends Fragment implements GoogleMap.OnMapLongClickListener {

    private static View view;
    private static GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        view = (RelativeLayout) inflater.inflate(R.layout.fragment_google_maps, container, false);
        setUpMapIfNeeded();

        return view;
    }


    public static void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((MapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.location_map)).getMap();

            if (mMap != null)
                setUpMap();
        }
    }


    private static void setUpMap() {
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        if (mMap != null)
            mMap.setOnMapLongClickListener(this);
            setUpMap();

        if (mMap == null) {

            mMap = ((MapFragment) MainActivity.fragmentManager
                    .findFragmentById(R.id.location_map)).getMap();

            if (mMap != null)
                setUpMap();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.location_map);
            if (mMap != null&&fragment.isResumed()) {
                MainActivity.fragmentManager.beginTransaction()
                        .remove(MainActivity.fragmentManager.findFragmentById(R.id.location_map)).commit();
                mMap = null;
            }
        }catch (Exception ex){;}
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        Coordinate coordinate= new Coordinate(latLng.latitude,latLng.longitude);

        BackgroundDefinePolution mt =new BackgroundDefinePolution();
        mt.execute(coordinate);
    }

}