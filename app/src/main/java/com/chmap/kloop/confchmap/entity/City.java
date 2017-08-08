package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop on 15.12.2015.
 */
public class City {
    private String name;

    private Coordinate coordinate;
    private double distance;
    private int idDistrtict;

    public int getIdOfVillageSoviet() {
        return idOfVillageSoviet;
    }

    public void setIdOfVillageSoviet(int idOfVillageSoviet) {
        this.idOfVillageSoviet = idOfVillageSoviet;
    }

    private int idOfVillageSoviet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;




    public void setIdDistrtict(int idDistrtict) {
        this.idDistrtict = idDistrtict;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public double getDistance() {
        return distance;
    }

    public int getIdDistrtict(){return idDistrtict;}

}
