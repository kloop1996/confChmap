package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop on 15.12.2015.
 */
public class PolutionLevel {
    private double startValue;
    private double endValue;

    public PolutionLevel(double startValue,double endValue){
        this.startValue=startValue;
        this.endValue=endValue;
    }

    public PolutionLevel(){
        this.startValue=startValue;
        this.endValue=endValue;
    }

    public void setStartValue(double startValue){this.startValue=startValue;}
    public void setEndValue(double endValue){this.endValue=endValue;}

    public double getStartValue(){return startValue;}
    public double getEndValue() {return endValue;}
}
