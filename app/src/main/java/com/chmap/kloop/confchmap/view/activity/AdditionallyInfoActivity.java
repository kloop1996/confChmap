package com.chmap.kloop.confchmap.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ActivityAdditinalInfoBinding;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.entity.PolutionSet;
import com.chmap.kloop.confchmap.viewmodel.AdditionallyInfoActivityViewModel;

/**
 * Created by kloop1996 on 07.08.2016.
 */
public class AdditionallyInfoActivity extends AppCompatActivity {
    private ActivityAdditinalInfoBinding activityAdditinalInfoBinding;
    private AdditionallyInfoActivityViewModel additionallyInfoActivityViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAdditinalInfoBinding = DataBindingUtil.setContentView(this, R.layout.activity_additinal_info);
        additionallyInfoActivityViewModel = new AdditionallyInfoActivityViewModel(this, (PolutionSet) getIntent().getSerializableExtra(Constants.POLUTION), ((Coordinate) getIntent().getSerializableExtra(Constants.COORDINATE)));
        activityAdditinalInfoBinding.setViewModel(additionallyInfoActivityViewModel);
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        additionallyInfoActivityViewModel.destroy();
    }


}
