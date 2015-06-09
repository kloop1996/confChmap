package com.chmap.kloop.confchmap;

import java.util.Vector;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.chmap.kloop.confchmap.database.ExternalDbOpenHelper;


public class InfoAboutMaps {
	private Vector <PointersOfMap> mInfPointersAboutMaps;
	private Vector <PointersOfMap> mSelectedPointersAboutMaps;
	
	public Vector <InfAboutPixel> mAllColoursOfCurrentPosition;
	public  Cordinate Inps;
	private String mNameOfSelectLocale; 
	private ExternalDbOpenHelper dbOpenHelper;
	private SQLiteDatabase dbOfMaps;
	private int mSelectIdOfLocale;
	private int mYearOfSelectLocale;
	private String DB_NAME="infmap.sqlite3";
	
	
	public InfoAboutMaps()
	{
		mInfPointersAboutMaps=new Vector<PointersOfMap>();
		mSelectedPointersAboutMaps=new Vector<PointersOfMap>();
		mSelectIdOfLocale=-1;
		mYearOfSelectLocale=-1;
		mAllColoursOfCurrentPosition=new Vector <InfAboutPixel>();
		mNameOfSelectLocale="Гомельская";
	}

	public InfoAboutMaps(double lat,double lon)
	{
		this();
	}


	
	public void InitPosition(Context context,double y,double x)
	{
		
		dbOpenHelper = new ExternalDbOpenHelper(context,DB_NAME);
		dbOfMaps = dbOpenHelper.openDataBase();
		
	    Inps=new Cordinate();
	    Inps.Latitude=x;
	    Inps.Longitude=y;
	    
	    
		//��������� �������
		
		Cursor InpCursor = dbOfMaps.query("locales", new String[] {
		"_id", "LongOfA","LatOfA","LongfOfD","LatOfD" }, null, null, null, null, null);
		
		PointersOfMap Buf;
		
		InpCursor.moveToFirst();
		
		if (!InpCursor.isAfterLast()) {
			do {
				Buf=new PointersOfMap();
				Buf.IdOfImgs=(InpCursor.getInt(0));
				
				Buf.A.Latitude=InpCursor.getDouble(2);
				Buf.A.Longitude=InpCursor.getDouble(1);
				
				Buf.D.Latitude=InpCursor.getDouble(4);
				Buf.D.Longitude=InpCursor.getDouble(3);
				
				mInfPointersAboutMaps.add(Buf);
				
				
			} while (InpCursor.moveToNext());
		}
		InpCursor.close();
		
		int size =mInfPointersAboutMaps.size();
		
		mSelectedPointersAboutMaps.clear();
		for (int i=0;i<size;i++)
		{
			//���� �������� � ������������� �������, �� ��������� � ���������
			
			if ((x>=mInfPointersAboutMaps.get(i).A.Latitude&&x<=mInfPointersAboutMaps.get(i).D.Latitude)&&(y>=mInfPointersAboutMaps.get(i).D.Longitude&&y<=mInfPointersAboutMaps.get(i).A.Longitude))
			{
				mSelectedPointersAboutMaps.add(mInfPointersAboutMaps.get(i));
				
			}
		}
		
		InfForCalulationPosition infCalc=new InfForCalulationPosition();
		size =mSelectedPointersAboutMaps.size();
		
		int CurrentColour=0;
		int i;
		for (i=0;i<size;i++)
		{
			InpCursor=dbOfMaps.query("maps", new String[] {
					"_id", "dividerByLong","dividerByLat","year"}, "idOfLocale = "+mSelectedPointersAboutMaps.get(i).IdOfImgs, null, null, null, "_id");
			InpCursor.moveToFirst();
			
			infCalc.idOfResource=InpCursor.getInt(0);
			infCalc.dividerByLat=InpCursor.getDouble(2);
			infCalc.dividerByLong=InpCursor.getDouble(1);
			
			infCalc.PointerCount=mSelectedPointersAboutMaps.get(i).A;

			mYearOfSelectLocale=InpCursor.getInt(3);
			
			
			InpCursor.close();
			
			
			ProviderMap Temp= new ProviderMap();
			 
			 CurrentColour=Temp.getColour(infCalc,Inps);
			 
			 if (CurrentColour!=0xffffffff)
			 {
				 i++;
				 break;
			 }
		}
		
	i--;
		
	if (CurrentColour==0xffffffff)	
	{
		mNameOfSelectLocale="";
		
		
	}
	else
	{
		mSelectIdOfLocale=mSelectedPointersAboutMaps.get(i).IdOfImgs;
		InpCursor=dbOfMaps.query("locales", new String[] {
				"_id", "name"}, "_id = "+mSelectIdOfLocale, null, null, null, null);
		
		InpCursor.moveToFirst();
		mNameOfSelectLocale=InpCursor.getString(1);
		
		mAllColoursOfCurrentPosition.clear();
		InpCursor.close();
		
		InpCursor=dbOfMaps.query("maps", new String[] {
				"_id", "year", "dividerByLong","dividerByLat"}, "idOfLocale = "+mSelectIdOfLocale, null, null, null, "_id");
		int Colour=1;
		InpCursor.moveToFirst();
		if (!InpCursor.isAfterLast()) {
			do {
				
				InfAboutPixel Bufs =new InfAboutPixel();
				Bufs.year=InpCursor.getInt(1);
				
				infCalc.dividerByLong=InpCursor.getDouble(2);
				infCalc.dividerByLat=InpCursor.getDouble(3);
				infCalc.idOfResource=InpCursor.getInt(0);
				
				infCalc.PointerCount=mSelectedPointersAboutMaps.get(i).A;
				
				if (Bufs.year!=mYearOfSelectLocale)
				{
				ProviderMap Temp= new ProviderMap();
				  Colour=Temp.getColour(infCalc,Inps);
				  Bufs.colour=Colour;
				}
				else
				{
					Bufs.colour=CurrentColour;
				}
				
				mAllColoursOfCurrentPosition.add(Bufs);
				
			
			} while (InpCursor.moveToNext());
		}
		InpCursor.close();
		
		
		
		
	}
		
	}

	public String getSelectLocaleName()
	{
		return mNameOfSelectLocale;
	}
	public int getSelectNubmerLocaleName()
	{
		return 3;
	}
}