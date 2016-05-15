package com.chmap.kloop.confchmap.service.impl;

/**
 * Created by kloop1996 on 24.04.2016.
 */
public final class PolutionType {
    public final static int CS_TYPE = 1;
    public final static int SR_TYPE = 2;
    public final static int PU_238 = 3;
    public final static int PU_241 = 4;
    public final static int AM_241 = 5;

    public static String getTypeNamePolution(int id){
        switch (id){
            case 1:
                return "CS-137";
            case 2:
                return "SR-90";
            case 3:
                return "PU-238-240";
            case 4:
                return "PU-241";
            case 5:
                return "AM_241";

        }
        return  null;
    }
}
