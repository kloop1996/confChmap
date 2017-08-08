package com.chmap.kloop.confchmap.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ActivityMapBinding;
import com.chmap.kloop.confchmap.viewmodel.MapViewModel;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


/**
 * Created by kloop1996 on 10.08.2016.
 */
public class MapActivity extends AppCompatActivity {
    private MapViewModel mapViewModel;
    private ActivityMapBinding activityMapBinding;
    private PlaceSelectionListener placeSelectionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMapBinding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        mapViewModel = new MapViewModel(this);
        activityMapBinding.setViewModel(mapViewModel);

        setSupportActionBar(activityMapBinding.toolbar);
        getSupportActionBar().setTitle(R.string.message_select_point);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        placeSelectionListener = new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mapViewModel.moveMapCamera(place.getLatLng());
            }

            @Override
            public void onError(Status status) {

            }
        };

        mapFragment.getMapAsync(mapViewModel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapViewModel.destroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_search_base:
                zzzG();
                return true;
        }

        return false;
    }

    private void zzzG() {
        int var1 = -1;

        try {

            Intent var2 = (new PlaceAutocomplete.IntentBuilder(2)).setBoundsBias(new LatLngBounds(new LatLng(51.266643,23.185005),new LatLng(56.170745,32.773354))).build(this);
            this.startActivityForResult(var2, 1);
        } catch (GooglePlayServicesRepairableException var3) {
            var1 = var3.getConnectionStatusCode();
            Log.e("Places", "Could not open autocomplete activity", var3);
        } catch (GooglePlayServicesNotAvailableException var4) {
            var1 = var4.errorCode;
            Log.e("Places", "Could not open autocomplete activity", var4);
        }

        if (var1 != -1) {
            GoogleApiAvailability var5 = GoogleApiAvailability.getInstance();
            var5.showErrorDialogFragment(this, var1, 2);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == -1) {
                Place var4 = PlaceAutocomplete.getPlace(this, data);
                if (placeSelectionListener!=null) {
                    placeSelectionListener.onPlaceSelected(var4);
                }
            }
            }
        }
}
