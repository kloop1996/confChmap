package com.chmap.kloop.confchmap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chmap.kloop.confchmap.view.FragmentDatabaseSearch;
import com.chmap.kloop.confchmap.view.FragmentGoogleMaps;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.service.comparator.SortCityByDistance;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.service.impl.PolutionService;
import com.chmap.kloop.confchmap.view.FragmentGpsSearch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends Activity {

    ActionBar actionBar;
    HashMap<String,Fragment> fragmentHashMap;
    public static Activity activity;
    public static FragmentManager fragmentManager;
    public static Activity getInstance(){return  activity;}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        actionBar = getActionBar();
        fragmentHashMap=new HashMap<String,Fragment>();
        activity=this;

        initFragments();
        ArrayList<City> cities =new ArrayList<City>();

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
                case R.id.menu_help:
                    tx = getFragmentManager()
                            .beginTransaction();

                    tx.replace(android.R.id.content, (Fragment) fragmentHashMap.get("database"));
                    tx.commit();
                    break;
            }
            return false;

    }


    private void initFragments(){
        fragmentHashMap.put("gps",Fragment.instantiate(this, FragmentGpsSearch.class.getName()));
        fragmentHashMap.put("admin", Fragment.instantiate(this, FragmentGoogleMaps.class.getName()));
        fragmentHashMap.put("database",Fragment.instantiate(this, FragmentDatabaseSearch.class.getName()));
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}