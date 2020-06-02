package com.register.me.model.data.model;

public class TimeSlotKV {
    int index;
    String fromTime;

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    String toTime;

    public TimeSlotKV(int index, String fromTime, String toTime) {
        this.index = index;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }
}
