package com.chmap.kloop.confchmap.service.comparator;

import com.chmap.kloop.confchmap.entity.City;

import java.util.Comparator;

/**
 * Created by kloop on 15.12.2015.
 */
public class SortCityByDistance implements Comparator<City>{
    @Override
    public int compare(City lhs, City rhs) {
        if (lhs.getDistance()==rhs.getDistance())
            return 0;
        else
            if (lhs.getDistance()<rhs.getDistance())
                return -1;
            else
                return 1;
    }
}
