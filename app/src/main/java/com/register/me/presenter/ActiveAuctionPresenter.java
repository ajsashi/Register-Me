package com.register.me.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.ActiveAuction;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 03-03-2020AM 11:03.
 */
public class ActiveAuctionPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilAlertInterface {


    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    JsonBuilder builder;

    private Context context;
    private IActiveAuction listener;
    private Integer id;

    public void init(Context context, IActiveAuction listener) {
        this.context = context;
        ((BaseActivity) this.context).injector().inject(this);
        this.listener = listener;
    }


    public void getAuctionList() {
//        constants.setBidList(null);
        if (utils.isOnline(context)) {
            listener.showProgress();
            String token = repo.getData(constants.getcacheTokenKey());
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            networkCall.getAuctionList(apiInterface, token, this);
        } else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void onCallSuccess(Object response) {
        listener.dismissProgress();
        if (response instanceof AddProductModel) {
            listener.navigate();
        } else if (response instanceof ActiveAuction) {
            ActiveAuction resBody = (ActiveAuction) response;
            List<ActiveAuction.Auctionsprogress> data = resBody.getData().getAuctionsprogress();
            List<ActiveAuction.Bidsreadytoevaluate_> bidReadyList = resBody.getData().getBidsreadytoevaluate();
            constants.setBidList(bidReadyList);
            listener.buildUI(data);
        }


    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();
        listener.showMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.showMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(),"false");
        repo.storeData(constants.getCACHE_USER_INFO(),null);
        utils.sessionExpired(context);
    }

    public void directAssignment(Integer projectID) {
        id = projectID;
        utils.showAlert(context, 7, this);

    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("$OK$")) {
            apicall();
        }
    }

    private void apicall() {
        if (utils.isOnline(context)) {
            listener.showProgress();
            String token = repo.getData(constants.getcacheTokenKey());
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            JsonObject object = new JsonObject();
            object.addProperty("projectid", id);
            networkCall.directAssignment(apiInterface, token, object, this);
        } else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    public void setProjectID(String id) {
        constants.setviewScreenFrom(2);
        constants.setProjectID(id);
    }

    public List<ActiveAuction.Bidsreadytoevaluate_> getBidList() {
        return constants.getBidList();
    }


    public interface IActiveAuction {

        void showMessage(String message);

        void buildUI(List<ActiveAuction.Auctionsprogress> data);

        void navigate();

        void showProgress();

        void dismissProgress();
    }
}

