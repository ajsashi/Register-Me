package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class PortFolioPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilNetworkInterface {
    private View view;
    private Context context;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    CacheRepo repo;
    private boolean isList;


    public void init(View view, Context context) {
        this.view = view;
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
    }


    public void getList() {
        isList = true;
        if (utils.isOnline(context)) {
            view.showProgress();
            String token = repo.getData(constants.getcacheTokenKey());
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            networkCall.getProductList(apiInterface, token, this);
        } else {
            utils.showNetworkAlert(context,this);
        }

    }

    public List<GetProductModel.Product> checkData() {
        return constants.getBASE_PRODUCT_LIST();
    }

    @Override
    public void onCallSuccess(Object response) {
        if (response instanceof GetProductModel) {
            view.hideProgress();
            GetProductModel body = ((GetProductModel) response);
            if (body != null) {
                List<GetProductModel.Product> list = body.getData().getProducts();
                constants.setBASE_PRODUCT_LIST(list);
                view.updateList(list);
            } else {
                view.switchView();
            }
        }
    }

    @Override
    public void onCallFail(String message) {
        view.hideProgress();
        view.showErroMessage(message);
    }

    @Override
    public void sessionExpired() {
        view.showErroMessage("Session Expired");

        utils.sessionExpired(context, repo);
    }

    @Override
    public void refreshNetwork() {
        utils.dismissAlert();
        if(isList){
            getList();
        }
    }

    public interface View {

        void showErroMessage(String message);

        void updateList(List<GetProductModel.Product> list);

        void showProgress();

        void hideProgress();

        void switchView();
    }
}
