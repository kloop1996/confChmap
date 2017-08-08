package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop1996 on 16.04.2017.
 */

public class LghpCutting {
    private int id;
    private String name;
    private String soil;
    private String localityDescription;
    private String landscapeDescription;
    private String dateLaunch;

    public LghpCutting() {
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

    public String getSoil() {
        return soil;
    }

    public void setSoil(String soil) {
        this.soil = soil;
    }

    public String getLocalityDescription() {
        return localityDescription;
    }

    public void setLocalityDescription(String localityDescription) {
        this.localityDescription = localityDescription;
    }

    public String getLandscapeDescription() {
        return landscapeDescription;
    }

    public void setLandscapeDescription(String landscapeDescription) {
        this.landscapeDescription = landscapeDescription;
    }

    public String getDateLaunch() {
        return dateLaunch;
    }

    public void setDateLaunch(String dateLaunch) {
        this.dateLaunch = dateLaunch;
    }
}
