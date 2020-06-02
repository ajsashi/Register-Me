package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientProductList {
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

    @SerializedName("productdetails")
    @Expose
    private List<Productdetail> productdetails = null;

    public List<Productdetail> getProductdetails() {
        return productdetails;
    }

    public void setProductdetails(List<Productdetail> productdetails) {
        this.productdetails = productdetails;
    }

}

    public class Productdetail {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("classification")
        @Expose
        private String classification;

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getClassification() {
            return classification;
        }

        public void setClassification(String classification) {
            this.classification = classification;
        }

    }
}
