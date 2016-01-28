package com.chmap.kloop.confchmap.dao.exception;

/**
 * Created by kloop on 14.12.2015.
 */
public class DaoException extends Exception{
    private static final long serialVersionUID = 1L;

    public DaoException(String message){
        super(message);
    }

    public DaoException(String message, Exception ex){
        super(message, ex);
    }
}