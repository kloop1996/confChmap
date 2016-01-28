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


import java.util.ArrayList;

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
                    "name", "lat", "long"}, "(" + Double.toString(dlat) + " < lat) AND (" + Double.toString(ulat) + " > lat) AND (" + Double.toString(llong) + " < long) AND (" + Double.toString(rlong) + " > long)", null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    City city = new City();
                    Coordinate currentCordinate = new Coordinate();
                    city.setName(cursor.getString(0));

                    currentCordinate.setLatitude(cursor.getDouble(1));
                    currentCordinate.setLongitude(cursor.getDouble(2));

                    city.setCoordinate(currentCordinate);

                    result.add(city);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error Access to Database", ex);
        } finally {
            if (cursor != null)
                cursor.close();

            //if (dbOfCordinates != null)
               // dbOfCordinates.close();

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
                    "_id", "LongOfA", "LatOfA", "LongfOfD", "LatOfD"}, null, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    NodeTableLocale nodeTableLocale = new NodeTableLocale();

                    nodeTableLocale.setCoordinateA(new Coordinate(Double.parseDouble(cursor.getString(2)), Double.parseDouble(cursor.getString(1))));
                    nodeTableLocale.setCoordinateD(new Coordinate(Double.parseDouble(cursor.getString(4)), Double.parseDouble(cursor.getString(3))));
                    nodeTableLocale.setId(Integer.parseInt(cursor.getString(0)));

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

            cursor=dbOfInfmap.query("maps", new String[]{
                    "idOfLocale", "type", "dividerByLong", "dividerByLat", "year"}, null, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    NodeTableMaps nodeTableMap = new NodeTableMaps();

                    nodeTableMap.setIdOfLocale(Integer.parseInt(cursor.getString(0)));
                    nodeTableMap.setType(Integer.parseInt(cursor.getString(1)));
                    nodeTableMap.setYear(Integer.parseInt(cursor.getString(4)));

                    nodeTableMap.setDividerByLong(Double.parseDouble(cursor.getString(2)));
                    nodeTableMap.setDividerByLat(Double.parseDouble(cursor.getString(3)));
                    result.add(nodeTableMap);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            throw new DaoException("Error access to Database", ex);
        } finally {
            cursor.close();
           // dbOfInfmap.close();
        }

        return  result;
    }

}
