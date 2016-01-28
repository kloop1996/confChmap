package com.chmap.kloop.confchmap.dao;

import android.graphics.Bitmap;

import com.chmap.kloop.confchmap.dao.exception.DaoException;

/**
 * Created by kloop on 14.12.2015.
 */
public interface IMapDao {
    Bitmap getBitmapByTag(String tag) throws DaoException;
    Bitmap getBitmapByTag(String tag, int x, int y) throws DaoException;
}
