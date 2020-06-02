package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jennifer - AIT on 10-03-2020PM 12:24.
 */
public class CrreList {

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

        @SerializedName("expertlist")
        @Expose
        private List<Expertlist> expertlist = null;

        public List<Expertlist> getExpertlist() {
            return expertlist;
        }

        public void setExpertlist(List<Expertlist> expertlist) {
            this.expertlist = expertlist;
        }

    }

    public class Expertlist {

        @SerializedName("expertid")
        @Expose
        private Integer expertid;
        @SerializedName("expertName")
        @Expose
        private String expertName;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("completiondate")
        @Expose
        private String completiondate;

        public Integer getExpertid() {
            return expertid;
        }

        public void setExpertid(Integer expertid) {
            this.expertid = expertid;
        }

        public String getExpertName() {
            return expertName;
        }

        public void setExpertName(String expertName) {
            this.expertName = expertName;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getCompletiondate() {
            return completiondate;
        }

        public void setCompletiondate(String completiondate) {
            this.completiondate = completiondate;
        }

    }
}

