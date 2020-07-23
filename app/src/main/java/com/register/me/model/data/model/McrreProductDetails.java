package com.register.me.model.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class McrreProductDetails {

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

        @SerializedName("productDetails")
        @Expose
        private ProductDetails productDetails;

        public ProductDetails getProductDetails() {
            return productDetails;
        }

        public void setProductDetails(ProductDetails productDetails) {
            this.productDetails = productDetails;
        }

    }

    public class ProductDetails {

        @SerializedName("product")
        @Expose
        private Product product;
        @SerializedName("projectlocation")
        @Expose
        private Object projectlocation;
        @SerializedName("crrebiddingdetails")
        @Expose
        private Object crrebiddingdetails;
        @SerializedName("clientName")
        @Expose
        private Object clientName;
        @SerializedName("companyName")
        @Expose
        private Object companyName;
        @SerializedName("avalabletoBid")
        @Expose
        private Integer avalabletoBid;
        @SerializedName("submittedBid")
        @Expose
        private Integer submittedBid;
        @SerializedName("completionDate")
        @Expose
        private String completionDate;
        @SerializedName("startdate")
        @Expose
        private String startdate;
        @SerializedName("enddate")
        @Expose
        private String enddate;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public Object getProjectlocation() {
            return projectlocation;
        }

        public void setProjectlocation(Object projectlocation) {
            this.projectlocation = projectlocation;
        }

        public Object getCrrebiddingdetails() {
            return crrebiddingdetails;
        }

        public void setCrrebiddingdetails(Object crrebiddingdetails) {
            this.crrebiddingdetails = crrebiddingdetails;
        }

        public Object getClientName() {
            return clientName;
        }

        public void setClientName(Object clientName) {
            this.clientName = clientName;
        }

        public Object getCompanyName() {
            return companyName;
        }

        public void setCompanyName(Object companyName) {
            this.companyName = companyName;
        }

        public Integer getAvalabletoBid() {
            return avalabletoBid;
        }

        public void setAvalabletoBid(Integer avalabletoBid) {
            this.avalabletoBid = avalabletoBid;
        }

        public Integer getSubmittedBid() {
            return submittedBid;
        }

        public void setSubmittedBid(Integer submittedBid) {
            this.submittedBid = submittedBid;
        }

        public String getCompletionDate() {
            return completionDate;
        }

        public void setCompletionDate(String completionDate) {
            this.completionDate = completionDate;
        }

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

    }

    public class Product {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
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
        private Object electricalDeviceType;
        @SerializedName("isPediatric")
        @Expose
        private Boolean isPediatric;
        @SerializedName("sterilizationType")
        @Expose
        private Object sterilizationType;
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

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
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

        public Object getElectricalDeviceType() {
            return electricalDeviceType;
        }

        public void setElectricalDeviceType(Object electricalDeviceType) {
            this.electricalDeviceType = electricalDeviceType;
        }

        public Boolean getIsPediatric() {
            return isPediatric;
        }

        public void setIsPediatric(Boolean isPediatric) {
            this.isPediatric = isPediatric;
        }

        public Object getSterilizationType() {
            return sterilizationType;
        }

        public void setSterilizationType(Object sterilizationType) {
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
}


