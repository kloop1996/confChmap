package com.chmap.kloop.confchmap.dao.factory;

import com.chmap.kloop.confchmap.dao.IBaseDao;
import com.chmap.kloop.confchmap.dao.IMapDao;
import com.chmap.kloop.confchmap.dao.ipml.FileBaseDao;
import com.chmap.kloop.confchmap.dao.ipml.FileMapDao;

/**
 * Created by kloop on 15.12.2015.
 */
public class FileDaoFactory extends DaoFactory {

    private final static FileDaoFactory instance = new FileDaoFactory();

    private FileDaoFactory() {
    }

    public final static FileDaoFactory getInstance() {
        return instance;
    }

    @Override
    public IBaseDao getBaseDao() {
        return FileBaseDao.getInstance();
    }

    @Override
    public IMapDao getMapDao() {
        return FileMapDao.getInstance();
    }
}
