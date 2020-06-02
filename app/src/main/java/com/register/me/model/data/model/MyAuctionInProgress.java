package com.register.me.model.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyAuctionInProgress {

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

        @SerializedName("biddingProgressDetails")
        @Expose
        private List<BiddingProgressDetail> biddingProgressDetails = null;

        public List<BiddingProgressDetail> getBiddingProgressDetails() {
            return biddingProgressDetails;
        }

        public void setBiddingProgressDetails(List<BiddingProgressDetail> biddingProgressDetails) {
            this.biddingProgressDetails = biddingProgressDetails;
        }

    }

    public class BiddingProgressDetail {

        @SerializedName("submittedBids")
        @Expose
        private Integer submittedBids;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("bidPercentage")
        @Expose
        private String bidPercentage;
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
        @SerializedName("completionDate")
        @Expose
        private String completionDate;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("submittedDate")
        @Expose
        private String submittedDate;
        @SerializedName("biddingStatus")
        @Expose
        private String biddingStatus;
        @SerializedName("remarks")
        @Expose
        private Object remarks;

        public Integer getSubmittedBids() {
            return submittedBids;
        }

        public void setSubmittedBids(Integer submittedBids) {
            this.submittedBids = submittedBids;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getBidPercentage() {
            return bidPercentage;
        }

        public void setBidPercentage(String bidPercentage) {
            this.bidPercentage = bidPercentage;
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

        public String getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(String completionDate) {
            this.completionDate = completionDate;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getSubmittedDate() {
            return submittedDate;
        }

        public void setSubmittedDate(String submittedDate) {
            this.submittedDate = submittedDate;
        }

        public String getBiddingStatus() {
            return biddingStatus;
        }

        public void setBiddingStatus(String biddingStatus) {
            this.biddingStatus = biddingStatus;
        }

        public Object getRemarks() {
            return remarks;
        }

        public void setRemarks(Object remarks) {
            this.remarks = remarks;
        }

    }
}