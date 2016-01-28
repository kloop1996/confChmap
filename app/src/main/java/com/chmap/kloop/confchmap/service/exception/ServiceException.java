package com.chmap.kloop.confchmap.service.exception;

/**
 * Created by kloop on 15.12.2015.
 */
public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Exception ex) {
        super(message, ex);
    }
}
