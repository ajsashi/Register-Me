package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SuccessCertificate {

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

        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("regions")
        @Expose
        private String regions;
        @SerializedName("certifiedregions")
        @Expose
        private List<Certifiedregion> certifiedregions = null;
        @SerializedName("uncertifiedregions")
        @Expose
        private List<Uncertifiedregion> uncertifiedregions = null;
        @SerializedName("comments")
        @Expose
        private List<Object> comments = null;
        @SerializedName("mysuccessStory")
        @Expose
        private MysuccessStory mysuccessStory;
        @SerializedName("myLibraryfiles")
        @Expose
        private List<Object> myLibraryfiles = null;

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getRegions() {
            return regions;
        }

        public void setRegions(String regions) {
            this.regions = regions;
        }

        public List<Certifiedregion> getCertifiedregions() {
            return certifiedregions;
        }

        public void setCertifiedregions(List<Certifiedregion> certifiedregions) {
            this.certifiedregions = certifiedregions;
        }

        public List<Uncertifiedregion> getUncertifiedregions() {
            return uncertifiedregions;
        }

        public void setUncertifiedregions(List<Uncertifiedregion> uncertifiedregions) {
            this.uncertifiedregions = uncertifiedregions;
        }

        public List<Object> getComments() {
            return comments;
        }

        public void setComments(List<Object> comments) {
            this.comments = comments;
        }

        public MysuccessStory getMysuccessStory() {
            return mysuccessStory;
        }

        public void setMysuccessStory(MysuccessStory mysuccessStory) {
            this.mysuccessStory = mysuccessStory;
        }

        public List<Object> getMyLibraryfiles() {
            return myLibraryfiles;
        }

        public void setMyLibraryfiles(List<Object> myLibraryfiles) {
            this.myLibraryfiles = myLibraryfiles;
        }

    }

    public class MysuccessStory {

        @SerializedName("offered")
        @Expose
        private Integer offered;
        @SerializedName("submittedBid")
        @Expose
        private Integer submittedBid;
        @SerializedName("bidWon")
        @Expose
        private Integer bidWon;
        @SerializedName("currentYear")
        @Expose
        private String currentYear;
        @SerializedName("lifttime")
        @Expose
        private String lifttime;
        @SerializedName("potential")
        @Expose
        private String potential;

        public Integer getOffered() {
            return offered;
        }

        public void setOffered(Integer offered) {
            this.offered = offered;
        }

        public Integer getSubmittedBid() {
            return submittedBid;
        }

        public void setSubmittedBid(Integer submittedBid) {
            this.submittedBid = submittedBid;
        }

        public Integer getBidWon() {
            return bidWon;
        }

        public void setBidWon(Integer bidWon) {
            this.bidWon = bidWon;
        }

        public String getCurrentYear() {
            return currentYear;
        }

        public void setCurrentYear(String currentYear) {
            this.currentYear = currentYear;
        }

        public String getLifttime() {
            return lifttime;
        }

        public void setLifttime(String lifttime) {
            this.lifttime = lifttime;
        }

        public String getPotential() {
            return potential;
        }

        public void setPotential(String potential) {
            this.potential = potential;
        }

    }
    public static class Uncertifiedregion {

        @SerializedName("locationid")
        @Expose
        private Integer locationid;
        @SerializedName("uncertifiedregion")
        @Expose
        private String uncertifiedregion;

        public Integer getLocationid() {
            return locationid;
        }

        public void setLocationid(Integer locationid) {
            this.locationid = locationid;
        }

        public String getUncertifiedregion() {
            return uncertifiedregion;
        }

        public void setUncertifiedregion(String uncertifiedregion) {
            this.uncertifiedregion = uncertifiedregion;
        }

    }

    public class Certifiedregion {

        @SerializedName("locationid")
        @Expose
        private Integer locationid;
        @SerializedName("certifiedregion")
        @Expose
        private String certifiedregion;

        public Integer getLocationid() {
            return locationid;
        }

        public void setLocationid(Integer locationid) {
            this.locationid = locationid;
        }

        public String getCertifiedregion() {
            return certifiedregion;
        }

        public void setCertifiedregion(String certifiedregion) {
            this.certifiedregion = certifiedregion;
        }

    }
}