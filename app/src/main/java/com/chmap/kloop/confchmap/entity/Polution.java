package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop on 15.12.2015.
 */
public class Polution {
    private int type;
    private int year;
    private PolutionLevel level;

    public int getType(){return type;}
    public PolutionLevel getLevel(){return  level;}
    public int getYear(){return year;}

    public void setType(int type) {this.type=type;}
    public void setYear(int year){this.year=year;}
    public void setLevel(PolutionLevel level){this.level=level;}
}
