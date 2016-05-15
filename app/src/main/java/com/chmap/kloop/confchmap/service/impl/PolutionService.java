package com.chmap.kloop.confchmap.service.impl;

import android.util.Log;

import com.chmap.kloop.confchmap.dao.database.cache.CacheInfmapDatabase;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.mikepenz.materialize.util.SystemUtils;

import java.util.ArrayList;

/**
 * Created by kloop on 15.12.2015.
 */
public class PolutionService {
    public static ArrayList<Polution> getAllPolutionByCordinate(Coordinate coordinate) throws ServiceException {
        ArrayList<Polution> result = new ArrayList<Polution>();

        long currentTime = System.currentTimeMillis();
        int idOfLocale = MapProviderService.getIdOfLocale(coordinate);
        Log.d("idOfLocale",String.valueOf(System.currentTimeMillis()-currentTime));

        ArrayList<NodeTableMaps> tableMaps = MapProviderService.getMapsById(idOfLocale);

        currentTime = System.currentTimeMillis();
        try {
            for (NodeTableMaps nodeTableMaps : tableMaps) {

                Polution currentPolution = new Polution();

                int color;

                //костыль
                if (nodeTableMaps.getType() == PolutionType.SR_TYPE && nodeTableMaps.getIdOfLocale()!=7) {
                    int x = MapProviderService.getXByLong(3, nodeTableMaps.getName(), coordinate.getLongitude());
                    int y = MapProviderService.getYByLat(6, nodeTableMaps.getName(), coordinate.getLatitude());

                    color = MapProviderService.getColorByCoordinate(MapProviderService.getMapByTag(nodeTableMaps.getName(), x, y));
                    currentPolution.setLevel(getPolution(color,nodeTableMaps.getIdOfGroupLevelPolution()));
                } else {
                    int x = MapProviderService.getXByLong(idOfLocale, nodeTableMaps.getName(), coordinate.getLongitude());
                    int y = MapProviderService.getYByLat(idOfLocale, nodeTableMaps.getName(), coordinate.getLatitude());


                    color = MapProviderService.getColorByCoordinate(MapProviderService.getMapByTag(nodeTableMaps.getName(), x, y));

                    currentTime = System.currentTimeMillis();
                    currentPolution.setLevel(getPolution(color,nodeTableMaps.getIdOfGroupLevelPolution()));
                    Log.d("getLevel",String.valueOf(System.currentTimeMillis()-currentTime));
                }


                currentPolution.setYear(nodeTableMaps.getYear());
                currentPolution.setType(nodeTableMaps.getType());

                result.add(currentPolution);
            }
        } catch (ServiceException ex) {
            throw ex;
        }
        Log.d("Bitmap",String.valueOf(System.currentTimeMillis()-currentTime));
        return result;
    }

    private static PolutionLevel getPolution(int color, int groupLevelPolution) throws ServiceException {
        PolutionLevel polutionLevel;

        try {
            polutionLevel = DaoFactory.getDaoFactory().getBaseDao().getPolutionLevel(color, groupLevelPolution);
        } catch (DaoException ex) {
            throw new ServiceException("Error calcylate position", ex);
        }

        return polutionLevel;
    }

}
