package com.register.me.model.data.model;

/**
 * Created by Jennifer - AIT on 28-02-2020AM 10:56.
 */
public class Location {
    int id;
    String region;

    public Location(int id, String region) {
        this.id = id;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
