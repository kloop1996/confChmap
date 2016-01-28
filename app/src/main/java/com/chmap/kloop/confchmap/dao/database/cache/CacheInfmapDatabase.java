package com.chmap.kloop.confchmap.dao.database.cache;

import com.chmap.kloop.confchmap.dao.exception.DaoException;
import com.chmap.kloop.confchmap.dao.factory.DaoFactory;
import com.chmap.kloop.confchmap.entity.NodeTableLocale;
import com.chmap.kloop.confchmap.entity.NodeTableMaps;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kloop on 15.12.2015.
 */
public class CacheInfmapDatabase {
    private static HashMap<Integer, String> localeNames;
    private static HashMap<Integer, String> fileLocalesNames;

    private static ArrayList<NodeTableLocale> tableLocale;
    private static ArrayList<NodeTableMaps> tableMaps;

    public static String getLocaleFileName(int key) {
        if (fileLocalesNames == null) {
            fileLocalesNames = new HashMap<Integer, String>();

            fileLocalesNames.put(1, "brest");
            fileLocalesNames.put(3, "gomel");
            fileLocalesNames.put(4, "grodno");
            fileLocalesNames.put(5, "minsk");
            fileLocalesNames.put(6, "mogilev");
        }
        return fileLocalesNames.get(key);
    }

    public static String getLocaleName(int key) {
        if (localeNames == null) {
            localeNames = new HashMap<Integer, String>();

            localeNames.put(1, "Брестcкая");
            localeNames.put(3, "Гомельская");
            localeNames.put(4, "Гродненская");
            localeNames.put(5, "Минская");
            localeNames.put(6, "Могилевская");
        }

        return localeNames.get(key);
    }

    public static ArrayList<NodeTableMaps> getTableMaps() throws DaoException {
        try {
            if (tableMaps == null) {
                tableMaps = DaoFactory.getDaoFactory().getBaseDao().getNodesFromMapsTable();
            }
        } catch (DaoException ex) {
            throw ex;
        }
        return tableMaps;
    }



    public static ArrayList<NodeTableLocale> getTableLocale() throws DaoException {
        try {
            if (tableLocale == null) {
                tableLocale = DaoFactory.getDaoFactory().getBaseDao().getNodesFromLocaleTable();
            }
        } catch (DaoException ex) {
            throw ex;
        }
        return tableLocale;
    }

}
