package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.R;
import com.chmap.kloop.confchmap.entity.LocalePreview;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;

import java.util.List;

/**
 * Created by kloop1996 on 04.07.2016.
 */
public class BaseSelectViewModel implements ViewModel {

    private Context context;

    private ObservableInt recyclerViewVisibility;
    private ObservableInt progressVisibility;
    private ObservableField<String> locationSelectMessage;

    public ObservableField<String> getCurrentLocationMessage() {
        return currentLocationMessage;
    }

    public void setCurrentLocationMessage(ObservableField<String> currentLocationMessage) {
        this.currentLocationMessage = currentLocationMessage;
    }

    private ObservableField<String> currentLocationMessage;

    private DataListener dataListener;

    public BaseSelectViewModel(Context context,DataListener dataListener) {
        this.context = context;
        this.dataListener = dataListener;

        locationSelectMessage = new ObservableField<String>();
        currentLocationMessage = new ObservableField<String>();

        recyclerViewVisibility = new ObservableInt(View.INVISIBLE);
        progressVisibility = new ObservableInt(View.VISIBLE);


    }

    public ObservableInt getProgressVisibility() {
        return progressVisibility;
    }

    public void setProgressVisibility(ObservableInt progressVisibility) {
        this.progressVisibility = progressVisibility;
    }

    public ObservableInt getRecyclerViewVisibility() {
        return recyclerViewVisibility;
    }

    public void setRecyclerViewVisibility(ObservableInt recyclerViewVisibility) {
        this.recyclerViewVisibility = recyclerViewVisibility;
    }

    public ObservableField<String> getLocationSelectMessage() {
        return locationSelectMessage;
    }

    public void loadLocalesPreview(int id){
        try {
            switch (dataListener.getLevelLocation()){
                case Constants.LEVEL_LOCATION:
                    loadCurrentLocationMessage(id,Constants.LEVEL_COUNTRY);
                    dataListener.onLocationChanged(CoordinateService.getLocalesByCountry(id));
                    locationSelectMessage.set(context.getString(R.string.locale));
                    break;
                case Constants.LEVEL_DISTRICT:
                    loadCurrentLocationMessage(id,Constants.LEVEL_LOCATION);
                    dataListener.onLocationChanged(CoordinateService.getDistrictsByLocale(id));
                    locationSelectMessage.set(context.getString(R.string.district));
                    break;
                case Constants.LEVEL_CITY:
                    loadCurrentLocationMessage(id,Constants.LEVEL_DISTRICT);
                    dataListener.onLocationChanged(CoordinateService.getCitiesByDistrict(id));
                    locationSelectMessage.set(context.getString(R.string.city));
                    break;
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void destroy() {
        context = null;
    }

    public interface DataListener {
        void onLocationChanged(List<LocalePreview> localePreviews);
        void setLevelLocation(int level);
        int getLevelLocation();
        void loadLocationAdapterData(int id);
    }

    private void loadCurrentLocationMessage(int id,int levelLocation){
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(CoordinateService.getCurrentLocationPreview(id,levelLocation).getName());
            builder.append(" ");

            switch (levelLocation){
                case Constants.LEVEL_LOCATION:
                    builder.append(context.getString(R.string.locale));
                    break;
                case Constants.LEVEL_DISTRICT:
                    builder.append(context.getString(R.string.district));
                    break;
            }
            currentLocationMessage.set(builder.toString());

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}
