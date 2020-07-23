package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuctionWon {

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

        @SerializedName("auctionswon")
        @Expose
        private List<AuctionsWonData> auctionswon = null;

        public List<AuctionsWonData> getAuctionswon() {
            return auctionswon;
        }

        public void setAuctionswon(List<AuctionsWonData> auctionswon) {
            this.auctionswon = auctionswon;
        }

    }
    public class AuctionsWonData {

        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("client")
        @Expose
        private String client;
        @SerializedName("projectCompletion")
        @Expose
        private String projectCompletion;
        @SerializedName("remaining")
        @Expose
        private String remaining;
        @SerializedName("projectStatus")
        @Expose
        private Object projectStatus;
        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("projectid")
        @Expose
        private Integer projectid;

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public String getProjectCompletion() {
            return projectCompletion;
        }

        public void setProjectCompletion(String projectCompletion) {
            this.projectCompletion = projectCompletion;
        }

        public String getRemaining() {
            return remaining;
        }

        public void setRemaining(String remaining) {
            this.remaining = remaining;
        }

        public Object getProjectStatus() {
            return projectStatus;
        }

        public void setProjectStatus(Object projectStatus) {
            this.projectStatus = projectStatus;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getProjectid() {
            return projectid;
        }

        public void setProjectid(Integer projectid) {
            this.projectid = projectid;
        }

    }
}





