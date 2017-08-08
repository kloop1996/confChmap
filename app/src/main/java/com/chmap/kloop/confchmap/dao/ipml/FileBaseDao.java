package com.chmap.kloop.confchmap.dao.ipml;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chmap.kloop.confchmap.dao.database.ExternalDbOpenHelper;
import com.chmap.kloop.confchmap.entity.City;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.dao.IBaseDao;
import com.chmap.kloop.confchmap.dao.database.DataBaseRepository;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.entity.District;
import com.chmap.kloop.confchmap.entity.Lghp;
import com.chmap.kloop.confchmap.entity.LghpCutting;
import com.chmap.kloop.confchmap.entity.Locale;
import com.chmap.kloop.confchmap.entity.LocalePreview;
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
                    "name", "lat", "long", "idOfDistrict","idOfVilllageSoviet"}, "(" + Double.toString(dlat) + " < lat) AND (" + Double.toString(ulat) + " > lat) AND (" + Double.toString(llong) + " < long) AND (" + Double.toString(rlong) + " > long)", null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    City city = new City();
                    Coordinate currentCordinate = new Coordinate();
                    city.setName(cursor.getString(0));

                    currentCordinate.setLatitude(cursor.getDouble(1));
                    currentCordinate.setLongitude(cursor.getDouble(2));

                    city.setIdDistrtict(cursor.getInt(3));

                    city.setIdOfVillageSoviet(cursor.getInt(4));
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
    public City getCityById(int id) throws DaoException {
        City city =new City();

        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("cities", new String[]{"_id",
                    "name","idOfDistrict","lat","long"},"_id = "+id, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    city.setName(cursor.getString(1));
                    city.setIdDistrtict(cursor.getInt(2));
                    city.setCoordinate(new Coordinate(cursor.getDouble(3),cursor.getDouble(4)));
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error Access to Database", ex);
        } finally {
            if (cursor != null)
                cursor.close();

        }


        return city;
    }

    @Override
    public LocalePreview getCountryLocalePreview(int id) throws DaoException{
        LocalePreview localePreview = null;
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("countries", new String[]{"_id",
                    "name"},"_id = "+id, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {

                    localePreview = new LocalePreview(cursor.getInt(0),cursor.getString(1));


                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error Access to Database", ex);
        } finally {
            if (cursor != null)
                cursor.close();

        }

        return localePreview;
    }

    @Override
    public LocalePreview getLocaleLocalePreview(int id) throws DaoException{
        LocalePreview localePreview = null;
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("locales", new String[]{"_id",
                    "name"},"_id = "+id, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {

                    localePreview = new LocalePreview(cursor.getInt(0),cursor.getString(1));


                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error Access to Database", ex);
        } finally {
            if (cursor != null)
                cursor.close();

        }

        return localePreview;
    }

    @Override
    public LocalePreview getDistrictLocalePreview(int id) throws DaoException {
        LocalePreview localePreview = null;
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("districts", new String[]{"_id",
                    "name"},"_id = "+id, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {

                    localePreview = new LocalePreview(cursor.getInt(0),cursor.getString(1));


                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            throw new DaoException("Error Access to Database", ex);
        } finally {
            if (cursor != null)
                cursor.close();

        }

        return localePreview;
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

    @Override
    public ArrayList<LocalePreview> getLocalesByCountry(int id) throws DaoException {

        ArrayList<LocalePreview> result = new ArrayList<LocalePreview>();
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("locales", new String[]{"_id",
                    "name","localesForChoice"},"idOfCountry = "+id, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {

                    LocalePreview localePreview = new LocalePreview(cursor.getInt(0),cursor.getString(1));
                    localePreview.setCountLocalesForChoice(cursor.getInt(2));
                    result.add(localePreview);

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
    public ArrayList<LocalePreview> getDistrictsByLocale(int id) throws DaoException {
        ArrayList<LocalePreview> result = new ArrayList<LocalePreview>();
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("districts", new String[]{"_id",
                    "name","countForChoice"},"idOfLocale = "+id, null, null, null, null);

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    LocalePreview localePreview = new LocalePreview(cursor.getInt(0),cursor.getString(1));
                    localePreview.setCountLocalesForChoice(cursor.getInt(2));
                    result.add(localePreview);
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
    public ArrayList<LocalePreview> getCitiesByDistrict(int id) throws DaoException {
        ArrayList<LocalePreview> result = new ArrayList<LocalePreview>();
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("cities", new String[]{"_id",
                    "name"},"idOfDistrict = "+id, null, null, null, "name");

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    LocalePreview localePreview = new LocalePreview(cursor.getInt(0),cursor.getString(1));
                    result.add(localePreview);
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
    public ArrayList<String> getRecomendations(PolutionLevel polutionLevel) throws DaoException {
        ArrayList<String> result = new ArrayList<String>();
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("recommend", new String[]{
                    "name"},"max_level >= "+polutionLevel.getEndValue(), null, null, null, "name");

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    result.add(cursor.getString(0));
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
    public ArrayList<Lghp> getLghp() throws DaoException {
        ArrayList<Lghp> result = new ArrayList<Lghp>();
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("lghp", new String[]{
                    "_id","name","latitude","longitude","idOfDistrict"},null, null, null, null, "_id");

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {
                    Lghp lghp = new Lghp();

                    lghp.setId(cursor.getInt(0));
                    lghp.setName(cursor.getString(1));
                    lghp.setCoordinate(new Coordinate(cursor.getDouble(2),cursor.getDouble(3)));
                    lghp.setIdOfDistrict(cursor.getInt(4));


                    result.add(lghp);
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
    public String getDistrictById(int id) throws DaoException {
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;
        String result = null;

        ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
        dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
        cursor = dbOfCordinates.query("districts", new String[]{
                "name"}, "_id = " + id, null, null, null, null);

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

    @Override
    public List<LghpCutting> getLghpCutting(int id) throws DaoException {
        ArrayList<LghpCutting> result = new ArrayList<LghpCutting>();
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;

        try {
            ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
            dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
            cursor = dbOfCordinates.query("lghp_cutting", new String[]{"_id",
                    "name","soil","locality_description","landscape_description","date_launch"},"idOfLghp = "+id, null, null, null, "_id");

            cursor.moveToFirst();

            if (!cursor.isAfterLast()) {
                do {

                    LghpCutting lghpCutting = new LghpCutting();
                    lghpCutting.setId(cursor.getInt(0));
                    lghpCutting.setName(cursor.getString(1));
                    lghpCutting.setLocalityDescription(cursor.getString(2));
                    lghpCutting.setLandscapeDescription(cursor.getString(3));
                    lghpCutting.setDateLaunch(cursor.getString(4));

                    result.add(lghpCutting);
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
    public String getVillageSovietById(int id) throws DaoException {
        SQLiteDatabase dbOfCordinates = null;
        Cursor cursor = null;
        String result = null;

        ExternalDbOpenHelper dbOpenHelper = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES);
        dbOfCordinates = DataBaseRepository.getDbByTag(DataBaseRepository.CORDINATES).openDataBase();
        cursor = dbOfCordinates.query("village_soviets", new String[]{
                "name"}, "_id = " + id, null, null, null, null);

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

}
