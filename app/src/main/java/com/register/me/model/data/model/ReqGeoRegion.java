package com.register.me.model.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReqGeoRegion {

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

        @SerializedName("regions")
        @Expose
        private List<Region> regions = null;

        public List<Region> getRegions() {
            return regions;
        }

        public void setRegions(List<Region> regions) {
            this.regions = regions;
        }

    }

    public class Region {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("requestedby")
        @Expose
        private String requestedby;
        @SerializedName("requestedCrre")
        @Expose
        private Boolean requestedCrre;
        @SerializedName("requestedbyCRRE")
        @Expose
        private Integer requestedbyCRRE;
        @SerializedName("requesteddate")
        @Expose
        private String requesteddate;
        @SerializedName("isAccepted")
        @Expose
        private String isAccepted;
        @SerializedName("requestCancelReason")
        @Expose
        private Object requestCancelReason;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getRequestedby() {
            return requestedby;
        }

        public void setRequestedby(String requestedby) {
            this.requestedby = requestedby;
        }

        public Boolean getRequestedCrre() {
            return requestedCrre;
        }

        public void setRequestedCrre(Boolean requestedCrre) {
            this.requestedCrre = requestedCrre;
        }

        public Integer getRequestedbyCRRE() {
            return requestedbyCRRE;
        }

        public void setRequestedbyCRRE(Integer requestedbyCRRE) {
            this.requestedbyCRRE = requestedbyCRRE;
        }

        public String getRequesteddate() {
            return requesteddate;
        }

        public void setRequesteddate(String requesteddate) {
            this.requesteddate = requesteddate;
        }

        public String getIsAccepted() {
            return isAccepted;
        }

        public void setIsAccepted(String isAccepted) {
            this.isAccepted = isAccepted;
        }

        public Object getRequestCancelReason() {
            return requestCancelReason;
        }

        public void setRequestCancelReason(Object requestCancelReason) {
            this.requestCancelReason = requestCancelReason;
        }

    }
}
