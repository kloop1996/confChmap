package com.chmap.kloop.confchmap.database;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by android on 30.03.2015.
 */
public class АdministrativeDbProvider extends ExternalDbProvider {

    private static final String DB_NAME = "chmapdb.sqlite3";

    public static final String TABLE_OF_COUNTRIES = "countries";
    public static final String TABLE_OF_LOCALES = "locales";
    public static final String TABLE_OF_REGIONS = "regions";
    public static final String TABLE_OF_CITIES="cities";

    public static final String BASE_ID = "_id";
    public static final String BASE_NAME = "name";

    private static final int ID_OF_COUNTRY=1;

    public АdministrativeDbProvider(Context context) {
        super(context, DB_NAME);
    }

    public ArrayList<String> getLocales(long idOfCountry){
        ArrayList<String> result;
        String selection ="idOfCounrty = "+(idOfCountry);

        result=getResultByQuery(TABLE_OF_LOCALES, new String[] {
                BASE_ID, BASE_NAME }, selection, null, null, null, BASE_NAME);

        return result;
    }

    public ArrayList<String> getRegions(long idOfLocale){
        ArrayList<String> result;
        String selection ="ifOfRegion = "+idOfLocale;

        result=getResultByQuery(TABLE_OF_CITIES, new String[] {BASE_ID,
                BASE_NAME,}, selection, null, null, null, null);

        return result;
    }

    public ArrayList <String> getCities(long idOfCity){
        ArrayList<String> result;
        String selection ="idOfLocale = "+(idOfCity+1);

        result=getResultByQuery(TABLE_OF_REGIONS, new String[] {
                BASE_ID, BASE_NAME }, selection, null, null, null, BASE_NAME);

        return result;
    }

    public int getIdByName(String table,String value){
        int res=0;
        ArrayList<String> result;
        String selection ="name = "+"'"+value+"'";

        result=getResultByQuery(table, new String[] {
                 BASE_NAME,BASE_ID }, selection, null, null, null, BASE_NAME);

        res=Integer.parseInt(result.get(0));
        return res;
    }
}
