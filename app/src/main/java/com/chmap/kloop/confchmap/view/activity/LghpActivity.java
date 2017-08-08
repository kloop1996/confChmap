package com.chmap.kloop.confchmap.view.activity;

import android.app.FragmentTransaction;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.chmap.kloop.confchmap.OnNavigationDrawerListener;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ActivityLghpBinding;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.viewmodel.LghpActivityViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

/**
 * Created by kloop1996 on 14.04.2017.
 */

public class LghpActivity extends AppCompatActivity implements LghpActivityViewModel.DataListener, OnNavigationDrawerListener, OnMapReadyCallback {
    private LghpActivityViewModel lghpActivityViewModel;
    private ActivityLghpBinding activityLghpBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityLghpBinding = DataBindingUtil.setContentView(this, R.layout.activity_lghp);

        GoogleMapOptions options = new GoogleMapOptions();
        options.compassEnabled(true);
        options.useViewLifecycleInFragment(false);
        MapFragment mMapFragment = MapFragment.newInstance(options);
        mMapFragment.getMapAsync(this);

        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.map_container, mMapFragment);
        fragmentTransaction.commit();


        lghpActivityViewModel = new LghpActivityViewModel(this, this);
        onLghpChanged();

        activityLghpBinding.setViewModel(lghpActivityViewModel);
        activityLghpBinding.autocompleteTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityLghpBinding.autocompleteTo.showDropDown();
            }
        });

        activityLghpBinding.autocompleteTo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lghpActivityViewModel.notifyToSelect(position);
            }
        });

    }


    @Override
    public void onDrawerClose() {

    }

    @Override
    public void onDrawerOpen() {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        lghpActivityViewModel.notifyMapLoad(googleMap);
    }

    @Override
    public void onLghpChanged() {
        List<Lghp> lghpList = null;

        try {
            lghpList = CoordinateService.getLghp();
            String[] items = new String[lghpList.size()];

            for (int i = 0; i < lghpList.size(); i++) {
                items[i] = lghpList.get(i).getName() + ", " + CoordinateService.getNameDistrictById(lghpList.get(i).getIdOfDistrict()) + " район";
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, items);

            activityLghpBinding.autocompleteTo.setAdapter(adapter);
            lghpActivityViewModel.setLghpList(lghpList);

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
