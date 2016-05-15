package com.chmap.kloop.confchmap.service.impl;

import android.graphics.Bitmap;

import com.chmap.kloop.confchmap.dao.database.cache.CacheInfmapDatabase;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.NodeTableLocale;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;
import com.chmap.kloop.confchmap.service.exception.ServiceException;

import java.util.ArrayList;

/**
 * Created by kloop on 15.12.2015.
 */
public class MapProviderService {
    public final static int EMPTY_COLOR = 0xffffffff;

    public static Bitmap getMapByTag(String tag) throws ServiceException {
        try {
            return DaoFactory.getDaoFactory().getMapDao().getBitmapByTag(tag);

        } catch (DaoException ex) {
            throw new ServiceException("Bitmap service error", ex);
        }
    }

    public static Bitmap getMapByTag(String tag, int x, int y) throws ServiceException {
        try {
            return DaoFactory.getDaoFactory().getMapDao().getBitmapByTag(tag, x, y);

        } catch (DaoException ex) {
            throw new ServiceException("Bitmap service error", ex);
        }
    }

    public static int getColorByCoordinate(Bitmap bitmap, int x, int y) throws ServiceException {
        try {
            return bitmap.getPixel(x, y);
        } catch (Exception ex) {
            throw new ServiceException("Extract color error", ex);
        } finally {
            bitmap.recycle();
        }
    }


    public static int getColorByCoordinate(Bitmap bitmap) throws ServiceException {
        try {
            return bitmap.getPixel(0, 0);
        } catch (Exception ex) {
            throw new ServiceException("Extract color error", ex);
        } finally {
            bitmap.recycle();
        }
    }


    public static int getXByLong(int idOfLocale, String name, double longitude) throws ServiceException {
        double aLongitude = 0;
        double divider;
        try {
            for (NodeTableLocale nodeTableLocale : CacheInfmapDatabase.getTableLocale())
                if (nodeTableLocale.getId() == idOfLocale) {
                    aLongitude = nodeTableLocale.getCoordinateA().getLongitude();
                    break;
                }
            divider = 1;
            for (NodeTableMaps nodeTableMaps : CacheInfmapDatabase.getTableMaps()) {
                if ((nodeTableMaps.getIdOfLocale() == idOfLocale) && (nodeTableMaps.getName().compareTo(name)==0)) {
                    divider = nodeTableMaps.getDividerByLong();
                }
            }
        } catch (DaoException ex) {
            throw new ServiceException("Error calculate position", ex);
        }
        return (int) ((longitude - aLongitude) / divider);
    }

    public static int getYByLat(int idOfLocale, String name, double latitude) throws ServiceException {
        double aLatitude = 0;
        double divider = 1;

        try {
            for (NodeTableLocale nodeTableLocale : CacheInfmapDatabase.getTableLocale())
                if (nodeTableLocale.getId() == idOfLocale) {
                    aLatitude = nodeTableLocale.getCoordinateA().getLatitude();
                    break;
                }

            for (NodeTableMaps nodeTableMaps : CacheInfmapDatabase.getTableMaps()) {
                if ((nodeTableMaps.getIdOfLocale() == idOfLocale) && (nodeTableMaps.getName().compareTo(name)==0)) {
                    divider = nodeTableMaps.getDividerByLat();
                }
            }
        } catch (DaoException ex) {
            throw new ServiceException("Error calcylate position", ex);
        }
        return (int) ((aLatitude - latitude) / divider);
    }

    public static int getIdOfLocale(Coordinate coordinate) throws ServiceException {
        ArrayList<NodeTableLocale> approximatedLocales = new ArrayList<NodeTableLocale>();
        int color = 0;

        int result = 0;
        try {
            for (NodeTableLocale nodeTableLocale : CacheInfmapDatabase.getTableLocale()) {
                if (((coordinate.getLongitude() >= nodeTableLocale.getCoordinateA().getLongitude()) && (coordinate.getLongitude() <= nodeTableLocale.getCoordinateD().getLongitude())) && (coordinate.getLatitude() >= nodeTableLocale.getCoordinateD().getLatitude() && coordinate.getLatitude() <= nodeTableLocale.getCoordinateA().getLatitude()))
                    approximatedLocales.add(nodeTableLocale);
            }

            for (NodeTableLocale nodeTableLocale : approximatedLocales) {
                String mapTag = getMapTagByIdCircuit(nodeTableLocale.getIdOfLocaleCircuit());

                color = MapProviderService.getColorByCoordinate(MapProviderService.getMapByTag(mapTag, getXByLong(nodeTableLocale.getId(), mapTag, coordinate.getLongitude()), getYByLat(nodeTableLocale.getId(), mapTag, coordinate.getLatitude())));
                if (color != EMPTY_COLOR) {
                    result=nodeTableLocale.getId();
                }
            }
        } catch (DaoException ex) {

        }
        return result;
    }

    public static ArrayList<NodeTableMaps> getMapsById(int id) throws ServiceException {
        ArrayList<NodeTableMaps> nodeTableMaps = new ArrayList<NodeTableMaps>();

        try {
            for (NodeTableMaps tableMaps : CacheInfmapDatabase.getTableMaps()) {
                if (tableMaps.getIdOfLocale() == id) {
                    nodeTableMaps.add(tableMaps);
                }
            }

        } catch (DaoException ex) {
            throw new ServiceException("Error", ex);
        }

        return nodeTableMaps;
    }

    private static String getMapTagByIdCircuit(int id) throws ServiceException {
        String result= null;
        try {
            for (NodeTableMaps nodeTableMaps : CacheInfmapDatabase.getTableMaps()){
                if (nodeTableMaps.getId() == id){
                    result = nodeTableMaps.getName();
                    break;
                }
            }
        }catch (DaoException ex){
            throw new ServiceException("Error define circuit map",ex);
        }

        return result;
    }
}
