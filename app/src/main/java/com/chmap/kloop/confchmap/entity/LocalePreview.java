package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop1996 on 05.07.2016.
 */
public class LocalePreview {
    protected int id;
    protected String name;
    protected int countLocalesForChoice;

    public LocalePreview(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getCountLocalesForChoice() {
        return countLocalesForChoice;
    }

    public void setCountLocalesForChoice(int countLocalesForChoice) {
        this.countLocalesForChoice = countLocalesForChoice;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalePreview that = (LocalePreview) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
