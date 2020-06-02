package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.ActiveCompProject;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 04-03-2020PM 05:14.
 */
public class ActiveCompProjectPresenter implements ClientNetworkCall.NetworkCallInterface {

    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;

    private Context context;
    private IACProject listener;

    public void init(Context context, IACProject listener){
        this.context =context;
        this.listener =listener;
        ((BaseActivity)context).injector().inject(this);
    }

    public boolean getIsActive() {
        return constants.isAcitiveProject();
    }

    public void getActiveCompleteList() {
        if(utils.isOnline(context)){
            listener.showProgress();
            String token = repo.getData(constants.getcacheTokenKey());
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            networkCall.getACProjectList(apiInterface,token,this);
        }else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void onCallSuccess(Object response) {
listener.dismissProgress();
        ActiveCompProject res = (ActiveCompProject) response;

        List<ActiveCompProject.ActiveProjectDetail> activeList = res.getData().getActiveProjectDetails();
        List<ActiveCompProject.CompletedProjectDetail> completedList = res.getData().getCompletedProjectDetails();
        if(getIsActive()){
            listener.updateActiveProject(activeList);
        }else {
            listener.updateCompleteProject(completedList);
        }

    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();
        listener.showMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.dismissProgress();
        listener.showMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(),"false");
        repo.storeData(constants.getCACHE_USER_INFO(),null);
        utils.sessionExpired(context);
    }

    public void setId(int id) {
        constants.setviewScreenFrom(3);
        constants.setProductID(null);
        constants.setProjectID(String.valueOf(id));
    }

    public String getUserRole() {
        return String.valueOf(constants.getuserRole());
    }

    public interface IACProject {
        void showMessage(String string);

        void updateActiveProject(List<ActiveCompProject.ActiveProjectDetail> activeList);

        void updateCompleteProject(List<ActiveCompProject.CompletedProjectDetail> completedList);

        void showProgress();

        void dismissProgress();

    }
}
