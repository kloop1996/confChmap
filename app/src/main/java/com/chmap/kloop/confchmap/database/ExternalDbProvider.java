package com.chmap.kloop.confchmap.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chmap.kloop.confchmap.MainActivity;

import java.util.ArrayList;

/**
 * Created by android on 30.03.2015.
 */
public class ExternalDbProvider {
    private SQLiteDatabase database;
    private ExternalDbOpenHelper dbOpenHelper;

    public ExternalDbProvider(Context context, String databaseName){
        dbOpenHelper = new ExternalDbOpenHelper(context,databaseName);
        database = dbOpenHelper.openDataBase();
    }

    protected ArrayList<String> getResultByQuery(String table,String[] columns,String selection,String[] selectionArgs,String groupBy,String having,String orderBy){
        ArrayList<String> result=new ArrayList<String>();

        Cursor CountryCursor = database.query(table,columns,selection,selectionArgs,groupBy,having,orderBy);
        CountryCursor.moveToFirst();

        if (!CountryCursor.isAfterLast()) {
            do {
                String name = CountryCursor.getString(1);
                result.add(name);
            } while (CountryCursor.moveToNext());
        }
        CountryCursor.close();

        return result;
    }

    protected  int getIdByName(String table,String value){
        int res=0;

        return  res;
    }
}
