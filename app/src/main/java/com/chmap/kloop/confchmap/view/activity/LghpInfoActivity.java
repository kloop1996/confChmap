package com.chmap.kloop.confchmap.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.databinding.ActivityLghpInfoBinding;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.view.adapter.LghpCuttingAdapter;
import com.chmap.kloop.confchmap.viewmodel.LghpInfoActivityViewModel;

import java.util.List;

/**
 * Created by kloop1996 on 15.04.2017.
 */

public class LghpInfoActivity extends AppCompatActivity implements LghpInfoActivityViewModel.DataListener {

    private LghpInfoActivityViewModel infoActivityViewModel;
    private ActivityLghpInfoBinding activityLghpInfoBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLghpInfoBinding= DataBindingUtil.setContentView(this,R.layout.activity_lghp_info);
        setupRecyclerView();

        Lghp lghp = null;

        if (getIntent().getExtras().get(Constants.LGHP)!=null){
            lghp = (Lghp)getIntent().getExtras().get(Constants.LGHP);
        }

        infoActivityViewModel = new LghpInfoActivityViewModel(this,lghp,this);

        activityLghpInfoBinding.setViewModel(infoActivityViewModel);



    }

    public void setupRecyclerView(){
        LghpCuttingAdapter lghpCuttingAdapter = new LghpCuttingAdapter();
        activityLghpInfoBinding.cuttingRecyclerView.setAdapter(lghpCuttingAdapter);
        activityLghpInfoBinding.cuttingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lghpCuttingAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLghpCuttingChanged(List<LghpCutting> lghpCuttingList) {

        LghpCuttingAdapter lghpCuttingAdapter = (LghpCuttingAdapter) activityLghpInfoBinding.cuttingRecyclerView.getAdapter();
        lghpCuttingAdapter.setLocalePreviews(lghpCuttingList);
        lghpCuttingAdapter.notifyDataSetChanged();
    }
}
