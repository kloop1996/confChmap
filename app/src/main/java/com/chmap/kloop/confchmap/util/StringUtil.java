package com.chmap.kloop.confchmap.util;

import com.chmap.kloop.confchmap.entity.Polution;

import java.util.ArrayList;

/**
 * Created by kloop1996 on 08.08.2016.
 */
public class StringUtil {
    public static String getRecommedationString(ArrayList<String> src){
        StringBuilder stringBuilder = new StringBuilder();

        for (String string: src){
            stringBuilder.append(string);
            stringBuilder.append("\n");
        }

        return  stringBuilder.toString();

    }
}
