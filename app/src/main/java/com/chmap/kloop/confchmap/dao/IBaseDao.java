package com.chmap.kloop.confchmap.dao;

import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.District;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.entity.Locale;
import com.chmap.kloop.confchmap.entity.LocalePreview;
import com.chmap.kloop.confchmap.entity.NodeTableLocale;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;
import com.chmap.kloop.confchmap.entity.PolutionLevel;

import java.io.DataInput;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kloop on 14.12.2015.
 */
public interface IBaseDao {
    ArrayList<City> getCitiesByCoordinate(Coordinate coordinate) throws DaoException;

    City getCityById(int id) throws DaoException;

    LocalePreview getCountryLocalePreview(int id) throws DaoException;

    LocalePreview getLocaleLocalePreview(int id) throws DaoException;

    LocalePreview getDistrictLocalePreview(int id) throws DaoException;

    ArrayList<NodeTableLocale> getNodesFromLocaleTable() throws DaoException;

    ArrayList<NodeTableMaps> getNodesFromMapsTable() throws DaoException;

    PolutionLevel getPolutionLevel(int color, int groupId) throws DaoException;

    ArrayList<LocalePreview> getLocalesByCountry(int id) throws DaoException;

    ArrayList<LocalePreview> getDistrictsByLocale(int id) throws DaoException;

    ArrayList<LocalePreview> getCitiesByDistrict(int id) throws DaoException;

    ArrayList<String> getRecomendations(PolutionLevel startLevel) throws DaoException;

    ArrayList<Lghp> getLghp() throws DaoException;

    String getVillageSovietById(int id) throws DaoException;

    String getDistrictById(int id) throws DaoException;

    List<LghpCutting> getLghpCutting(int id) throws  DaoException;


}


