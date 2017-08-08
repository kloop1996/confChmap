package com.chmap.kloop.confchmap.entity;

import java.io.Serializable;

/**
 * Created by kloop1996 on 15.04.2017.
 */

public class Lghp implements Serializable {
    private int id;
    private String name;
    private Coordinate coordinate;
    private int idOfDistrict;

    public Lghp(int id, String name, Coordinate coordinate, int idOfDistrict) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.idOfDistrict = idOfDistrict;
    }

    public Lghp() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int getIdOfDistrict() {
        return idOfDistrict;
    }

    public void setIdOfDistrict(int idOfDistrict) {
        this.idOfDistrict = idOfDistrict;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lghp lghp = (Lghp) o;

        if (id != lghp.id) return false;
        if (idOfDistrict != lghp.idOfDistrict) return false;
        if (name != null ? !name.equals(lghp.name) : lghp.name != null) return false;
        return coordinate != null ? coordinate.equals(lghp.coordinate) : lghp.coordinate == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (coordinate != null ? coordinate.hashCode() : 0);
        result = 31 * result + idOfDistrict;
        return result;
    }
}
