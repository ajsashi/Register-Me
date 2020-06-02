package com.register.me.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DocumentPresenter {
    private Context context;
    @Inject
    JsonBuilder builder;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
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
        return constants.getApplicationData().getData().getDocument();
    }

    public String getFileName(String doc) {
        String fileName = doc;
        int slash = doc.lastIndexOf("/");
        fileName = fileName.substring(slash+1);
        int dot = fileName.lastIndexOf(".");
        extension = fileName.substring(dot);
        return fileName;
    }

    public String getExtension() {
        return extension.replace(".","");
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

        constants.getApplicationData().getData().setDocument(document);
    }

    public int getRole() {
        return constants.getuserRole();
    }

    public interface IDocument {

    }
}
