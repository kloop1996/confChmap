package com.chmap.kloop.confchmap.dao;

import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.NodeTableLocale;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;
import com.chmap.kloop.confchmap.entity.PolutionLevel;

import java.util.ArrayList;

/**
 * Created by kloop on 14.12.2015.
 */
public interface IBaseDao {
    ArrayList<City> getCitiesByCoordinate(Coordinate coordinate) throws DaoException;

    City getNearCity(Coordinate coordinate) throws DaoException;

    String getApproximateDistrict(Coordinate coordinate) throws DaoException;

    ArrayList<NodeTableLocale> getNodesFromLocaleTable() throws DaoException;

    ArrayList<NodeTableMaps> getNodesFromMapsTable() throws DaoException;

    PolutionLevel getPolutionLevel(int color, int groupId) throws DaoException;
}


