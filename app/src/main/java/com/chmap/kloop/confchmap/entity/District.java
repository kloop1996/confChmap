package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop1996 on 05.07.2016.
 */
public class District {
    private int id;
    private String name;
    private int idOfLocale;

    public District(int id, String name, int idOfLocale) {
        this.id = id;
        this.name = name;
        this.idOfLocale = idOfLocale;
    }

    public District() {

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

    public int getIdOfLocale() {
        return idOfLocale;
    }

    public void setIdOfLocale(int idOfLocale) {
        this.idOfLocale = idOfLocale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        District district = (District) o;

        if (id != district.id) return false;
        if (idOfLocale != district.idOfLocale) return false;
        return name != null ? name.equals(district.name) : district.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + idOfLocale;
        return result;
    }
}
