package com.chmap.kloop.confchmap.service.impl;

import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.ServiceConfigurationError;

/**
 * Created by kloop on 15.12.2015.
 */
public class CoordinateService {
    public static ArrayList<City> getNearCities(Coordinate coordinate) throws ServiceException {
        ArrayList<City> result = new ArrayList<City>();

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getCitiesByCoordinate(coordinate);
            for (City city : result) {
                city.setDistance(calculateDistance(city.getCoordinate(), coordinate));
            }


        } catch (DaoException ex) {
            throw new ServiceException("Service error", ex);
        }
        return result;
    }

    public static String getLocale(Coordinate coordinate) throws ServiceException {
        String result = "";

        return result;
    }

    public static String getApproximatedDistrict(Coordinate cordinate) throws ServiceException {
        String result = null;

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getApproximateDistrict(cordinate);
            if (result == null)
                result = "Не определен";
        } catch (DaoException e) {
            throw new ServiceException("Define district error");
        }

        return result;
    }

    public static double calculateDistance(Coordinate currentPoint, Coordinate destPoint) throws ServiceException {
        try {
            return Math.sqrt(((destPoint.getLatitude() - currentPoint.getLatitude()) * 111) * ((destPoint.getLatitude() - currentPoint.getLatitude()) * 111) + (((destPoint.getLongitude() - currentPoint.getLongitude()) * 65.5) * ((destPoint.getLongitude() - currentPoint.getLongitude()) * 65.5)));
        } catch (Exception ex) {
            throw new ServiceConfigurationError("Calulate exception", ex);
        }
    }
}
