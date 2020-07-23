package com.register.me.presenter;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.model.RRE;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.internal.Util;

public class DocumentPresenter {
    private Context context;
    @Inject
    JsonBuilder builder;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    private String extension;

    public void init(Context context, IDocument listener) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
    }

    public JsonObject getDocJson(ArrayList<QandA> list) {
        return builder.uploadDocument(list);
    }

    public String getToken() {
        return repo.getData(constants.getcacheTokenKey());
    }

    public List<RREApplication.Document> getDocuments() {

        final RREApplication applicationData = constants.getApplicationData();
        List<RREApplication.Document> document;
        // For Low memory devices the data will be cleared in backgroud to handle such case following code is used.
        if (applicationData != null) {
            document = applicationData.getData().getDocument();
        }else {
            RREApplication data = new Gson().fromJson(repo.getData(constants.getCACHE_APPLICATION_DATA()), RREApplication.class);
            document = data.getData().getDocument();
        }

        if (document != null) {
            return document;
        }
        return null;
    }

    public String getFileName(String doc) {
        String fileName = doc;
        int slash = doc.lastIndexOf("/");

        fileName = fileName.substring(slash + 1);
        int dot = fileName.lastIndexOf(".");
        if (dot == -1) {
            extension = "";
        } else {
            extension = fileName.substring(dot);
        }
        return fileName;
    }

    public String getExtension() {
        if (!extension.isEmpty()) {
            return extension.replace(".", "");
        }
        return "";
    }

    public JsonObject getDeleteJson(RREApplication.Document doc) {
        ArrayList<QandA> dataList = new ArrayList<>();
        dataList.add(new QandA("data", "", 0, 0, 0, "", null, null));
        dataList.add(new QandA("filename", "", 0, 0, 0, "", null, null));
        dataList.add(new QandA("type", "", 0, 0, 0, "", null, null));
        dataList.add(new QandA("docid", String.valueOf(doc.getId()), 0, 0, 0, "", null, null));
        dataList.add(new QandA("action", "del", 0, 0, 0, "", null, null));

        return getDocJson(dataList);
    }


    public void setDocumentList(List<RREApplication.Document> document) {

        RREApplication applicationData = constants.getApplicationData();
        if (applicationData == null) {
            applicationData = new Gson().fromJson(repo.getData(constants.getCACHE_APPLICATION_DATA()), RREApplication.class);
        }
        if (applicationData != null) {
            constants.getApplicationData().getData().setDocument(document);
            repo.storeData(constants.getCACHE_APPLICATION_DATA(),new Gson().toJson(constants.getApplicationData(), RREApplication.class));
            applicationData.getData().setDocument(document);
        }else {
            utils.showToastMessage(context,"Cannot Set Application Data, Check log for more info.");
        }

    }

    public int getRole() {
        return constants.getuserRole();
    }

    public boolean getContext() {
        if(context !=null){
            return true;
        }
        return false;
    }

    public interface IDocument {

    }
}
