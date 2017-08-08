package com.chmap.kloop.confchmap.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.service.impl.CoordinateService;
import com.chmap.kloop.confchmap.service.impl.PolutionService;
import com.chmap.kloop.confchmap.service.impl.RecommendationService;
import com.chmap.kloop.confchmap.util.StringUtil;

import java.util.List;

/**
 * Created by kloop1996 on 15.04.2017.
 */

public class LghpInfoActivityViewModel implements ViewModel {
    private Context context;
    private DataListener dataListener;
    private Lghp lghp;
    private List<LghpCutting> lghpCuttings;

    private ObservableField<String> pointInfoField = new ObservableField<String>();
    private ObservableField<Coordinate> coordinateField = new ObservableField<Coordinate>();

    public LghpInfoActivityViewModel(Context context,  Lghp lghp,DataListener dataListener) {
        this.context = context;
        this.lghp = lghp;
        this.dataListener = dataListener;

        coordinateField.set(lghp.getCoordinate());
        try {
            pointInfoField.set(CoordinateService.getApproximatedVillageSoviets(lghp.getCoordinate()));
            lghpCuttings = CoordinateService.getLghpCutting(lghp.getId());
        } catch (ServiceException e) {
            e.printStackTrace();

        }
        dataListener.onLghpCuttingChanged(lghpCuttings);
    }

    public ObservableField<Coordinate> getCoordinateField() {
        return coordinateField;
    }
    public ObservableField<String> getPointInfoField() {
        return pointInfoField;
    }



    @Override
    public void destroy() {
        context = null;
    }

    public interface DataListener{
        void onLghpCuttingChanged(List<LghpCutting> lghpCuttingList);

    }
}
