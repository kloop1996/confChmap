package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;

import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.view.activity.LghpInfoActivity;

/**
 * Created by kloop1996 on 17.04.2017.
 */

public class ItemLghpCuttingViewModel implements ViewModel {
    private Context context;
    private LghpCutting lghpCutting;

    public LghpCutting getLghpCutting() {
        return lghpCutting;
    }

    public void setLghpCutting(LghpCutting lghpCutting) {
        this.lghpCutting = lghpCutting;
    }

    public ItemLghpCuttingViewModel(Context context, LghpCutting lghpCutting) {
        this.context = context;
        this.lghpCutting = lghpCutting;
    }


    @Override
    public void destroy() {
        context = null;
    }


}
