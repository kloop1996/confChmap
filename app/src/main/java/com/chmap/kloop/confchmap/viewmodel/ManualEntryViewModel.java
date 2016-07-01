package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;

/**
 * Created by kloop1996 on 27.06.2016.
 */
public class ManualEntryViewModel implements ViewModel {

    private Context context;

    public ManualEntryViewModel(Context context) {
        this.context = context;
    }

    @Override
    public void destroy() {
        context = null;
    }
}
