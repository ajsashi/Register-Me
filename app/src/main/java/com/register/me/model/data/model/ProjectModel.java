package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jennifer - AIT on 02-03-2020PM 04:18.
 */
public class ProjectModel {
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

        @SerializedName("productID")
        @Expose
        private Integer productId;
        @SerializedName("projects")
        @Expose
        private List<Project> projects = null;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public List<Project> getProjects() {
            return projects;
        }

        public void setProjects(List<Project> projects) {
            this.projects = projects;
        }

    }

    public class Project {

        @SerializedName("product_oppurtunity")
        @Expose
        private ProductOppurtunity productOppurtunity;
        @SerializedName("projectlocations")
        @Expose
        private Projectlocations projectlocations;
        @SerializedName("projectassign")
        @Expose
        private Projectassign projectassign;
        @SerializedName("isView")
        @Expose
        private Boolean isView;
        @SerializedName("isdirectassignment")
        @Expose
        private Boolean isdirectassignment;
        @SerializedName("isCancel")
        @Expose
        private Boolean isCancel;
        @SerializedName("bidstatus")
        @Expose
        private String bidstatus;

        public ProductOppurtunity getProductOppurtunity() {
            return productOppurtunity;
        }

        public void setProductOppurtunity(ProductOppurtunity productOppurtunity) {
            this.productOppurtunity = productOppurtunity;
        }

        public Projectlocations getProjectlocations() {
            return projectlocations;
        }

        public void setProjectlocations(Projectlocations projectlocations) {
            this.projectlocations = projectlocations;
        }

        public Projectassign getProjectassign() {
            return projectassign;
        }

        public void setProjectassign(Projectassign projectassign) {
            this.projectassign = projectassign;
        }

        public Boolean getIsView() {
            return isView;
        }

        public void setIsView(Boolean isView) {
            this.isView = isView;
        }

        public Boolean getIsdirectassignment() {
            return isdirectassignment;
        }

        public void setIsdirectassignment(Boolean isdirectassignment) {
            this.isdirectassignment = isdirectassignment;
        }

        public Boolean getIsCancel() {
            return isCancel;
        }

        public void setIsCancel(Boolean isCancel) {
            this.isCancel = isCancel;
        }

        public String getBidstatus() {
            return bidstatus;
        }

        public void setBidstatus(String bidstatus) {
            this.bidstatus = bidstatus;
        }

    }


    public class Projectassign {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("assignedDate")
        @Expose
        private String assignedDate;
        @SerializedName("active")
        @Expose
        private Integer active;
        @SerializedName("clientStatus")
        @Expose
        private Object clientStatus;
        @SerializedName("completedStatus")
        @Expose
        private Object completedStatus;
        @SerializedName("crreStatus")
        @Expose
        private Object crreStatus;
        @SerializedName("ratings")
        @Expose
        private Object ratings;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getAssignedDate() {
            return assignedDate;
        }

        public void setAssignedDate(String assignedDate) {
            this.assignedDate = assignedDate;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }

        public Object getClientStatus() {
            return clientStatus;
        }

        public void setClientStatus(Object clientStatus) {
            this.clientStatus = clientStatus;
        }

        public Object getCompletedStatus() {
            return completedStatus;
        }

        public void setCompletedStatus(Object completedStatus) {
            this.completedStatus = completedStatus;
        }

        public Object getCrreStatus() {
            return crreStatus;
        }

        public void setCrreStatus(Object crreStatus) {
            this.crreStatus = crreStatus;
        }

        public Object getRatings() {
            return ratings;
        }

        public void setRatings(Object ratings) {
            this.ratings = ratings;
        }

    }


    public class Projectlocations {

        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("locationid")
        @Expose
        private int locationid;

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public int getLocationid() {
            return locationid;
        }

        public void setLocationid(int locationid) {
            this.locationid = locationid;
        }
    }

    public class ProductOppurtunity {

        @SerializedName("projectid")
        @Expose
        private Integer projectid;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("projectDeadline")
        @Expose
        private String projectDeadline;
        @SerializedName("budget")
        @Expose
        private String budget;
        @SerializedName("bidStartDate")
        @Expose
        private String bidStartDate;
        @SerializedName("bidEndDate")
        @Expose
        private String bidEndDate;
        @SerializedName("isCancel")
        @Expose
        private Integer isCancel;
        @SerializedName("cancelReason")
        @Expose
        private String cancelReason;

        public Integer getProjectid() {
            return projectid;
        }

        public void setProjectid(Integer projectid) {
            this.projectid = projectid;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getProjectDeadline() {
            return projectDeadline;
        }

        public void setProjectDeadline(String projectDeadline) {
            this.projectDeadline = projectDeadline;
        }

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getBidStartDate() {
            return bidStartDate;
        }

        public void setBidStartDate(String bidStartDate) {
            this.bidStartDate = bidStartDate;
        }

        public String getBidEndDate() {
            return bidEndDate;
        }

        public void setBidEndDate(String bidEndDate) {
            this.bidEndDate = bidEndDate;
        }

        public Integer getIsCancel() {
            return isCancel;
        }

        public void setIsCancel(Integer isCancel) {
            this.isCancel = isCancel;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
            this.cancelReason = cancelReason;
        }

    }
}


