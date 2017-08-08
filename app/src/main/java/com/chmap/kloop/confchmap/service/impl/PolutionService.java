package com.chmap.kloop.confchmap.service.impl;

import android.content.Context;
import android.util.Log;

import com.chmap.kloop.confchmap.dao.database.cache.CacheInfmapDatabase;
import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.service.exception.ServiceException;
import com.chmap.kloop.confchmap.util.StringUtil;
import com.mikepenz.materialize.util.SystemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kloop on 15.12.2015.
 */
public class PolutionService {
    public static ArrayList<Polution> getAllPolutionByCordinate(Coordinate coordinate) throws ServiceException {
        ArrayList<Polution> result = new ArrayList<Polution>();

        long currentTime = System.currentTimeMillis();
        int idOfLocale = MapProviderService.getIdOfLocale(coordinate);
        Log.d("idOfLocale", String.valueOf(System.currentTimeMillis() - currentTime));

        ArrayList<NodeTableMaps> tableMaps = MapProviderService.getMapsById(idOfLocale);

        currentTime = System.currentTimeMillis();
        try {
            for (NodeTableMaps nodeTableMaps : tableMaps) {

                Polution currentPolution = new Polution();

                int color;

                //костыль
                if (nodeTableMaps.getType() == PolutionType.SR_TYPE && nodeTableMaps.getIdOfLocale() != 7) {
                    int x = MapProviderService.getXByLong(3, nodeTableMaps.getName(), coordinate.getLongitude());
                    int y = MapProviderService.getYByLat(6, nodeTableMaps.getName(), coordinate.getLatitude());

                    color = MapProviderService.getColorByCoordinate(MapProviderService.getMapByTag(nodeTableMaps.getName(), x, y));
                    currentPolution.setLevel(getPolution(color, nodeTableMaps.getIdOfGroupLevelPolution()));
                } else {
                    int x = MapProviderService.getXByLong(idOfLocale, nodeTableMaps.getName(), coordinate.getLongitude());
                    int y = MapProviderService.getYByLat(idOfLocale, nodeTableMaps.getName(), coordinate.getLatitude());


                    color = MapProviderService.getColorByCoordinate(MapProviderService.getMapByTag(nodeTableMaps.getName(), x, y));

                    currentTime = System.currentTimeMillis();
                    currentPolution.setLevel(getPolution(color, nodeTableMaps.getIdOfGroupLevelPolution()));
                    Log.d("getLevel", String.valueOf(System.currentTimeMillis() - currentTime));
                }


                currentPolution.setYear(nodeTableMaps.getYear());
                currentPolution.setType(nodeTableMaps.getType());

                result.add(currentPolution);
            }
        } catch (ServiceException ex) {
            throw ex;
        }
        Log.d("Bitmap", String.valueOf(System.currentTimeMillis() - currentTime));
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


    public static String getInfoPolutionMessage(List<Polution> polutionList) throws ServiceException {
        StringBuilder stringBuilder = new StringBuilder();

        for (Polution polution : polutionList) {
            if ((polution.getYear() != 1986 && polution.getYear() != 2009) && polution.getType() == PolutionType.CS_TYPE)
                continue;

            stringBuilder.append(String.format("%s: %.2f-%.2f ки/км2", PolutionType.getTypeNamePolution(polution.getType()), polution.getLevel().getStartValue(), polution.getLevel().getEndValue()));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public static String getStatus(List<Polution> polutions) {
        String result = "";

        for (Polution polution: polutions){
           if (polution.getYear()==1986 ||polution.getYear()== 2016||polution.getYear()==2009){
               switch ((int)Math.round(polution.getLevel().getStartValue())){
                   case 0:
                       result="Условно чисто";
                       break;
                   case 1:
                       result="Зона с периодическим радиационным контролем";
                       break;
                   case 5:
                       result="Зона с правом на отселение";
                       break;
                   case 15:
                       result="Контрольно-пропускной режим";
                       break;
                   case 40:
                       result="Контрольно-пропускной режим";
                       break;
               }
           }
        }

        return result;
    }

}
