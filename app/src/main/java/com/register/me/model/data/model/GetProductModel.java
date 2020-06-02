package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Jennifer - AIT on 19-02-2020PM 05:36.
 */
public class GetProductModel {

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

        @SerializedName("products")
        @Expose
        private List<Product> products = null;

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

    }

    public class Product {

        @SerializedName("product")
        @Expose
        private Product_ product;
        @SerializedName("isprojectavailable")
        @Expose
        private Boolean isprojectavailable;
        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("isEdit")
        @Expose
        private Boolean isEdit;
        @SerializedName("isView")
        @Expose
        private Boolean isView;
        @SerializedName("isCancel")
        @Expose
        private Boolean isCancel;
        @SerializedName("isinitiatebid")
        @Expose
        private Boolean isinitiatebid;
        @SerializedName("isdirectassign")
        @Expose
        private Boolean isdirectassign;
        @SerializedName("isdirectassignment")
        @Expose
        private Boolean isdirectassignment;
        @SerializedName("status")
        @Expose
        private String status;

        public Product_ getProduct() {
            return product;
        }

        public void setProduct(Product_ product) {
            this.product = product;
        }

        public Boolean getIsprojectavailable() {
            return isprojectavailable;
        }

        public void setIsprojectavailable(Boolean isprojectavailable) {
            this.isprojectavailable = isprojectavailable;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Boolean getIsEdit() {
            return isEdit;
        }

        public void setIsEdit(Boolean isEdit) {
            this.isEdit = isEdit;
        }

        public Boolean getIsView() {
            return isView;
        }

        public void setIsView(Boolean isView) {
            this.isView = isView;
        }

        public Boolean getIsCancel() {
            return isCancel;
        }

        public void setIsCancel(Boolean isCancel) {
            this.isCancel = isCancel;
        }

        public Boolean getIsinitiatebid() {
            return isinitiatebid;
        }

        public void setIsinitiatebid(Boolean isinitiatebid) {
            this.isinitiatebid = isinitiatebid;
        }

        public Boolean getIsdirectassign() {
            return isdirectassign;
        }

        public void setIsdirectassign(Boolean isdirectassign) {
            this.isdirectassign = isdirectassign;
        }

        public Boolean getIsdirectassignment() {
            return isdirectassignment;
        }

        public void setIsdirectassignment(Boolean isdirectassignment) {
            this.isdirectassignment = isdirectassignment;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public class Product_ {

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
        private List<String> deviceClassification = null;
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
        private List<String> connectionType = null;
        @SerializedName("requiredSterilization")
        @Expose
        private Boolean requiredSterilization;
        @SerializedName("deviceType")
        @Expose
        private List<String> deviceType = null;
        @SerializedName("useEnvironment")
        @Expose
        private List<String> useEnvironment = null;
        @SerializedName("isCombinationDevice")
        @Expose
        private Boolean isCombinationDevice;
        @SerializedName("isElectricalDevice")
        @Expose
        private Boolean isElectricalDevice;
        @SerializedName("electricalDeviceType")
        @Expose
        private List<String> electricalDeviceType = null;
        @SerializedName("isPediatric")
        @Expose
        private Boolean isPediatric;
        @SerializedName("sterilizationType")
        @Expose
        private List<String> sterilizationType = null;
        @SerializedName("indications")
        @Expose
        private List<String> indications = null;
        @SerializedName("intendedPopulation")
        @Expose
        private List<String> intendedPopulation = null;
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
        @SerializedName("active")
        @Expose
        private Boolean active;

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

        public List<String> getDeviceClassification() {
            return deviceClassification;
        }

        public void setDeviceClassification(List<String> deviceClassification) {
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

        public List<String> getConnectionType() {
            return connectionType;
        }

        public void setConnectionType(List<String> connectionType) {
            this.connectionType = connectionType;
        }

        public Boolean getRequiredSterilization() {
            return requiredSterilization;
        }

        public void setRequiredSterilization(Boolean requiredSterilization) {
            this.requiredSterilization = requiredSterilization;
        }

        public List<String> getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(List<String> deviceType) {
            this.deviceType = deviceType;
        }

        public List<String> getUseEnvironment() {
            return useEnvironment;
        }

        public void setUseEnvironment(List<String> useEnvironment) {
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

        public List<String> getElectricalDeviceType() {
            return electricalDeviceType;
        }

        public void setElectricalDeviceType(List<String> electricalDeviceType) {
            this.electricalDeviceType = electricalDeviceType;
        }

        public Boolean getIsPediatric() {
            return isPediatric;
        }

        public void setIsPediatric(Boolean isPediatric) {
            this.isPediatric = isPediatric;
        }

        public List<String> getSterilizationType() {
            return sterilizationType;
        }

        public void setSterilizationType(List<String> sterilizationType) {
            this.sterilizationType = sterilizationType;
        }

        public List<String> getIndications() {
            return indications;
        }

        public void setIndications(List<String> indications) {
            this.indications = indications;
        }

        public List<String> getIntendedPopulation() {
            return intendedPopulation;
        }

        public void setIntendedPopulation(List<String> intendedPopulation) {
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

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

    }
}