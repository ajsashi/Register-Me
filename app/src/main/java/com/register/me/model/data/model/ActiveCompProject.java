package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jennifer - AIT on 04-03-2020PM 05:12.
 */
public class ActiveCompProject {
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


    public class ActiveProjectDetail {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("projectId")
        @Expose
        private Integer projectId;
        @SerializedName("projectid")
        @Expose
        private Integer projectid;
        @SerializedName("productname")
        @Expose
        private String productname;
        @SerializedName("productnumber")
        @Expose
        private String productnumber;
        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("crreId")
        @Expose
        private Integer crreId;
        @SerializedName("client")
        @Expose
        private String client;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("projectCompletion")
        @Expose
        private String projectCompletion;
        @SerializedName("remaining")
        @Expose
        private String remaining;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("paid")
        @Expose
        private String paid;
        @SerializedName("balance")
        @Expose
        private String balance;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("registrationexpert")
        @Expose
        private String registrationexpert;
        @SerializedName("bidamount")
        @Expose
        private String bidamount;
        @SerializedName("paidamount")
        @Expose
        private String paidamount;
        @SerializedName("balanceamount")
        @Expose
        private String balanceamount;
        @SerializedName("nextdueamount")
        @Expose
        private String nextdueamount;
        @SerializedName("completiondate")
        @Expose
        private String completiondate;
        @SerializedName("projectstatus")
        @Expose
        private String projectstatus;

        public Integer getProductid() {
            return productid;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getProductnumber() {
            return productnumber;
        }

        public void setProductnumber(String productnumber) {
            this.productnumber = productnumber;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getRegistrationexpert() {
            return registrationexpert;
        }

        public void setRegistrationexpert(String registrationexpert) {
            this.registrationexpert = registrationexpert;
        }

        public String getBidamount() {
            return bidamount;
        }

        public void setBidamount(String bidamount) {
            this.bidamount = bidamount;
        }

        public String getPaidamount() {
            return paidamount;
        }

        public void setPaidamount(String paidamount) {
            this.paidamount = paidamount;
        }

        public String getBalanceamount() {
            return balanceamount;
        }

        public void setBalanceamount(String balanceamount) {
            this.balanceamount = balanceamount;
        }

        public String getNextdueamount() {
            return nextdueamount;
        }

        public void setNextdueamount(String nextdueamount) {
            this.nextdueamount = nextdueamount;
        }

        public String getCompletiondate() {
            return completiondate;
        }

        public void setCompletiondate(String completiondate) {
            this.completiondate = completiondate;
        }

        public String getProjectstatus() {
            return projectstatus;
        }

        public void setProjectstatus(String projectstatus) {
            this.projectstatus = projectstatus;
        }

        public Integer getCrreId() {
            return crreId;
        }

        public void setCrreId(Integer crreId) {
            this.crreId = crreId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getProjectCompletion() {
            return projectCompletion;
        }

        public void setProjectCompletion(String projectCompletion) {
            this.projectCompletion = projectCompletion;
        }

        public String getRemaining() {
            return remaining;
        }

        public void setRemaining(String remaining) {
            this.remaining = remaining;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getProjectid() {
            return projectid;
        }

        public void setProjectid(Integer projectid) {
            this.projectid = projectid;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }
    }


    public class Data {

        @SerializedName("activeProjectDetails")
        @Expose
        private List<ActiveProjectDetail> activeProjectDetails = null;
        @SerializedName("completedProjectDetails")
        @Expose
        private List<CompletedProjectDetail> completedProjectDetails = null;

        public List<ActiveProjectDetail> getActiveProjectDetails() {
            return activeProjectDetails;
        }

        public void setActiveProjectDetails(List<ActiveProjectDetail> activeProjectDetails) {
            this.activeProjectDetails = activeProjectDetails;
        }

        public List<CompletedProjectDetail> getCompletedProjectDetails() {
            return completedProjectDetails;
        }

        public void setCompletedProjectDetails(List<CompletedProjectDetail> completedProjectDetails) {
            this.completedProjectDetails = completedProjectDetails;
        }

    }

    public class CompletedProjectDetail {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("projectId")
        @Expose
        private Integer projectId;
        @SerializedName("projectid")
        @Expose
        private Integer projectid;
        @SerializedName("productname")
        @Expose
        private String productname;
        @SerializedName("productnumber")
        @Expose
        private String productnumber;
        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("crreId")
        @Expose
        private Integer crreId;
        @SerializedName("client")
        @Expose
        private String client;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("projectCompletion")
        @Expose
        private String projectCompletion;
        @SerializedName("remaining")
        @Expose
        private String remaining;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("paid")
        @Expose
        private String paid;
        @SerializedName("balance")
        @Expose
        private String balance;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("registrationexpert")
        @Expose
        private String registrationexpert;
        @SerializedName("bidamount")
        @Expose
        private String bidamount;
        @SerializedName("paidamount")
        @Expose
        private String paidamount;
        @SerializedName("balanceamount")
        @Expose
        private String balanceamount;
        @SerializedName("nextdueamount")
        @Expose
        private String nextdueamount;
        @SerializedName("completiondate")
        @Expose
        private String completiondate;
        @SerializedName("projectstatus")
        @Expose
        private String projectstatus;

        public Integer getProductid() {
            return productid;
        }

        public Integer getProjectId() {
            return projectId;
        }

        public void setProjectId(Integer projectId) {
            this.projectId = projectId;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getProductname() {
            return productname;
        }

        public void setProductname(String productname) {
            this.productname = productname;
        }

        public String getProductnumber() {
            return productnumber;
        }

        public void setProductnumber(String productnumber) {
            this.productnumber = productnumber;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getRegistrationexpert() {
            return registrationexpert;
        }

        public void setRegistrationexpert(String registrationexpert) {
            this.registrationexpert = registrationexpert;
        }

        public String getBidamount() {
            return bidamount;
        }

        public void setBidamount(String bidamount) {
            this.bidamount = bidamount;
        }

        public String getPaidamount() {
            return paidamount;
        }

        public void setPaidamount(String paidamount) {
            this.paidamount = paidamount;
        }

        public String getBalanceamount() {
            return balanceamount;
        }

        public void setBalanceamount(String balanceamount) {
            this.balanceamount = balanceamount;
        }

        public String getNextdueamount() {
            return nextdueamount;
        }

        public void setNextdueamount(String nextdueamount) {
            this.nextdueamount = nextdueamount;
        }

        public String getCompletiondate() {
            return completiondate;
        }

        public void setCompletiondate(String completiondate) {
            this.completiondate = completiondate;
        }

        public String getProjectstatus() {
            return projectstatus;
        }

        public void setProjectstatus(String projectstatus) {
            this.projectstatus = projectstatus;
        }

        public Integer getCrreId() {
            return crreId;
        }

        public void setCrreId(Integer crreId) {
            this.crreId = crreId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getProjectCompletion() {
            return projectCompletion;
        }

        public void setProjectCompletion(String projectCompletion) {
            this.projectCompletion = projectCompletion;
        }

        public String getRemaining() {
            return remaining;
        }

        public void setRemaining(String remaining) {
            this.remaining = remaining;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPaid() {
            return paid;
        }

        public void setPaid(String paid) {
            this.paid = paid;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getClient() {
            return client;
        }

        public void setClient(String client) {
            this.client = client;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getProjectid() {
            return projectid;
        }

        public void setProjectid(Integer projectid) {
            this.projectid = projectid;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }
    }
}
