package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CertificateStatus {
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

        @SerializedName("certificateprocess")
        @Expose
        private List<Certificateprocess> certificateprocess = null;

        public List<Certificateprocess> getCertificateprocess() {
            return certificateprocess;
        }

        public void setCertificateprocess(List<Certificateprocess> certificateprocess) {
            this.certificateprocess = certificateprocess;
        }

    }

    public class Certificateprocess {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("applicationdate")
        @Expose
        private String applicationdate;
        @SerializedName("applicationstatus")
        @Expose
        private String applicationstatus;
        @SerializedName("interviewdate")
        @Expose
        private String interviewdate;
        @SerializedName("interviewstatus")
        @Expose
        private String interviewstatus;
        @SerializedName("apppolicydate")
        @Expose
        private String apppolicydate;
        @SerializedName("trainingstatus")
        @Expose
        private String trainingstatus;
        @SerializedName("certificatedate")
        @Expose
        private String certificatedate;
        @SerializedName("certificatestatus")
        @Expose
        private String certificatestatus;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getApplicationdate() {
            return applicationdate;
        }

        public void setApplicationdate(String applicationdate) {
            this.applicationdate = applicationdate;
        }

        public String getApplicationstatus() {
            return applicationstatus;
        }

        public void setApplicationstatus(String applicationstatus) {
            this.applicationstatus = applicationstatus;
        }

        public String getInterviewdate() {
            return interviewdate;
        }

        public void setInterviewdate(String interviewdate) {
            this.interviewdate = interviewdate;
        }

        public String getInterviewstatus() {
            return interviewstatus;
        }

        public void setInterviewstatus(String interviewstatus) {
            this.interviewstatus = interviewstatus;
        }

        public String getApppolicydate() {
            return apppolicydate;
        }

        public void setApppolicydate(String apppolicydate) {
            this.apppolicydate = apppolicydate;
        }

        public String getTrainingstatus() {
            return trainingstatus;
        }

        public void setTrainingstatus(String trainingstatus) {
            this.trainingstatus = trainingstatus;
        }

        public String getCertificatedate() {
            return certificatedate;
        }

        public void setCertificatedate(String certificatedate) {
            this.certificatedate = certificatedate;
        }

        public String getCertificatestatus() {
            return certificatestatus;
        }

        public void setCertificatestatus(String certificatestatus) {
            this.certificatestatus = certificatestatus;
        }

    }
}
