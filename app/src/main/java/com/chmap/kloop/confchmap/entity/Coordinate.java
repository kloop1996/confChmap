package com.chmap.kloop.confchmap.entity;

import java.io.Serializable;

/**
 * Created by kloop on 15.12.2015.
 */
public class Coordinate implements Serializable {

    private double longitude;
    private double latitude;

    public Coordinate(double latitude,double longitude){
        this.longitude=longitude;
        this.latitude=latitude;
    }

    public Coordinate(){}

    public void setLongitude(double longitude){
        this.longitude=longitude;
    }

    public void setLatitude(double latitude){
        this.latitude=latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
