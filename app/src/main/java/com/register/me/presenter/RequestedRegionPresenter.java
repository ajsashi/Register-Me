package com.register.me.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.ReqGeoRegion;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class RequestedRegionPresenter implements Utils.UtilNetworkInterface, Utils.UtilAlertInterface {
    private Context context;
    private RRListener listener;
    @Inject
    Utils utils;

    @Inject
    MasterNetworkCall masterNetworkCall;
    private Observer<String> message;
    private Observer<ReqGeoRegion> geoObserver;
    private Observer<ResponseData> acceptObserver;
    private int id;
    private Observer<ResponseData> cancelObserver;

    public void init(Context context, RRListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                listener.hideProgress();
                if (utils.isOnline(context)) {
                    listener.showMessage(s);
                }
                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {
                listener.hideProgress();
                listener.showMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.init(context, message, this);
    }


    @Override
    public void refreshNetwork() {
        listener.resume();
    }

    public void getRequestedGeoList() {
        geoObserver = new Observer<ReqGeoRegion>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ReqGeoRegion geographicLocation) {
                final List<ReqGeoRegion.Region> regions = geographicLocation.getData().getRegions();
                if (regions != null) {
                    listener.updateRecyclerView(regions);
                }
                listener.hideProgress();
                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {
                listener.hideProgress();
                listener.showMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        listener.showProgress();
        masterNetworkCall.getReqGeoLocation(geoObserver);
    }

    public void showAlertDialog(String s, int i) {
        this.id = i;
        if (s.equals("accept")) {
            utils.showAlert(context, 20, this);
        } else if (s.equals("reject")) {
            utils.showAlert(context, 21, this);
        } else {
            utils.showAlert(context, 19, this);
        }
    }

    @Override
    public void alertResponse(String success) {
        String response = null;
        if (success.contains("$REJECT")) {
            response = success;
            success = "$REJECT";
        }
        switch (success) {
            case "$ACCEPT":
                acceptRegion(id);
                break;
            case "$REJECT":
                String[] data = response.split(":");
                JsonObject object = new JsonObject();
                object.addProperty("id", id);
                object.addProperty("reason", data[1]);
                rejectRegion(object);
                break;
            default:
                //Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
                break;

        }

    }

    public void acceptRegion(Integer id) {
        listener.showProgress();
        acceptObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                final String message = responseData.getData().getMessage();
                listener.hideProgress();
                listener.showMessage(message != null ? message : responseData.getError());
                masterNetworkCall.clearDisposable();
                getRequestedGeoList();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.acceptReqRegion(String.valueOf(id), acceptObserver);
    }

    public void rejectRegion(JsonObject obj) {
        listener.showProgress();
        cancelObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                final String message = responseData.getData().getMessage();
                listener.hideProgress();
                listener.showMessage(message != null ? message : responseData.getError());
                masterNetworkCall.clearDisposable();
                getRequestedGeoList();
            }

            @Override
            public void onError(Throwable e) {
                listener.hideProgress();
                listener.showMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.cancleReqRegion(obj, cancelObserver);
    }


    public interface RRListener {
        void showProgress();

        void hideProgress();

        void showMessage(String s);

        void updateRecyclerView(List<ReqGeoRegion.Region> locations);

        void resume();
    }
}
