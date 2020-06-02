package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RREApplication {

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

    public class Application {

        @SerializedName("registeredproducts")
        @Expose
        private Integer registeredproducts;
        @SerializedName("registeredProductDescription")
        @Expose
        private String registeredProductDescription;
        @SerializedName("deviceClassification")
        @Expose
        private List<String> deviceClassification = null;
        @SerializedName("ivd")
        @Expose
        private Boolean ivd;
        @SerializedName("lifeSupporting")
        @Expose
        private Boolean lifeSupporting;
        @SerializedName("patientContactComponents")
        @Expose
        private Boolean patientContactComponents;
        @SerializedName("isItAnImplant")
        @Expose
        private Object isItAnImplant;
        @SerializedName("usedSoftwareProducts")
        @Expose
        private Integer usedSoftwareProducts;
        @SerializedName("connectionType")
        @Expose
        private List<String> connectionType = null;
        @SerializedName("sterilizedProducts")
        @Expose
        private Integer sterilizedProducts;
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

        public Integer getRegisteredproducts() {
            return registeredproducts;
        }

        public void setRegisteredproducts(Integer registeredproducts) {
            this.registeredproducts = registeredproducts;
        }

        public String getRegisteredProductDescription() {
            return registeredProductDescription;
        }

        public void setRegisteredProductDescription(String registeredProductDescription) {
            this.registeredProductDescription = registeredProductDescription;
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

        public Boolean getPatientContactComponents() {
            return patientContactComponents;
        }

        public void setPatientContactComponents(Boolean patientContactComponents) {
            this.patientContactComponents = patientContactComponents;
        }

        public Object getIsItAnImplant() {
            return isItAnImplant;
        }

        public void setIsItAnImplant(Object isItAnImplant) {
            this.isItAnImplant = isItAnImplant;
        }

        public Integer getUsedSoftwareProducts() {
            return usedSoftwareProducts;
        }

        public void setUsedSoftwareProducts(Integer usedSoftwareProducts) {
            this.usedSoftwareProducts = usedSoftwareProducts;
        }

        public List<String> getConnectionType() {
            return connectionType;
        }

        public void setConnectionType(List<String> connectionType) {
            this.connectionType = connectionType;
        }

        public Integer getSterilizedProducts() {
            return sterilizedProducts;
        }

        public void setSterilizedProducts(Integer sterilizedProducts) {
            this.sterilizedProducts = sterilizedProducts;
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

    }

    public class Comment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("createddate")
        @Expose
        private String createddate;
        @SerializedName("comment")
        @Expose
        private String comment;
        @SerializedName("subcomments")
        @Expose
        private List<Subcomment> subcomments = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<Subcomment> getSubcomments() {
            return subcomments;
        }

        public void setSubcomments(List<Subcomment> subcomments) {
            this.subcomments = subcomments;
        }

    }

    public class Data {

        @SerializedName("application")
        @Expose
        private Application application;
        @SerializedName("document")
        @Expose
        private List<Document> document = null;
        @SerializedName("comments")
        @Expose
        private List<Comment> comments = null;

        public Application getApplication() {
            return application;
        }

        public void setApplication(Application application) {
            this.application = application;
        }

        public List<Document> getDocument() {
            return document;
        }

        public void setDocument(List<Document> document) {
            this.document = document;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

    }


    public class Document {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("document")
        @Expose
        private String document;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getDocument() {
            return document;
        }

        public void setDocument(String document) {
            this.document = document;
        }

    }

    public class Subcomment {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("createddate")
        @Expose
        private String createddate;
        @SerializedName("comment")
        @Expose
        private String comment;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }
}

