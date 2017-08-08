package com.chmap.kloop.confchmap.dao.ipml;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;

import com.chmap.kloop.confchmap.ChmapApplication;
import com.chmap.kloop.confchmap.view.activity.MainActivity;
import com.chmap.kloop.confchmap.dao.IMapDao;
import com.chmap.kloop.confchmap.dao.exception.DaoException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kloop on 15.12.2015.
 */
public class FileMapDao implements IMapDao {

    private final static FileMapDao instance = new FileMapDao();

    private FileMapDao() {
    }

    public static FileMapDao getInstance() {
        return instance;
    }

    public Bitmap getBitmapByTag(String tag) throws DaoException {
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            AssetManager assetManager = ChmapApplication.getInstance().getAssets();
            inputStream = assetManager.open(tag);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception ex) {
            throw new DaoException("Error map connection", ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        return bitmap;
    }

    @Override
    public Bitmap getBitmapByTag(String tag, int x, int y) throws DaoException {
        InputStream inputStream = null;
        Bitmap region = null;
        BitmapRegionDecoder decoder = null;
        try {
            AssetManager assetManager = ChmapApplication.getInstance().getAssets();
            inputStream = assetManager.open(tag);
            decoder = BitmapRegionDecoder.newInstance(inputStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inSampleSize = 1;
            region = decoder.decodeRegion(new Rect(x, y, x+2, y+2), options);
        } catch (Exception ex) {
            throw new DaoException("Error map connection", ex);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (decoder != null)
                    decoder.recycle();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        return region;

    }
}
