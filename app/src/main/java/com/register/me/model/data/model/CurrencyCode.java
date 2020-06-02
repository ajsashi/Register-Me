package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrencyCode {

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

        @SerializedName("currencydetails")
        @Expose
        private List<Currencydetail> currencydetails = null;

        public List<Currencydetail> getCurrencydetails() {
            return currencydetails;
        }

        public void setCurrencydetails(List<Currencydetail> currencydetails) {
            this.currencydetails = currencydetails;
        }

    }
    public static class Currencydetail {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("currencycode")
        @Expose
        private String currencycode;
        @SerializedName("currencysymbol")
        @Expose
        private String currencysymbol;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCurrencycode() {
            return currencycode;
        }

        public void setCurrencycode(String currencycode) {
            this.currencycode = currencycode;
        }

        public String getCurrencysymbol() {
            return currencysymbol;
        }

        public void setCurrencysymbol(String currencysymbol) {
            this.currencysymbol = currencysymbol;
        }

    }


}