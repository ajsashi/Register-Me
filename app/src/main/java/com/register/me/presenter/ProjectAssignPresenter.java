package com.register.me.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.CrreList;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 10-03-2020PM 05:30.
 */
public class ProjectAssignPresenter implements ClientNetworkCall.NetworkCallInterface {

    private Context context;
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
    private IProjectAssign listener;
    private ApiInterface apiInterface;

    public void init(Context context,IProjectAssign listener){
        this.context = context;
        this.listener = listener;
        ((BaseActivity)context).injector().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public void getCrreList(int locationId) {
        apiCall(1,locationId);

    }

    private void apiCall(int callCase, int id) {
        if(utils.isOnline(context)){
            String token = repo.getData(constants.getcacheTokenKey());
        if(callCase==1){
            JsonObject object = new JsonObject();
            object.addProperty("locationid",id);
            object.addProperty("projectid",constants.getProjectID());
networkCall.getCrreList(apiInterface,token,object,this);
        } else if (callCase == 2) {


        }}
        else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void onCallSuccess(Object response) {
        int type = getResponseType(response);
        if (type == 1) {
            CrreList clist = (CrreList) response;
            List<CrreList.Expertlist> data = clist.getData().getExpertlist();
            listener.updateSPinner(data);
        }
    }

    private int getResponseType(Object response) {
        if(response instanceof CrreList){
            return 1;
        }
        return -1;
    }

    @Override
    public void onCallFail(String message) {
listener.showMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.showMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(),"false");
        repo.storeData(constants.getCACHE_USER_INFO(),null);
        utils.sessionExpired(context);
    }

    public interface IProjectAssign{
        void showMessage(String message);

        void updateSPinner(List<CrreList.Expertlist> data);
    }
}
