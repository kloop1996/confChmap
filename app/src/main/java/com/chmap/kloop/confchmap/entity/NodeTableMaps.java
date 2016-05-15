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
    private String name;
    private int idOfGroupLevelPolution;
    private int id;


    public String getName(){return name;}
    public int getId(){return id;}
    public int getIdOfGroupLevelPolution(){return idOfGroupLevelPolution;}
    public int getIdOfLocale(){return idOfLocale;}
    public int getYear(){return  year;}
    public int getType(){return type;}
    public double getDividerByLong(){return  dividerByLong;}
    public double getDividerByLat(){return getDividerByLat;}


    public void setId(int id){this.id = id;}
    public void setName(String name){this.name=name;}
    public void setIdOfGroupLevelPolution(int idOfGroupLevelPolution){this.idOfGroupLevelPolution=idOfGroupLevelPolution;}
    public void setIdOfLocale(int idOfLocale){this.idOfLocale=idOfLocale;}
    public void setYear(int year){this.year=year;}
    public void setType(int type){this.type=type;}
    public void setDividerByLong(double dividerByLong){this.dividerByLong=dividerByLong;}
    public void setDividerByLat(double dividerByLat){this.getDividerByLat=dividerByLat;}
}
