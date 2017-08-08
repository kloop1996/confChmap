package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.controller.BackgroundDefinePolution;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.entity.PolutionSet;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.service.impl.PolutionService;
import com.chmap.kloop.confchmap.view.activity.AdditionallyInfoActivity;
import com.chmap.kloop.confchmap.view.activity.ResultActivity;

import java.util.List;

/**
 * Created by kloop1996 on 03.07.2016.
 */
public class ResultViewModel implements ViewModel {

    public ObservableInt recyclerViewVisibility;
    public ObservableInt progressVisibility;
    private ObservableField<String> locationField;
    private ObservableField<String> statusField;
    private Context context;
    private DataListener dataListener;
    private Coordinate coordinate;
    private List<Polution> polutions;
    public ResultViewModel(Context context, DataListener dataListener, Coordinate coordinate) {
        this.dataListener = dataListener;
        this.context = context;
        this.coordinate = coordinate;

        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        progressVisibility = new ObservableInt(View.VISIBLE);

        try {
            locationField = new ObservableField<String>(CoordinateService.getApproximatedDistrict(coordinate) + " район");
            statusField = new ObservableField<String>();
        } catch (ServiceException e) {
            locationField.set("Ошибка определения");
        }


        loadPolution();
    }

    public ObservableField<String> getLocationField() {
        return locationField;
    }
    public ObservableField<String> getStatusField() {
        return statusField;
    }

    public void setLocationField(ObservableField<String> locationField) {
        this.locationField = locationField;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    private void loadPolution() {
        BackgroundDefinePolution backgroundDefinePolution = new BackgroundDefinePolution();
        backgroundDefinePolution.setContext(context);

        backgroundDefinePolution.execute(coordinate);
    }

    @Override
    public void destroy() {
        context = null;
    }

    public void onClickDetail(View view) {
        Intent intent = new Intent(context, AdditionallyInfoActivity.class);

        intent.putExtra(Constants.POLUTION, new PolutionSet(polutions));
        intent.putExtra(Constants.COORDINATE, coordinate);
        context.startActivity(intent);
    }

    public List<Polution> getPolutions() {
        return polutions;
    }

    public void setPolutions(List<Polution> polutions) {
        this.polutions = polutions;
    }

    public interface DataListener {
        void onPolutionChanged(List<Polution> work);
    }


}
