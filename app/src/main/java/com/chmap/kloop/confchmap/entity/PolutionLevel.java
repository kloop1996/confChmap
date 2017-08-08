package com.chmap.kloop.confchmap.entity;

import java.io.Serializable;

/**
 * Created by kloop on 15.12.2015.
 */
public class PolutionLevel implements Serializable {
    private double startValue;
    private double endValue;

    public PolutionLevel(double startValue,double endValue){
        this.startValue=startValue;
        this.endValue=endValue;
    }

    public PolutionLevel(){

    }

    public void setStartValue(double startValue){this.startValue=startValue;}
    public void setEndValue(double endValue){this.endValue=endValue;}

    public double getStartValue(){return startValue;}
    public double getEndValue() {return endValue;}
}
