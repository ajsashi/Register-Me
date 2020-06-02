package com.register.me.model.data.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Client {

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

        @SerializedName("clientdetail")
        @Expose
        private List<Clientdetail> clientdetail = null;

        public List<Clientdetail> getClientdetail() {
            return clientdetail;
        }

        public void setClientdetail(List<Clientdetail> clientdetail) {
            this.clientdetail = clientdetail;
        }

    }
    public class Clientdetail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("telephone")
        @Expose
        private String telephone;
        @SerializedName("cellPhone")
        @Expose
        private String cellPhone;
        @SerializedName("createdDate1")
        @Expose
        private String createdDate1;
        @SerializedName("active")
        @Expose
        private String active;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
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

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public String getCreatedDate1() {
            return createdDate1;
        }

        public void setCreatedDate1(String createdDate1) {
            this.createdDate1 = createdDate1;
        }

        public String getActive() {
            return active;
        }

        public void setActive(String active) {
            this.active = active;
        }

    }
}
