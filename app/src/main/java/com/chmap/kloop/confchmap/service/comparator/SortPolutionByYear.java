package com.chmap.kloop.confchmap.service.comparator;

import com.chmap.kloop.confchmap.entity.Polution;

import java.util.Comparator;

/**
 * Created by kloop on 22.01.2016.
 */
public class SortPolutionByYear implements Comparator<Polution> {

    @Override
    public int compare(Polution lhs, Polution rhs) {
        if (lhs.getYear()==rhs.getYear()){
            return 0;
        }
        else{
            if (lhs.getYear()<rhs.getYear()){
                return -1;
            }
            else
                return 1;
        }
    }
}