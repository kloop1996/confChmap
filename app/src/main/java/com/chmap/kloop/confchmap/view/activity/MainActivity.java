package com.chmap.kloop.confchmap.view.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.OnNavigationDrawerListener;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.view.fragment.FragmentGpsSearch;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class MainActivity extends AppCompatActivity implements OnNavigationDrawerListener {


    private final static String GPS_FRAGMENT = "gps_fragment";
    public static Activity activity;
    public static FragmentManager fragmentManager;
    private Drawer mDrawer;

    public static Activity getInstance() {
        return activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

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
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_base_title).withIcon(R.drawable.ic_format_list_bulleted_type).withDescription(R.string.drawer_item_search_base_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_gps_title).withIcon(R.drawable.ic_crosshairs_gps).withDescription(R.string.drawer_item_search_gps_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_search_coordinates_title).withIcon(R.drawable.ic_map_marker_circle).withDescription(R.string.drawer_item_search_coordinates_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color).withTag(Constants.MANUAL_ENTRY_ACTIVITY),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_help_title).withIcon(R.drawable.ic_help_circle_outline).withDescription(R.string.drawer_item_help_subtitle).withDescriptionTextColorRes(R.color.description_item_text_color)
                ).withSavedInstance(savedInstanceState).withOnDrawerListener(new Drawer.OnDrawerListener() {
            @Override
            public void onDrawerOpened(View view) {
                ((OnNavigationDrawerListener) MainActivity.getInstance()).onDrawerOpen();
            }

            @Override
            public void onDrawerClosed(View view) {
                ((OnNavigationDrawerListener) MainActivity.getInstance()).onDrawerClose();
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
        }
    }



}