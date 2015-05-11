package com.chmap.kloop.confchmap;



import android.graphics.Bitmap;



import android.graphics.BitmapFactory;




public class ProviderMap {
	
	

	
	private Bitmap mBitMap;
	
	public int getColour(InfForCalulationPosition inf,Cordinate Inp)
	{
	
		
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inScaled = false;
		options.inSampleSize = 1;
     	mBitMap = BitmapFactory.decodeResource(FragmentGpsSearch.mGetRes(), inf.idOfResource,options);
		
		int y=(int)((inf.PointerCount.Longitude-Inp.Longitude)/inf.dividerByLong);
		int x=(int)((Inp.Latitude-inf.PointerCount.Latitude)/inf.dividerByLat);
		int colour =mBitMap.getPixel(x, y);
		
		mBitMap.recycle();
		mBitMap=null;
		
		return colour;
	}
	

}
