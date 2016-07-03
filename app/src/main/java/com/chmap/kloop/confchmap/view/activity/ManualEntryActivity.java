package com.chmap.kloop.confchmap.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ActivityManualEntryBinding;
import com.chmap.kloop.confchmap.viewmodel.ManualEntryViewModel;

/**
 * Created by kloop1996 on 27.06.2016.
 */
public class ManualEntryActivity extends AppCompatActivity {

    private ActivityManualEntryBinding activityManualEntryBinding;
    private ManualEntryViewModel manualEntryViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManualEntryBinding = DataBindingUtil.setContentView(this,R.layout.activity_manual_entry);

        manualEntryViewModel = new ManualEntryViewModel(this);
        activityManualEntryBinding.setViewModel(manualEntryViewModel);

        setSupportActionBar(activityManualEntryBinding.toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(R.string.app_name);

        activityManualEntryBinding.toolbar.setTitle(R.string.enter_coordinate);
      
    }

    @Override
    public void onStart() {
        super.onStart();
        manualEntryViewModel.notifyActivityOnStart();
    }

    @Override
    public void onStop() {
        manualEntryViewModel.notifyActivityOnStop();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
