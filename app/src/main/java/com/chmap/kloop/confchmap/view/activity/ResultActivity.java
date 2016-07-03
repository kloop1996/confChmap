package com.chmap.kloop.confchmap.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ActivityResultBinding;
import com.chmap.kloop.confchmap.viewmodel.ResultViewModel;

/**
 * Created by kloop1996 on 03.07.2016.
 */
public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding activityResultBinding;
    private ResultViewModel resultViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultBinding = DataBindingUtil.setContentView(this, R.layout.activity_result);

        resultViewModel = new ResultViewModel(this);
        activityResultBinding.setViewModel(resultViewModel);

        setSupportActionBar(activityResultBinding.toolbar);

        final android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        //ab.setTitle(R.string.app_name);

        //activityResultBinding.toolbar.setTitle(R.string.enter_coordinate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
