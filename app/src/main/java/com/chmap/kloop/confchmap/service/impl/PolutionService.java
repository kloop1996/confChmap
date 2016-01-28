package com.chmap.kloop.confchmap.service.impl;

import com.chmap.kloop.confchmap.dao.database.cache.CacheInfmapDatabase;
import com.chmap.kloop.confchmap.entity.Coordinate;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;
import com.chmap.kloop.confchmap.entity.Polution;
import com.chmap.kloop.confchmap.entity.PolutionLevel;
import com.chmap.kloop.confchmap.entity.PolutionTypeName;
import com.chmap.kloop.confchmap.service.exception.ServiceException;

import java.util.ArrayList;

/**
 * Created by kloop on 15.12.2015.
 */
public class PolutionService {
    public static ArrayList<Polution> getAllPolutionByCordinate(Coordinate coordinate) throws ServiceException{
        ArrayList<Polution> result = new ArrayList<Polution>();

        int idOfLocale=MapProviderService.getIdOfLocale(coordinate);
        ArrayList<NodeTableMaps> tableMaps=MapProviderService.getMapsById(idOfLocale);

        try {
            for(NodeTableMaps nodeTableMaps:tableMaps) {

                Polution currentPolution = new Polution();

                int x=MapProviderService.getXByLong(idOfLocale, nodeTableMaps.getYear(), coordinate.getLongitude());
                int y=MapProviderService.getYByLat(idOfLocale, nodeTableMaps.getYear(), coordinate.getLatitude());

               //int color = MapProviderService.getColorByCoordinate(MapProviderService.getMapByTag(CacheInfmapDatabase.getLocaleFileName(idOfLocale)+"_"+Integer.toString(nodeTableMaps.getYear())+".png"), x, y);

                int color=MapProviderService.getColorByCoordinate(MapProviderService.getMapByTag(CacheInfmapDatabase.getLocaleFileName(idOfLocale)+"_"+Integer.toString(nodeTableMaps.getYear())+".png", x, y));

                currentPolution.setLevel(getPolutionByColor(color));
                currentPolution.setYear(nodeTableMaps.getYear());
                currentPolution.setType(1);

                result.add(currentPolution);
            }
        } catch (ServiceException ex) {
            throw ex;
        }

        return result;
    }

    private static PolutionLevel getPolutionByColor(int color){

        PolutionLevel polutionLevel=new PolutionLevel();

            switch (color){
                case 0xffeef3c1:
                    polutionLevel.setStartValue(0);
                    polutionLevel.setEndValue(0.1);
                    break;
                case 0xfffffbc8:
                    polutionLevel.setStartValue(0.1);
                    polutionLevel.setEndValue(0.2);
                    break;
                case 0xfffedbb4:
                    polutionLevel.setStartValue(0.2);
                    polutionLevel.setEndValue(0.5);
                    break;
                case 0xfff8b5b2:
                    polutionLevel.setStartValue(0.5);
                    polutionLevel.setEndValue(0.1);
                    break;

                case 0xffea95a4:
                    polutionLevel.setStartValue(1);
                    polutionLevel.setEndValue(5);
                    break;

                case 0xfff27180:
                    polutionLevel.setStartValue(5);
                    polutionLevel.setEndValue(15);
                    break;

                case 0xffd74c64:
                    polutionLevel.setStartValue(15);
                    polutionLevel.setEndValue(40);
                    break;

                case  0xffbe106e:
                    polutionLevel.setStartValue(40);
                    polutionLevel.setEndValue(40);
                    break;
            }


        return  polutionLevel;
    }

}
