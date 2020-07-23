package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.UserInfo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MasterViewDetailPresenter implements Utils.UtilNetworkInterface {

    private Context context;
    @Inject
    MasterNetworkCall masterNetworkCall;
    @Inject
    Utils utils;
    private Observer<String> message;
    MVDListener listener;
    private Observer<RREApplication> dcObserver;
    private String extension;

    public void init(Context context,MVDListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
               if (utils.isOnline(context)) { listener.showMessage(s); }
               listener.hideProgress();
               masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.init(context,message,this);
    }

    public ArrayList<KeyValue> getUserDetails(UserInfo userInfo) {
        ArrayList<KeyValue> list = new ArrayList<>();
        UserInfo.User info = userInfo.getData().getUser();
        list.add(new KeyValue("First Name", info.getFirstName(), null));
         String lastName = info.getLastName();
        if (lastName.isEmpty()) {
            lastName = "-";
        }
        list.add(new KeyValue("Last Name", lastName !=null? lastName:"-", null));
        list.add(new KeyValue("E-Mail Address", info.getEmailAddress()!=null? info.getEmailAddress():"-", null));
        list.add(new KeyValue("Telephone", info.getTelephone()!=null? info.getTelephone():"-", null));
        if(info.getOrganization()!=null) {
            list.add(new KeyValue("Company Name", info.getOrganization().getCompanyName(), null));
            list.add(new KeyValue("Division", info.getOrganization().getDivision(), null));
            list.add(new KeyValue("Address", info.getOrganization().getAddress(), null));
            list.add(new KeyValue("City", info.getOrganization().getCity(), null));
            list.add(new KeyValue("State", info.getOrganization().getState(), null));
            list.add(new KeyValue("Postal Code", info.getOrganization().getPostalCode(), null));
            list.add(new KeyValue("Country", info.getOrganization().getCountry(), null));
        }
        return list;

    }


    public void getdocCommentsData(Integer id) {
        dcObserver=new Observer<RREApplication>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RREApplication rreApplication) {
                if (rreApplication.getData()!=null) {
                    final List<RREApplication.Document> document = rreApplication.getData().getDocument();
                        listener.updateDocs(document);
                    List<RREApplication.Comment> comment = rreApplication.getData().getComments();
                        listener.updateComments(comment);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.getDocCommentDetails(String.valueOf(id),dcObserver);
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
    @Override
    public void refreshNetwork() {
        listener.onRefresh();
    }

    public interface MVDListener{
        void showMessage(String s);

        void onRefresh();

        void updateDocs(List<RREApplication.Document> viewList);

        void updateComments(List<RREApplication.Comment> comment);

        void hideProgress();
    }
}
