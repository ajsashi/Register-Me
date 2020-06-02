package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class PortFolioPresenter implements ClientNetworkCall.NetworkCallInterface {
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


    public void init(View view, Context context) {
        this.view = view;
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
    }


    public void getList() {
        if (utils.isOnline(context)) {
            view.showProgress();
            String token = repo.getData(constants.getcacheTokenKey());
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            networkCall.getProductList(apiInterface, token, this);
        } else {
            view.hideProgress();
            view.showErroMessage(context.getResources().getString(R.string.network_alert));
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
        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(), null);
        utils.sessionExpired(context);
    }

    public interface View {

        void showErroMessage(String message);

        void updateList(List<GetProductModel.Product> list);

        void showProgress();

        void hideProgress();

        void switchView();
    }
}
