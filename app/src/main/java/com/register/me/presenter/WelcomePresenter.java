package com.register.me.presenter;

import android.content.Context;

import com.google.gson.Gson;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 25-02-2020PM 01:23.
 */
public class WelcomePresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilNetworkInterface {

    Context context;

    @Inject
    Constants constants;
    @Inject
    CacheRepo repo;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    Utils utils;
    private IWelcome listener;


    public void init(Context context,IWelcome listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        
    }

    public synchronized void getUserProfile() {
        String data = repo.getData(constants.getCACHE_USER_INFO());
        if (data == null) {
            apiUserProfileCall();
        }
    }

    private void apiUserProfileCall() {
        if (utils.isOnline(context)) {
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            networkCall.getUserDetails(apiInterface, token, this);
        } else {
            utils.showToastMessage(context,context.getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void onCallSuccess(Object response) {
        if (response instanceof GetUserInfoModel) {
            GetUserInfoModel body = ((GetUserInfoModel) response);
            repo.storeData(constants.getCACHE_USER_INFO(), new Gson().toJson(body));
            listener.buildUI();
        }
    }

    @Override
    public void onCallFail(String message) {
        utils.showToastMessage(context,message);
    }

    @Override
    public void sessionExpired() {
        utils.showToastMessage(context,context.getString(R.string.session_expired));

        utils.sessionExpired(context, repo);
    }

    public void isOnLine() {
        if(!utils.checkAlert()){
            utils.showNetworkAlert(context,this);
        } else {
            utils.dismissAlert();
        }
    }

    @Override
    public void refreshNetwork() {
        listener.onResume();
    }

    public void setTab(int tab) {
        repo.storeData(constants.getCACHE_SELECTED_TAB(), String.valueOf(tab));
    }

    public void setApplicationData(RREApplication rreApplication) {
        repo.storeData(constants.getCACHE_APPLICATION_DATA(),new Gson().toJson(rreApplication));
        constants.setApplicationData(rreApplication);
    }


    public interface IWelcome{
        void buildUI();

        void onResume();
    }
}
