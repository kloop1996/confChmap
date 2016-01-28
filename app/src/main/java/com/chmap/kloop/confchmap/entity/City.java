package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop on 15.12.2015.
 */
public class City {
    private String name;
    private String locale;
    private String district;
    private Coordinate coordinate;
    private double distance;

    public void setName(String name){
        this.name=name;
    }

    public void setLocale(String locale){
        this.locale=locale;
    }

    public void setDistrict(String district) {
        this.district=district;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate=coordinate;
    }

    public  void setDistance(double distance){this.distance=distance;}

    public String getName(){return name;}
    public String getLocale(){return locale;}
    public String getDistrict(){return district;}
    public Coordinate getCoordinate(){return coordinate;}
    public double getDistance(){return distance;}
}
