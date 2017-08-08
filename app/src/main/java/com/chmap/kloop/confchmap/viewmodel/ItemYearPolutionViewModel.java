package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.entity.Polution;

/**
 * Created by kloop1996 on 03.07.2016.
 */
public class ItemYearPolutionViewModel implements ViewModel {

    private Context context;
    private Polution polution;

    private ObservableField<String> poltuionLevelField;
    private ObservableField<String> yearField;

    private ObservableInt recyclerViewVisibility;
    private ObservableInt progressVisibility;

    public ObservableField<String> getPoltuionLevelField() {
        poltuionLevelField.set(getPolutionLevel());
        return poltuionLevelField;
    }

    public ObservableField<String> getYearField() {
        yearField.set(getPolutionYear());
        return yearField;
    }

    public String getPolutionYear(){return String.valueOf(polution.getYear());}

    public String getPolutionLevel(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(polution.getLevel().getStartValue());
        stringBuilder.append("-");
        stringBuilder.append(polution.getLevel().getEndValue());

        return stringBuilder.toString();
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public ObservableInt getRecyclerViewVisibility() {
        return recyclerViewVisibility;
    }

    public void setRecyclerViewVisibility(ObservableInt recyclerViewVisibility) {
        this.recyclerViewVisibility = recyclerViewVisibility;
    }

    public ObservableInt getProgressVisibility() {
        return progressVisibility;
    }

    public void setProgressVisibility(ObservableInt progressVisibility) {
        this.progressVisibility = progressVisibility;
    }

    public ItemYearPolutionViewModel(Context context,Polution polution){
        this.context = context;
        this.polution = polution;

        yearField = new ObservableField<String>();
        poltuionLevelField = new ObservableField<String>();

    }

    public Polution getPolution() {
        return polution;
    }

    public void setPolution(Polution polution) {
        this.polution = polution;

        poltuionLevelField.notifyChange();
        yearField.notifyChange();
    }

    @Override
    public void destroy() {
        context = null;
    }

}
