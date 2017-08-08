package com.chmap.kloop.confchmap.service.impl;

import android.widget.ArrayAdapter;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.service.exception.ServiceException;

import java.util.ArrayList;

/**
 * Created by kloop on 17.12.2015.
 */
public class RecommendationService {
    public static ArrayList<String> getRecommendationByLevelPolution(Polution polution) throws ServiceException {
        ArrayList<String> result = new ArrayList<String>();

        switch (polution.getType()){
            case PolutionType.CS_TYPE:
                result = getRecomendationByCsLevel(polution.getLevel());
        }

        return result;
    }

    private static ArrayList<String> getRecomendationByCsLevel(PolutionLevel polutionLevel) throws ServiceException {
        ArrayList<String> result = new ArrayList<String>();

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getRecomendations(polutionLevel);
        } catch (DaoException e) {
            throw new ServiceException("Error calcylate position", e);
        }

        return result;
    }
}
