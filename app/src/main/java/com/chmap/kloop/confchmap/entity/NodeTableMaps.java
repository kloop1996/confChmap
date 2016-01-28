package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop on 15.12.2015.
 */
public class NodeTableMaps {
    private int idOfLocale;
    private int year;
    private int type;
    private double dividerByLong;
    private double getDividerByLat;

    public int getIdOfLocale(){return idOfLocale;}
    public int getYear(){return  year;}
    public int getType(){return type;}
    public double getDividerByLong(){return  dividerByLong;}
    public double getDividerByLat(){return getDividerByLat;}

    public void setIdOfLocale(int idOfLocale){this.idOfLocale=idOfLocale;}
    public void setYear(int year){this.year=year;}
    public void setType(int type){this.type=type;}
    public void setDividerByLong(double dividerByLong){this.dividerByLong=dividerByLong;}
    public void setDividerByLat(double dividerByLat){this.getDividerByLat=dividerByLat;}
}
