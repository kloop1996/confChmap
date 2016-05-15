package com.chmap.kloop.confchmap.service.impl;

/**
 * Created by kloop1996 on 27.04.2016.
 */
public class MathService {
    public static long getUnsignedInt(int x) {
        return x & 0x00000000ffffffffL;
    }
}
