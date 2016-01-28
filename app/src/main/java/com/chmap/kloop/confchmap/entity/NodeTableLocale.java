package com.chmap.kloop.confchmap.entity;

import com.chmap.kloop.confchmap.entity.Coordinate;

/**
 * Created by kloop on 15.12.2015.
 */
public class NodeTableLocale {
    private int id;
    private Coordinate coordinateA;
    private Coordinate coordinateD;

    public int getId(){return id;}
    public Coordinate getCoordinateA(){return coordinateA;}
    public Coordinate getCoordinateD(){return coordinateD;}

    public void setId(int id){this.id=id;}
    public void setCoordinateA(Coordinate coordinateA){this.coordinateA=coordinateA;}
    public void setCoordinateD(Coordinate coordinateD){this.coordinateD=coordinateD;}

}
