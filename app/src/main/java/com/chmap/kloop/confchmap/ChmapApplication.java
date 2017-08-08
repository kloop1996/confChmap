package com.chmap.kloop.confchmap;

import android.app.Application;
import android.content.Context;

/**
 * Created by kloop1996 on 27.06.2016.
 */
public class ChmapApplication extends Application {

    private static Context context;

    public static Context getInstance(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
