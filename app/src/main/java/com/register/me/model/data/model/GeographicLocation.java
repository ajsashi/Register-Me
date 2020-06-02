package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeographicLocation {
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public class Data {

        @SerializedName("locations")
        @Expose
        private List<Location> locations = null;

        public List<Location> getLocations() {
            return locations;
        }

        public void setLocations(List<Location> locations) {
            this.locations = locations;
        }

    }

    public class Location {

        @SerializedName("geographiclocation")
        @Expose
        private String geographiclocation;
        @SerializedName("creationdate")
        @Expose
        private String creationdate;
        @SerializedName("status")
        @Expose
        private String status;

        public String getGeographiclocation() {
            return geographiclocation;
        }

        public void setGeographiclocation(String geographiclocation) {
            this.geographiclocation = geographiclocation;
        }

        public String getCreationdate() {
            return creationdate;
        }

        public void setCreationdate(String creationdate) {
            this.creationdate = creationdate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
