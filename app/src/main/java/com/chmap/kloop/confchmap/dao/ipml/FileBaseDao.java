package com.chmap.kloop.confchmap.dao.ipml;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chmap.kloop.confchmap.dao.database.ExternalDbOpenHelper;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.dao.IBaseDao;
import com.chmap.kloop.confchmap.dao.database.DataBaseRepository;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.entity.NodeTableLocale;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.service.comparator.SortCityByDistance;
import com.chmap.kloop.confchmap.service.impl.MathService;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by kloop on 15.12.2015.
 */
public class FileBaseDao implements IBaseDao {
    private final static FileBaseDao instance = new FileBaseDao();

    private FileBaseDao() {
    }

    public static FileBaseDao getInstance() {
        return instance;
    }


    @Override
    public ArrayList<City> getCitiesByCoordinate(Coordinate cordinate) throws DaoException {
        ArrayList<City> result = new ArrayList<City>();
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        double dlat, ulat, llong, rlong;

        dlat = cordinate.getLatitude() - 0.045065;
        ulat = cordinate.getLatitude() + 0.045065;

        llong = cordinate.getLongitude() - 0.075817;
        rlong = cordinate.getLongitude() + 0.075817;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("cities", new String[]{
                    "name", "lat", "long", "idOfDistrict"}, "(" + Double.toString(dlat) + " < lat) AND (" + Double.toString(ulat) + " > lat) AND (" + Double.toString(llong) + " < long) AND (" + Double.toString(rlong) + " > long)", null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    City city = new City();
                    Coordinate currentCordinate = new Coordinate();
                    city.setName(cursor.getString(0));

                    currentCordinate.setLatitude(cursor.getDouble(1));
                    currentCordinate.setLongitude(cursor.getDouble(2));

                    city.setIdDistrtict(cursor.getInt(3));

                    city.setCoordinate(currentCordinate);

                    result.add(city);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error Access to Database", ex);
        } finally {
            if (cursor != null)
                cursor.close();

        }

        return result;
    }

    @Override
    public City getNearCity(Coordinate coordinate) throws DaoException {
        List<City> cities = (getCitiesByCoordinate(coordinate));
        Collections.sort((getCitiesByCoordinate(coordinate)), new SortCityByDistance());

        if (cities.size() == 0)
            return null;

        return cities.get(0);
    }

    @Override
    public String getApproximateDistrict(Coordinate coordinate) throws DaoException {

        if (getNearCity(coordinate) == null)
            return null;

        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;
        String result = null;


        int idDistrict = getNearCity(coordinate).getIdDistrtict();

        ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
        dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
        cursor = dbOfCordinates.query("districts", new String[]{
                "name"}, "_id = " + idDistrict, null, null, null, null);

        cursor.moveToFirst();
        try {
            if (!cursor.isAfterLast()) {
                do {
                    result = cursor.getString(0);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error Access to Database", ex);
        } finally {
            if (cursor != null)
                cursor.close();

        }


        return result;

    }

    public ArrayList<NodeTableLocale> getNodesFromLocaleTable() throws DaoException {
        ArrayList<NodeTableLocale> result = new ArrayList<NodeTableLocale>();


        SQLiteDatabase dbOfInfmap = null;
        Cursor cursor = null;

        try {
            dbOfInfmap = DataBaseRepository.getDbByTag(DataBaseRepository.INFMAP).openDataBase();

            cursor = dbOfInfmap.query("locales", new String[]{
                    "_id", "LongOfA", "LatOfA", "LongfOfD", "LatOfD","id_map_circuit"}, null, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    NodeTableLocale nodeTableLocale = new NodeTableLocale();

                    nodeTableLocale.setCoordinateA(new Coordinate(cursor.getDouble(2),cursor.getDouble(1)));
                    nodeTableLocale.setCoordinateD(new Coordinate(cursor.getDouble(4), cursor.getDouble(3)));
                    nodeTableLocale.setId(cursor.getInt(0));
                    nodeTableLocale.setIdOfLocaleCircuit(cursor.getInt(5));

                    result.add(nodeTableLocale);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error access to Database", ex);
        } finally {
            cursor.close();
            // dbOfInfmap.close();
        }

        return result;
    }


    public ArrayList<NodeTableMaps> getNodesFromMapsTable() throws DaoException {
        ArrayList<NodeTableMaps> result = new ArrayList<NodeTableMaps>();

        SQLiteDatabase dbOfInfmap = null;
        Cursor cursor = null;

        try {
            dbOfInfmap = DataBaseRepository.getDbByTag(DataBaseRepository.INFMAP).openDataBase();

            cursor = dbOfInfmap.query("maps", new String[]{
                    "idOfLocale", "type", "dividerByLong", "dividerByLat", "year", "idOfGroupLevelPolution", "name","_id"}, null, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    NodeTableMaps nodeTableMap = new NodeTableMaps();

                    nodeTableMap.setIdOfLocale(cursor.getInt(0));
                    nodeTableMap.setType(cursor.getInt(1));
                    nodeTableMap.setYear(cursor.getInt(4));

                    nodeTableMap.setDividerByLong(cursor.getDouble(2));
                    nodeTableMap.setDividerByLat(cursor.getDouble(3));

                    nodeTableMap.setIdOfGroupLevelPolution(cursor.getInt(5));

                    nodeTableMap.setName(cursor.getString(6));
                    nodeTableMap.setId(cursor.getInt(7));

                    result.add(nodeTableMap);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            throw new DaoException("Error access to Database", ex);
        } finally {
            cursor.close();
            // dbOfInfmap.close();
        }

        return result;
    }

    @Override
    public PolutionLevel getPolutionLevel(int color, int groupId) throws DaoException {
        PolutionLevel polutionLevel = new PolutionLevel();


        SQLiteDatabase dbOfInfmap = null;
        Cursor cursor = null;

        try {
            dbOfInfmap = DataBaseRepository.getDbByTag(DataBaseRepository.INFMAP).openDataBase();
            long unsignedValueColor = MathService.getUnsignedInt(color);

            cursor = dbOfInfmap.query("polution_level", new String[]{
                    "startValue", "endValue"}, "(group_id = " + groupId + ") AND (color = "+unsignedValueColor+")", null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    polutionLevel.setStartValue(cursor.getDouble(0));
                    polutionLevel.setEndValue(cursor.getDouble(1));
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            throw new DaoException("Error access to Database", ex);
        } finally {
            cursor.close();
        }

        return polutionLevel;
    }

}
