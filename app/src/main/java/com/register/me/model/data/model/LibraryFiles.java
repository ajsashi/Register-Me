package com.register.me.model.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LibraryFiles {

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

        @SerializedName("myLibraryfiles")
        @Expose
        private List<MyLibraryfile> myLibraryfiles = null;

        public List<MyLibraryfile> getMyLibraryfiles() {
            return myLibraryfiles;
        }

        public void setMyLibraryfiles(List<MyLibraryfile> myLibraryfiles) {
            this.myLibraryfiles = myLibraryfiles;
        }

    }

    public class MyLibraryfile {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("filename")
        @Expose
        private String filename;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

    }
}
