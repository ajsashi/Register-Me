package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CertifiedRREList {

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

        @SerializedName("certifiedrredetails")
        @Expose
        private List<Certifiedrredetail> certifiedrredetails = null;

        public List<Certifiedrredetail> getCertifiedrredetails() {
            return certifiedrredetails;
        }

        public void setCertifiedrredetails(List<Certifiedrredetail> certifiedrredetails) {
            this.certifiedrredetails = certifiedrredetails;
        }

    }

    public class Certifiedrredetail {

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
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("certifiedCountry")
        @Expose
        private String certifiedCountry;
        @SerializedName("creationDate")
        @Expose
        private String creationDate;

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

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getCertifiedCountry() {
            return certifiedCountry;
        }

        public void setCertifiedCountry(String certifiedCountry) {
            this.certifiedCountry = certifiedCountry;
        }

        public String getCreationDate() {
            return creationDate;
        }

        public void setCreationDate(String creationDate) {
            this.creationDate = creationDate;
        }

    }
}
