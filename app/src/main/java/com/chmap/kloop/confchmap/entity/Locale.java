package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop1996 on 05.07.2016.
 */
public class Locale {
    private int id;
    private String name;
    private int idOfCountry;

    public Locale(int id, String name, int idOfCountry) {
        this.id = id;
        this.name = name;
        this.idOfCountry = idOfCountry;
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

    public int getIdOfCountry() {
        return idOfCountry;
    }

    public void setIdOfCountry(int idOfCountry) {
        this.idOfCountry = idOfCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Locale locale = (Locale) o;

        if (id != locale.id) return false;
        if (idOfCountry != locale.idOfCountry) return false;
        return name != null ? name.equals(locale.name) : locale.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + idOfCountry;
        return result;
    }
}
