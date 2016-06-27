package com.chmap.kloop.confchmap.controller;

import android.app.FragmentManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.service.comparator.SortCityByDistance;
import com.chmap.kloop.confchmap.service.comparator.SortPolutionByYear;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.service.impl.PolutionService;
import com.chmap.kloop.confchmap.view.activity.MainActivity;
import com.chmap.kloop.confchmap.view.dialog.DialogShowResult;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by kloop on 17.12.2015.
 */
public class BackgroundDefinePolution extends AsyncTask<Coordinate, Void, Void> {

    private static ArrayList<City> nearCity;
    private static ArrayList<Polution> polutions;
    private static Coordinate currentPosition;
    private boolean error = false;

    public static ArrayList<Polution> getResultPolution() {
        return polutions;
    }
    public static ArrayList<City> getResultNearCity() {
        return nearCity;
    }
    public static Coordinate getCurrentPosition(){return currentPosition;}

    @Override
    protected Void doInBackground(Coordinate... params) {
        error = false;
        nearCity=null;
        polutions=null;
        currentPosition=params[0];
        try {
            long currentTime = System.currentTimeMillis();

            polutions = PolutionService.getAllPolutionByCordinate(params[0]);
            Collections.sort(polutions,new SortPolutionByYear());

            Log.d("Polutiong",String.valueOf(currentTime - System.currentTimeMillis()));

            currentTime = System.currentTimeMillis();
            nearCity = CoordinateService.getNearCities(params[0]);

            Log.d("NearCity",String.valueOf(currentTime - System.currentTimeMillis()));

            Collections.sort(nearCity, new SortCityByDistance());
        } catch (ServiceException e) {
            error=true;
            currentPosition=null;
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (error||polutions.size()==0) {
            Toast.makeText(MainActivity.getInstance(), "Ошибка определения загрязнения", Toast.LENGTH_LONG).show();
        } else {
            FragmentManager manager = MainActivity.getInstance().getFragmentManager();
            DialogShowResult dlgResult = new DialogShowResult();
            dlgResult.show(manager, "fragment_edit_name");
        }
    }
}