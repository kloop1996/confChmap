package com.chmap.kloop.confchmap.controller;

/**
 * Created by kloop on 17.12.2015.
 */
public class ControllerException extends  Exception {
    private static final long serialVersionUID = 1L;

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Exception ex) {
        super(message, ex);
    }
}
