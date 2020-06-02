package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyActiveAuction {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    public MyActiveAuction ApiList;

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

    public MyActiveAuction getApiList() {
        return ApiList;
    }

    public void setApiList(MyActiveAuction apiList) {
        ApiList = apiList;
    }

    public class Data {

        @SerializedName("activeBiddingdetails")
        @Expose
        private List<ActiveBiddingdetail> activeBiddingdetails = null;

        public List<ActiveBiddingdetail> getActiveBiddingdetails() {
            return activeBiddingdetails;
        }

        public void setActiveBiddingdetails(List<ActiveBiddingdetail> activeBiddingdetails) {
            this.activeBiddingdetails = activeBiddingdetails;
        }

    }

    public class ActiveBiddingdetail {

        @SerializedName("geoLocationId")
        @Expose
        private Integer geoLocationId;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("projectDeadline")
        @Expose
        private String projectDeadline;
        @SerializedName("geolocationActive")
        @Expose
        private Integer geolocationActive;
        @SerializedName("bidStartDate")
        @Expose
        private String bidStartDate;
        @SerializedName("bidEndDate")
        @Expose
        private String bidEndDate;
        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("productActive")
        @Expose
        private Boolean productActive;
        @SerializedName("projectId")
        @Expose
        private Integer projectId;
        @SerializedName("bidDetails")
        @Expose
        private Boolean bidDetails;

        public Integer getGeoLocationId() {
            return geoLocationId;
        }

        public void setGeoLocationId(Integer geoLocationId) {
            this.geoLocationId = geoLocationId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getProjectDeadline() {
            return projectDeadline;
        }

        public void setProjectDeadline(String projectDeadline) {
            this.projectDeadline = projectDeadline;
        }

        public Integer getGeolocationActive() {
            return geolocationActive;
        }

        public void setGeolocationActive(Integer geolocationActive) {
            this.geolocationActive = geolocationActive;
        }

        public String getBidStartDate() {
            return bidStartDate;
        }

        public void setBidStartDate(String bidStartDate) {
            this.bidStartDate = bidStartDate;
        }

        public String getBidEndDate() {
            return bidEndDate;
        }

        public void setBidEndDate(String bidEndDate) {
            this.bidEndDate = bidEndDate;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Boolean getProductActive() {
            return productActive;
        }

        public void setProductActive(Boolean productActive) {
            this.productActive = productActive;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public Boolean getBidDetails() {
            return bidDetails;
        }

        public void setBidDetails(Boolean bidDetails) {
            this.bidDetails = bidDetails;
        }

    }
}
