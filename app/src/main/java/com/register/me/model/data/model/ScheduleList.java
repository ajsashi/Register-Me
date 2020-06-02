package com.register.me.model.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScheduleList {

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


    public class Schedule {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("rre")
        @Expose
        private String rre;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("availableDate")
        @Expose
        private String availableDate;
        @SerializedName("availableStartTime")
        @Expose
        private String availableStartTime;
        @SerializedName("availableEndTime")
        @Expose
        private String availableEndTime;
        @SerializedName("availableDuration")
        @Expose
        private String availableDuration;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRre() {
            return rre;
        }

        public void setRre(String rre) {
            this.rre = rre;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getAvailableDate() {
            return availableDate;
        }

        public void setAvailableDate(String availableDate) {
            this.availableDate = availableDate;
        }

        public String getAvailableStartTime() {
            return availableStartTime;
        }

        public void setAvailableStartTime(String availableStartTime) {
            this.availableStartTime = availableStartTime;
        }

        public String getAvailableEndTime() {
            return availableEndTime;
        }

        public void setAvailableEndTime(String availableEndTime) {
            this.availableEndTime = availableEndTime;
        }

        public String getAvailableDuration() {
            return availableDuration;
        }

        public void setAvailableDuration(String availableDuration) {
            this.availableDuration = availableDuration;
        }

    }

    public class Data {

        @SerializedName("schedules")
        @Expose
        private List<Schedule> schedules = null;

        public List<Schedule> getSchedules() {
            return schedules;
        }

        public void setSchedules(List<Schedule> schedules) {
            this.schedules = schedules;
        }

    }

}
