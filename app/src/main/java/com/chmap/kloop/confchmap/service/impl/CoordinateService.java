package com.chmap.kloop.confchmap.service.impl;

import com.chmap.kloop.confchmap.Constants;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.District;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.entity.Locale;
import com.chmap.kloop.confchmap.entity.LocalePreview;
import com.chmap.kloop.confchmap.service.comparator.SortCityByDistance;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ServiceConfigurationError;

/**
 * Created by kloop on 15.12.2015.
 */
public class CoordinateService {
    public static ArrayList<City> getNearCities(Coordinate coordinate) throws ServiceException {
        ArrayList<City> result = null;

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getCitiesByCoordinate(coordinate);
            for (City city : result) {
                city.setDistance(calculateDistance(city.getCoordinate(), coordinate));
            }
            Collections.sort(result, new SortCityByDistance());

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
        ArrayList<City> cities;
        cities = getNearCities(cordinate);

        int idDistrict = 0;

        for (City city : cities) {
            if (city.getIdDistrtict() != 0) {
                idDistrict = city.getIdDistrtict();
                break;
            }

        }
        if (idDistrict != 0) {
            try {
                result = DaoFactory.getDaoFactory().getBaseDao().getDistrictById(idDistrict);


            } catch (DaoException e) {
                throw new ServiceException("Define district error");
            }
        }

        if (result == null)
              result = "Не определен";

        return result;
    }

    public static String getApproximatedVillageSoviets(Coordinate cordinate) throws ServiceException {
        String result = null;
        ArrayList<City> cities;
        cities = getNearCities(cordinate);

        int idVillageSoviet = 0;

        for (City city : cities) {
            if (city.getIdOfVillageSoviet() != 0) {
                idVillageSoviet = city.getIdOfVillageSoviet();
                break;
            }

        }
        if (idVillageSoviet != 0) {
            try {
                result = DaoFactory.getDaoFactory().getBaseDao().getVillageSovietById(idVillageSoviet);
                result+=" сельсовет";

            } catch (DaoException e) {
                throw new ServiceException("Define district error");
            }
        }
        return result;
    }

    public static double calculateDistance(Coordinate currentPoint, Coordinate destPoint) throws ServiceException {
        try {
            return Math.sqrt(((destPoint.getLatitude() - currentPoint.getLatitude()) * 111) * ((destPoint.getLatitude() - currentPoint.getLatitude()) * 111) + (((destPoint.getLongitude() - currentPoint.getLongitude()) * 65.5) * ((destPoint.getLongitude() - currentPoint.getLongitude()) * 65.5)));
        } catch (Exception ex) {
            throw new ServiceConfigurationError("Calculate exception", ex);
        }
    }

    public static ArrayList<LocalePreview> getLocalesByCountry(int id) throws ServiceException {
        ArrayList<LocalePreview> result = null;

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getLocalesByCountry(id);

        } catch (DaoException e) {
            throw new ServiceException("Get locales error");
        }

        return result;
    }

    public static ArrayList<LocalePreview> getDistrictsByLocale(int id) throws ServiceException {
        ArrayList<LocalePreview> result = null;

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getDistrictsByLocale(id);

        } catch (DaoException e) {
            throw new ServiceException("Get locales error");
        }

        return result;
    }


    public static ArrayList<LocalePreview> getCitiesByDistrict(int id) throws ServiceException {
        ArrayList<LocalePreview> result = null;

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getCitiesByDistrict(id);

        } catch (DaoException e) {
            throw new ServiceException("Get locales error");
        }

        return result;
    }

    public static City getCityById(int id) throws ServiceException {
        City city = null;

        try {
            city = DaoFactory.getDaoFactory().getBaseDao().getCityById(id);

        } catch (DaoException e) {
            throw new ServiceException("Get locales error");
        }

        return city;
    }

    public static LocalePreview getCurrentLocationPreview(int id, int levelLocation) throws ServiceException {
        LocalePreview result = null;

        try {
            switch (levelLocation) {
                case Constants.LEVEL_LOCATION:
                    result = DaoFactory.getDaoFactory().getBaseDao().getLocaleLocalePreview(id);
                    break;
                case Constants.LEVEL_DISTRICT:
                    result = DaoFactory.getDaoFactory().getBaseDao().getDistrictLocalePreview(id);
                    break;
                case Constants.LEVEL_COUNTRY:
                    result = DaoFactory.getDaoFactory().getBaseDao().getCountryLocalePreview(id);
                    break;
            }
        } catch (DaoException e) {
            throw new ServiceException("Get locales error");
        }

        return result;
    }

    public static ArrayList<Lghp> getLghp() throws ServiceException{
        ArrayList<Lghp> lghpArrayList = null;

        try {
            lghpArrayList = DaoFactory.getDaoFactory().getBaseDao().getLghp();

        } catch (DaoException e) {
            throw new ServiceException("Get locales error");
        }



        return  lghpArrayList;


    }

    public static List<LghpCutting> getLghpCutting(int id) throws ServiceException{
        List<LghpCutting> lghpArrayList = null;

        try {
            lghpArrayList = DaoFactory.getDaoFactory().getBaseDao().getLghpCutting(id);

        } catch (DaoException e) {
            throw new ServiceException("Get locales error");
        }



        return  lghpArrayList;


    }

    public static String getNameDistrictById(int id) throws ServiceException {

        String result = null;

        try {
            result = DaoFactory.getDaoFactory().getBaseDao().getDistrictById(id);

        } catch (DaoException e) {
            throw new ServiceException("Get district error");
        }


        return result;
    }
}
