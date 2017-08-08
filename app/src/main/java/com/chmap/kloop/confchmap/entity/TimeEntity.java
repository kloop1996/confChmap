package com.chmap.kloop.confchmap.entity;

/**
 * Created by kloop1996 on 14.06.2016.
 */
public class TimeEntity {
    private int hour;
    private int minute;

    public TimeEntity(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    public TimeEntity(){;}

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getAbsoluteValue(){return hour*60+minute;}
}
