package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RRE {

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

        @SerializedName("rredetails")
        @Expose
        private List<RREdetail> rredetails = null;

        public List<RREdetail> getRredetails() {
            return rredetails;
        }

        public void setRredetails(List<RREdetail> rredetails) {
            this.rredetails = rredetails;
        }
    }

    public class RREdetail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("registrationExpert")
        @Expose
        private String registrationExpert;
        @SerializedName("fullName")
        @Expose
        private String fullName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("completeApplication")
        @Expose
        private String completeApplication;
        @SerializedName("interview")
        @Expose
        private String interview;
        @SerializedName("appTraining")
        @Expose
        private String appTraining;
        @SerializedName("certificateIssue")
        @Expose
        private String certificateIssue;
        @SerializedName("unread")
        @Expose
        private Integer unread;
        @SerializedName("applicationid")
        @Expose
        private String applicationid;
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getRegistrationExpert() {
            return registrationExpert;
        }

        public void setRegistrationExpert(String registrationExpert) {
            this.registrationExpert = registrationExpert;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCompleteApplication() {
            return completeApplication;
        }

        public void setCompleteApplication(String completeApplication) {
            this.completeApplication = completeApplication;
        }

        public String getInterview() {
            return interview;
        }

        public void setInterview(String interview) {
            this.interview = interview;
        }

        public String getAppTraining() {
            return appTraining;
        }

        public void setAppTraining(String appTraining) {
            this.appTraining = appTraining;
        }

        public String getCertificateIssue() {
            return certificateIssue;
        }

        public void setCertificateIssue(String certificateIssue) {
            this.certificateIssue = certificateIssue;
        }

        public Integer getUnread() {
            return unread;
        }

        public void setUnread(Integer unread) {
            this.unread = unread;
        }

        public String getApplicationid() {
            return applicationid;
        }

        public void setApplicationid(String applicationid) {
            this.applicationid = applicationid;
        }
    }
}
