package com.chmap.kloop.confchmap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends Activity {

    ActionBar actionBar;
    HashMap<String,Fragment> fragmentHashMap;
    public static Activity activity;
    public static FragmentManager fragmentManager;
    public static Activity getInstance(){return  activity;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);

        actionBar = getActionBar();
        fragmentHashMap=new HashMap<String,Fragment>();
        activity=getInstance();

        initFragments();


        FragmentTransaction tx = getFragmentManager()
                .beginTransaction();

        tx.replace(android.R.id.content, (Fragment) fragmentHashMap.get("gps"));
        tx.commit();
        fragmentManager=getFragmentManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionf_file, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        FragmentTransaction tx;
            switch (item.getItemId()){
                case R.id.menu_search_coordinates:
                    tx = getFragmentManager()
                            .beginTransaction();

                    tx.replace(android.R.id.content, (Fragment) fragmentHashMap.get("gps"));
                    tx.commit();
                    break;
                case R.id.menu_search_base:
                    tx = getFragmentManager()
                            .beginTransaction();

                    tx.replace(android.R.id.content, (Fragment) fragmentHashMap.get("admin"));
                    tx.commit();
                    break;
            }
            return false;

    }


    private void initFragments(){
        fragmentHashMap.put("gps",Fragment.instantiate(this, FragmentGpsSearch.class.getName()));
        fragmentHashMap.put("admin", Fragment.instantiate(this, FragmentGoogleMaps.class.getName()));
    }



}