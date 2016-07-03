package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;

/**
 * Created by kloop1996 on 03.07.2016.
 */
public class ResultViewModel implements ViewModel {

    private Context context;

    public ResultViewModel (Context context){
        this.context= context;
    }

    @Override
    public void destroy() {
        context = null;
    }
}
