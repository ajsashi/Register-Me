package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeSchedule {

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

        @SerializedName("time")
        @Expose
        private List<Time> time = null;
        @SerializedName("schedules")
        @Expose
        private List<Schedule> schedules = null;

        public List<Time> getTime() {
            return time;
        }

        public void setTime(List<Time> time) {
            this.time = time;
        }

        public List<Schedule> getSchedules() {
            return schedules;
        }

        public void setSchedules(List<Schedule> schedules) {
            this.schedules = schedules;
        }

    }

    public class Time {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("availableid")
        @Expose
        private Integer availableid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("booked")
        @Expose
        private Boolean booked;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public Integer getAvailableid() {
            return availableid;
        }

        public void setAvailableid(Integer availableid) {
            this.availableid = availableid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getBooked() {
            return booked;
        }

        public void setBooked(Boolean booked) {
            this.booked = booked;
        }

    }

    public class Schedule {

        @SerializedName("timeid")
        @Expose
        private Integer timeid;
        @SerializedName("crre")
        @Expose
        private String crre;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("status")
        @Expose
        private String status;

        public Integer getTimeid() {
            return timeid;
        }

        public void setTimeid(Integer timeid) {
            this.timeid = timeid;
        }

        public String getCrre() {
            return crre;
        }

        public void setCrre(String crre) {
            this.crre = crre;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}
