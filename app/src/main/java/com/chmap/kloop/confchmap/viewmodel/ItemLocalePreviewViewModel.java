package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.LocalePreview;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.view.activity.ResultActivity;

import java.util.Collection;

/**
 * Created by kloop1996 on 05.07.2016.
 */
public class ItemLocalePreviewViewModel implements ViewModel {

    private Context context;
    private LocalePreview localePreview;
    private BaseSelectViewModel.DataListener dataListener;

    private ObservableInt countForChoice;
    private ObservableField<String> localeName;

    public ItemLocalePreviewViewModel(Context context, LocalePreview localePreview, BaseSelectViewModel.DataListener dataListener) {
        this.context = context;
        this.localePreview = localePreview;
        this.dataListener = dataListener;
        localeName = new ObservableField<String>();
        countForChoice = new ObservableInt();


        localeName.set(localePreview.getName());
        countForChoice.set(localePreview.getCountLocalesForChoice());

    }

    public ObservableField<String> getLocaleName() {
        return localeName;
    }


    public ObservableInt getCountForChoice() {
        return countForChoice;
    }

    public void setCountForChoice(ObservableInt countForChoice) {
        this.countForChoice = countForChoice;
    }

    public void setLocaleName(ObservableField<String> localeName) {
        this.localeName = localeName;
    }

    public LocalePreview getLocalePreview() {
        return localePreview;
    }

    public void setLocalePreview(LocalePreview localePreview) {
        this.localePreview = localePreview;
        localeName.set(localePreview.getName());
        countForChoice.set(localePreview.getCountLocalesForChoice());
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void onClick(View v) {
        switch (dataListener.getLevelLocation()) {
            case Constants.LEVEL_LOCATION:
                dataListener.setLevelLocation(Constants.LEVEL_DISTRICT);
                dataListener.loadLocationAdapterData(localePreview.getId());
                break;
            case Constants.LEVEL_DISTRICT:
                dataListener.setLevelLocation(Constants.LEVEL_CITY);
                dataListener.loadLocationAdapterData(localePreview.getId());
                break;
            case Constants.LEVEL_CITY:
                City currentCity = null;
                try {
                    currentCity = CoordinateService.getCityById(localePreview.getId());
                } catch (ServiceException e) {
                    e.printStackTrace();
                    return;
                }


                Intent intent = new Intent(context, ResultActivity.class);

                intent.putExtra(Constants.LATITUDE, String.valueOf(currentCity.getCoordinate().getLatitude()));
                intent.putExtra(Constants.LONGITUDE, String.valueOf(currentCity.getCoordinate().getLongitude()));
                intent.putExtra(Constants.CITY,localePreview.getName());

                context.startActivity(intent);
                break;
        }
    }

    @Override
    public void destroy() {
        context = null;
    }


}
