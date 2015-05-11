package com.chmap.kloop.confchmap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.database.ExternalDbOpenHelper;

import java.util.HashMap;
import java.util.Vector;

public class MainActivity extends Activity{

    ActionBar actionBar;
    HashMap<String,Fragment> fragmentHashMap;
    public static Activity activity;

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
        fragmentHashMap.put("admin",Fragment.instantiate(this, FragmentAdminSearch.class.getName()));
    }



}