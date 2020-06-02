package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jennifer - AIT on 03-03-2020AM 10:54.
 */
public class ActiveAuction {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("errors")
    @Expose
    private List<Object> errors = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }



    public class Data {

        @SerializedName("auctionsprogress")
        @Expose
        private List<Auctionsprogress> auctionsprogress = null;

        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("bidsreadytoevaluate")
        @Expose
        private List<Bidsreadytoevaluate_> bidsreadytoevaluate = null;

        public List<Auctionsprogress> getAuctionsprogress() {
            return auctionsprogress;
        }

        public void setAuctionsprogress(List<Auctionsprogress> auctionsprogress) {
            this.auctionsprogress = auctionsprogress;
        }

        public List<Bidsreadytoevaluate_> getBidsreadytoevaluate() {
            return bidsreadytoevaluate;
        }

        public void setBidsreadytoevaluate(List<Bidsreadytoevaluate_> bidsreadytoevaluate) {
            this.bidsreadytoevaluate = bidsreadytoevaluate;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public class Auctionsprogress {

        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("timediff")
        @Expose
        private String timediff;
        @SerializedName("noofPotentialExperts")
        @Expose
        private Integer noofPotentialExperts;
        @SerializedName("noofexperts")
        @Expose
        private Integer noofexperts;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getTimediff() {
            return timediff;
        }

        public void setTimediff(String timediff) {
            this.timediff = timediff;
        }

        public Integer getNoofPotentialExperts() {
            return noofPotentialExperts;
        }

        public void setNoofPotentialExperts(Integer noofPotentialExperts) {
            this.noofPotentialExperts = noofPotentialExperts;
        }

        public Integer getNoofexperts() {
            return noofexperts;
        }

        public void setNoofexperts(Integer noofexperts) {
            this.noofexperts = noofexperts;
        }

    }

    public class Bidsreadytoevaluate_ {

        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("noofPotentialExperts")
        @Expose
        private Integer noofPotentialExperts;
        @SerializedName("noofexperts")
        @Expose
        private Integer noofexperts;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("isWaitingAssigned")
        @Expose
        private Boolean isWaitingAssigned;
        @SerializedName("bidCompleted")
        @Expose
        private Boolean bidCompleted;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public Integer getNoofPotentialExperts() {
            return noofPotentialExperts;
        }

        public void setNoofPotentialExperts(Integer noofPotentialExperts) {
            this.noofPotentialExperts = noofPotentialExperts;
        }

        public Integer getNoofexperts() {
            return noofexperts;
        }

        public void setNoofexperts(Integer noofexperts) {
            this.noofexperts = noofexperts;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Boolean getIsWaitingAssigned() {
            return isWaitingAssigned;
        }

        public void setIsWaitingAssigned(Boolean isWaitingAssigned) {
            this.isWaitingAssigned = isWaitingAssigned;
        }

        public Boolean getBidCompleted() {
            return bidCompleted;
        }

        public void setBidCompleted(Boolean bidCompleted) {
            this.bidCompleted = bidCompleted;
        }

    }
}
