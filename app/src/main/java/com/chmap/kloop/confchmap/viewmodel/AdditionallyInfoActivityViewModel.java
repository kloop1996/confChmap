package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;
import android.databinding.ObservableDouble;
import android.databinding.ObservableField;

import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.entity.PolutionSet;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.service.impl.PolutionService;
import com.chmap.kloop.confchmap.service.impl.RecommendationService;
import com.chmap.kloop.confchmap.util.StringUtil;
import com.chmap.kloop.confchmap.view.activity.AdditionallyInfoActivity;

import java.util.List;

/**
 * Created by kloop1996 on 07.08.2016.
 */
public class AdditionallyInfoActivityViewModel implements ViewModel {

    private Context context;
    private ObservableField<String> recommendField;
    private ObservableField<String> pointInfoField;
    private ObservableField<String> polutionInfoField;
    private ObservableField<Coordinate> coordinateField;

    public AdditionallyInfoActivityViewModel(Context context, PolutionSet polutionSet, Coordinate coordinate) {
        this.context = context;

        recommendField = new ObservableField<String>();
        pointInfoField = new ObservableField<String>();
        polutionInfoField = new ObservableField<String>();

        coordinateField = new ObservableField<Coordinate>(coordinate);

        try {
            if (polutionSet.getPolutions() != null) {
                polutionInfoField.set(PolutionService.getInfoPolutionMessage(polutionSet.getPolutions()));
                recommendField.set(StringUtil.getRecommedationString(RecommendationService.getRecommendationByLevelPolution(polutionSet.getPolutions().get(1))));
            }
            pointInfoField.set(CoordinateService.getApproximatedVillageSoviets(coordinate));
        } catch (ServiceException e) {
            e.printStackTrace();

        }


    }

    public ObservableField<String> getPointInfoField() {
        return pointInfoField;
    }

    public void setPointInfoField(ObservableField<String> pointInfoField) {
        this.pointInfoField = pointInfoField;
    }

    public ObservableField<String> getPolutionInfoField() {
        return polutionInfoField;
    }

    public void setPolutionInfoField(ObservableField<String> polutionInfoField) {
        this.polutionInfoField = polutionInfoField;
    }

    public ObservableField<Coordinate> getCoordinateField() {
        return coordinateField;
    }

    public void setCoordinateField(ObservableField<Coordinate> coordinateField) {
        this.coordinateField = coordinateField;
    }

    public ObservableField<String> getRecommendField() {
        return recommendField;
    }


    public void setRecommendField(ObservableField<String> recommendField) {
        this.recommendField = recommendField;
    }

    @Override
    public void destroy() {
        context = null;
    }
}
