package com.chmap.kloop.confchmap.view.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.OnNavigationDrawerListener;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.District;
import com.chmap.kloop.confchmap.entity.Locale;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.view.fragment.FragmentGpsSearch;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnNavigationDrawerListener {


    private final static String GPS_FRAGMENT = "gps_fragment";
    private FragmentManager fragmentManager;
    private Drawer mDrawer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.app_name);


        new DrawerBuilder(this)
                //this layout have to contain child layouts
                .withRootView(R.id.drawer_container)
                .withToolbar(toolbar)
                .withCloseOnClick(true)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)

                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_base_title).withIcon(R.drawable.ic_format_list_bulleted_type).withDescription(R.string.drawer_item_search_base_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.BASE_SELECT_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_gps_title).withIcon(R.drawable.ic_crosshairs_gps).withDescription(R.string.drawer_item_search_gps_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_coordinates_title).withIcon(R.drawable.ic_map_marker_circle).withDescription(R.string.drawer_item_search_coordinates_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.map_message).withIcon(R.drawable.ic_google_maps).withDescription(R.string.message_internet_connect).withTag(Constants.MAP_ACTIVITY).withDescriptionTextColorRes(R.color.description_item_text_color),
                        new PrimaryDrawerItem().withName(R.string.lghp_message).withIcon(R.drawable.ic_google_maps).withDescription(R.string.message_internet_connect).withTag(Constants.LGHP_ACTIVITY).withDescriptionTextColorRes(R.color.description_item_text_color),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_help_title).withIcon(R.drawable.ic_help_circle_outline).withDescription(R.string.drawer_item_help_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY)
                ).withSavedInstance(savedInstanceState).withOnDrawerListener(new Drawer.OnDrawerListener() {
            @Override
            public void onDrawerOpened(View view) {
                MainActivity.this.onDrawerOpen();
            }

            @Override
            public void onDrawerClosed(View view) {
                MainActivity.this.onDrawerClose();
            }

            @Override
            public void onDrawerSlide(View view, float v) {

            }
        })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        changeActivity((String) drawerItem.getTag());
                        return false;
                    }
                })
                .build();


        if (savedInstanceState == null) {

            FragmentTransaction tx = getFragmentManager()
                    .beginTransaction();

            tx.add(R.id.frame_container, new FragmentGpsSearch(), GPS_FRAGMENT);
            tx.addToBackStack(null);
            tx.commit();
            fragmentManager = getFragmentManager();


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


    @Override
    public void onDrawerClose() {
        getSupportActionBar().setTitle(R.string.app_name);
    }

    @Override
    public void onDrawerOpen() {
        getSupportActionBar().setTitle(R.string.drawer_tolbar_title);
    }

    private void changeActivity(String tag) {
        switch (tag) {
            case Constants.MANUAL_ENTRY_ACTIVITY:
                startActivity(new Intent(this, ManualEntryActivity.class));
                break;
            case Constants.BASE_SELECT_ACTIVITY:
                startActivity(new Intent(this, BaseSelectActivity.class));
                break;
            case Constants.MAP_ACTIVITY:
                startActivity(new Intent(this,MapActivity.class));
                break;
            case Constants.LGHP_ACTIVITY:
                startActivity(new Intent(this,LghpActivity.class));
                break;
        }
    }



}