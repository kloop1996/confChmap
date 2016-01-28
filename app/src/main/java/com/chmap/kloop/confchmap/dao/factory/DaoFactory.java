package com.chmap.kloop.confchmap.dao.factory;

import com.chmap.kloop.confchmap.dao.IBaseDao;
import com.chmap.kloop.confchmap.dao.IMapDao;

/**
 * Created by kloop on 15.12.2015.
 */
public abstract class DaoFactory {
    private static final String DAO_TYPE = "file";

    public static DaoFactory getDaoFactory(){
        switch (DAO_TYPE){
            case "file":
                return FileDaoFactory.getInstance();
        }
        return null;
    }

    public abstract IBaseDao getBaseDao();
    public abstract IMapDao getMapDao();
}
