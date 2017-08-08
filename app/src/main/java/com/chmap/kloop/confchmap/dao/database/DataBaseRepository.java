package com.chmap.kloop.confchmap.dao.database;

import com.chmap.kloop.confchmap.ChmapApplication;
import com.chmap.kloop.confchmap.view.activity.MainActivity;

/**
 * Created by kloop on 15.12.2015.
 */
public class DataBaseRepository {
    private static volatile ExternalDbOpenHelper cordinates;
    private static volatile ExternalDbOpenHelper infmap;
    private static volatile ExternalDbOpenHelper chmapdb;

    public final static String CORDINATES ="cordinates";
    public final static String INFMAP ="infmap";
    public final static String CHMAPDB ="chmapdb";

    public static synchronized ExternalDbOpenHelper getDbByTag(String tag){

            switch (tag)
            {
                case CORDINATES:
                    if (cordinates==null)
                        cordinates=new ExternalDbOpenHelper(ChmapApplication.getInstance(),"cordinates.sqlite3");
                    return  cordinates;

                case INFMAP:
                    if (infmap==null)
                        infmap=new ExternalDbOpenHelper(ChmapApplication.getInstance(),"infmap.sqlite3");
                    return infmap;

                case CHMAPDB:
                    if (chmapdb==null)
                        chmapdb=new ExternalDbOpenHelper(ChmapApplication.getInstance(),"chmapdb.sqlite3");
                    return chmapdb;
            }

        return null;
    }
}
