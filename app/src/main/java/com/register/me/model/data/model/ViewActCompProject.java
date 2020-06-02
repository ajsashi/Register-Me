package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jennifer - AIT on 05-03-2020PM 07:21.
 */
public class ViewActCompProject {

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

        @SerializedName("productdetails")
        @Expose
        private Productdetails productdetails;
        @SerializedName("project_opportunity")
        @Expose
        private ProjectOpportunity projectOpportunity;
        @SerializedName("projectlocation")
        @Expose
        private Projectlocation projectlocation;
        @SerializedName("crrebiddingdetails")
        @Expose
        private Crrebiddingdetails crrebiddingdetails;
        @SerializedName("paymentdetails")
        @Expose
        private List<Paymentdetail> paymentdetails = null;
        @SerializedName("comments")
        @Expose
        private List<Comment> comments = null;

        public Productdetails getProductdetails() {
            return productdetails;
        }

        public void setProductdetails(Productdetails productdetails) {
            this.productdetails = productdetails;
        }

        public ProjectOpportunity getProjectOpportunity() {
            return projectOpportunity;
        }

        public void setProjectOpportunity(ProjectOpportunity projectOpportunity) {
            this.projectOpportunity = projectOpportunity;
        }

        public Projectlocation getProjectlocation() {
            return projectlocation;
        }

        public void setProjectlocation(Projectlocation projectlocation) {
            this.projectlocation = projectlocation;
        }

        public Crrebiddingdetails getCrrebiddingdetails() {
            return crrebiddingdetails;
        }

        public void setCrrebiddingdetails(Crrebiddingdetails crrebiddingdetails) {
            this.crrebiddingdetails = crrebiddingdetails;
        }

        public List<Paymentdetail> getPaymentdetails() {
            return paymentdetails;
        }

        public void setPaymentdetails(List<Paymentdetail> paymentdetails) {
            this.paymentdetails = paymentdetails;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

    }

    public class Paymentdetail {

        @SerializedName("transactionID")
        @Expose
        private String transactionID;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("bankAccount")
        @Expose
        private String bankAccount;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("url")
        @Expose
        private String url;

        public String getTransactionID() {
            return transactionID;
        }

        public void setTransactionID(String transactionID) {
            this.transactionID = transactionID;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public class Comment {

        @SerializedName("commentID")
        @Expose
        private Integer commentID;
        @SerializedName("commentTopic")
        @Expose
        private String commentTopic;
        @SerializedName("createdby")
        @Expose
        private String createdby;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("subDescriptions")
        @Expose
        private List<SubDescription> subDescriptions = null;

        public Integer getCommentID() {
            return commentID;
        }

        public void setCommentID(Integer commentID) {
            this.commentID = commentID;
        }

        public String getCommentTopic() {
            return commentTopic;
        }

        public void setCommentTopic(String commentTopic) {
            this.commentTopic = commentTopic;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<SubDescription> getSubDescriptions() {
            return subDescriptions;
        }

        public void setSubDescriptions(List<SubDescription> subDescriptions) {
            this.subDescriptions = subDescriptions;
        }

    }

    public class SubDescription {

        @SerializedName("subDescription")
        @Expose
        private String subDescription;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;

        public String getSubDescription() {
            return subDescription;
        }

        public void setSubDescription(String subDescription) {
            this.subDescription = subDescription;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

    }

    public class Productdetails {

        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("projectAssignId")
        @Expose
        private Integer projectAssignId;
        @SerializedName("productName")
        @Expose
        private String productName;
        @SerializedName("productNumber")
        @Expose
        private String productNumber;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("deviceClassification")
        @Expose
        private String deviceClassification;
        @SerializedName("ivd")
        @Expose
        private Boolean ivd;
        @SerializedName("lifeSupporting")
        @Expose
        private Boolean lifeSupporting;
        @SerializedName("patientContactComponent")
        @Expose
        private Boolean patientContactComponent;
        @SerializedName("isitAnImplant")
        @Expose
        private Boolean isitAnImplant;
        @SerializedName("useSoftware")
        @Expose
        private Boolean useSoftware;
        @SerializedName("digitalHealthTechnology")
        @Expose
        private Boolean digitalHealthTechnology;
        @SerializedName("connectionType")
        @Expose
        private String connectionType;
        @SerializedName("requiredSterilization")
        @Expose
        private Boolean requiredSterilization;
        @SerializedName("deviceType")
        @Expose
        private String deviceType;
        @SerializedName("useEnvironment")
        @Expose
        private String useEnvironment;
        @SerializedName("isCombinationDevice")
        @Expose
        private Boolean isCombinationDevice;
        @SerializedName("isElectricalDevice")
        @Expose
        private Boolean isElectricalDevice;
        @SerializedName("electricalDeviceType")
        @Expose
        private String electricalDeviceType;
        @SerializedName("isPediatric")
        @Expose
        private Boolean isPediatric;
        @SerializedName("sterilizationType")
        @Expose
        private String sterilizationType;
        @SerializedName("indications")
        @Expose
        private String indications;
        @SerializedName("intendedPopulation")
        @Expose
        private String intendedPopulation;
        @SerializedName("emitRadiation")
        @Expose
        private Boolean emitRadiation;
        @SerializedName("biologicalOrigin")
        @Expose
        private Boolean biologicalOrigin;
        @SerializedName("reprocessSUD")
        @Expose
        private Boolean reprocessSUD;
        @SerializedName("createdDate")
        @Expose
        private String createdDate;
        @SerializedName("modifiedDate")
        @Expose
        private String modifiedDate;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getProjectAssignId() {
            return projectAssignId;
        }

        public void setProjectAssignId(Integer projectAssignId) {
            this.projectAssignId = projectAssignId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductNumber() {
            return productNumber;
        }

        public void setProductNumber(String productNumber) {
            this.productNumber = productNumber;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDeviceClassification() {
            return deviceClassification;
        }

        public void setDeviceClassification(String deviceClassification) {
            this.deviceClassification = deviceClassification;
        }

        public Boolean getIvd() {
            return ivd;
        }

        public void setIvd(Boolean ivd) {
            this.ivd = ivd;
        }

        public Boolean getLifeSupporting() {
            return lifeSupporting;
        }

        public void setLifeSupporting(Boolean lifeSupporting) {
            this.lifeSupporting = lifeSupporting;
        }

        public Boolean getPatientContactComponent() {
            return patientContactComponent;
        }

        public void setPatientContactComponent(Boolean patientContactComponent) {
            this.patientContactComponent = patientContactComponent;
        }

        public Boolean getIsitAnImplant() {
            return isitAnImplant;
        }

        public void setIsitAnImplant(Boolean isitAnImplant) {
            this.isitAnImplant = isitAnImplant;
        }

        public Boolean getUseSoftware() {
            return useSoftware;
        }

        public void setUseSoftware(Boolean useSoftware) {
            this.useSoftware = useSoftware;
        }

        public Boolean getDigitalHealthTechnology() {
            return digitalHealthTechnology;
        }

        public void setDigitalHealthTechnology(Boolean digitalHealthTechnology) {
            this.digitalHealthTechnology = digitalHealthTechnology;
        }

        public String getConnectionType() {
            return connectionType;
        }

        public void setConnectionType(String connectionType) {
            this.connectionType = connectionType;
        }

        public Boolean getRequiredSterilization() {
            return requiredSterilization;
        }

        public void setRequiredSterilization(Boolean requiredSterilization) {
            this.requiredSterilization = requiredSterilization;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getUseEnvironment() {
            return useEnvironment;
        }

        public void setUseEnvironment(String useEnvironment) {
            this.useEnvironment = useEnvironment;
        }

        public Boolean getIsCombinationDevice() {
            return isCombinationDevice;
        }

        public void setIsCombinationDevice(Boolean isCombinationDevice) {
            this.isCombinationDevice = isCombinationDevice;
        }

        public Boolean getIsElectricalDevice() {
            return isElectricalDevice;
        }

        public void setIsElectricalDevice(Boolean isElectricalDevice) {
            this.isElectricalDevice = isElectricalDevice;
        }

        public String getElectricalDeviceType() {
            return electricalDeviceType;
        }

        public void setElectricalDeviceType(String electricalDeviceType) {
            this.electricalDeviceType = electricalDeviceType;
        }

        public Boolean getIsPediatric() {
            return isPediatric;
        }

        public void setIsPediatric(Boolean isPediatric) {
            this.isPediatric = isPediatric;
        }

        public String getSterilizationType() {
            return sterilizationType;
        }

        public void setSterilizationType(String sterilizationType) {
            this.sterilizationType = sterilizationType;
        }

        public String getIndications() {
            return indications;
        }

        public void setIndications(String indications) {
            this.indications = indications;
        }

        public String getIntendedPopulation() {
            return intendedPopulation;
        }

        public void setIntendedPopulation(String intendedPopulation) {
            this.intendedPopulation = intendedPopulation;
        }

        public Boolean getEmitRadiation() {
            return emitRadiation;
        }

        public void setEmitRadiation(Boolean emitRadiation) {
            this.emitRadiation = emitRadiation;
        }

        public Boolean getBiologicalOrigin() {
            return biologicalOrigin;
        }

        public void setBiologicalOrigin(Boolean biologicalOrigin) {
            this.biologicalOrigin = biologicalOrigin;
        }

        public Boolean getReprocessSUD() {
            return reprocessSUD;
        }

        public void setReprocessSUD(Boolean reprocessSUD) {
            this.reprocessSUD = reprocessSUD;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

        public String getModifiedDate() {
            return modifiedDate;
        }

        public void setModifiedDate(String modifiedDate) {
            this.modifiedDate = modifiedDate;
        }

    }

    public class ProjectOpportunity {

        @SerializedName("addpayicon")
        @Expose
        private Boolean addpayicon;
        @SerializedName("projectStatus")
        @Expose
        private String projectStatus;
        @SerializedName("completionDate")
        @Expose
        private Object completionDate;
        @SerializedName("bidAmount")
        @Expose
        private String bidAmount;
        @SerializedName("paidAmount")
        @Expose
        private String paidAmount;
        @SerializedName("balanceAmount")
        @Expose
        private String balanceAmount;
        @SerializedName("nextDueAmount")
        @Expose
        private String nextDueAmount;

        public Boolean getAddpayicon() {
            return addpayicon;
        }

        public void setAddpayicon(Boolean addpayicon) {
            this.addpayicon = addpayicon;
        }

        public String getProjectStatus() {
            return projectStatus;
        }

        public void setProjectStatus(String projectStatus) {
            this.projectStatus = projectStatus;
        }

        public Object getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(Object completionDate) {
            this.completionDate = completionDate;
        }

        public String getBidAmount() {
            return bidAmount;
        }

        public void setBidAmount(String bidAmount) {
            this.bidAmount = bidAmount;
        }

        public String getPaidAmount() {
            return paidAmount;
        }

        public void setPaidAmount(String paidAmount) {
            this.paidAmount = paidAmount;
        }

        public String getBalanceAmount() {
            return balanceAmount;
        }

        public void setBalanceAmount(String balanceAmount) {
            this.balanceAmount = balanceAmount;
        }

        public String getNextDueAmount() {
            return nextDueAmount;
        }

        public void setNextDueAmount(String nextDueAmount) {
            this.nextDueAmount = nextDueAmount;
        }

    }

    public class Projectlocation {

        @SerializedName("country")
        @Expose
        private String country;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }

    public class Crrebiddingdetails {

        @SerializedName("registrationExpertName")
        @Expose
        private String registrationExpertName;
        @SerializedName("projectAmount")
        @Expose
        private String projectAmount;
        @SerializedName("projectDuration")
        @Expose
        private String projectDuration;
        @SerializedName("biddingstatus")
        @Expose
        private String biddingstatus;

        public String getRegistrationExpertName() {
            return registrationExpertName;
        }

        public void setRegistrationExpertName(String registrationExpertName) {
            this.registrationExpertName = registrationExpertName;
        }

        public String getProjectAmount() {
            return projectAmount;
        }

        public void setProjectAmount(String projectAmount) {
            this.projectAmount = projectAmount;
        }

        public String getProjectDuration() {
            return projectDuration;
        }

        public void setProjectDuration(String projectDuration) {
            this.projectDuration = projectDuration;
        }

        public String getBiddingstatus() {
            return biddingstatus;
        }

        public void setBiddingstatus(String biddingstatus) {
            this.biddingstatus = biddingstatus;
        }

    }
}
