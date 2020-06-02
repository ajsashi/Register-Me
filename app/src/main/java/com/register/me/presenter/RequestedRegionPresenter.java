package com.register.me.presenter;

import android.content.Context;
import android.widget.Toast;

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
                listener.showMessage(s);
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
                listener.updateRecyclerView(geographicLocation.getData().getRegions());
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

    public void showAlertDialog() {
        utils.showAlert(context,19,this);
    }

    @Override
    public void alertResponse(String success) {
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }

    public void acceptRegion(Integer id) {
        acceptObserver= new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                final String message = responseData.getData().getMessage();
                listener.showMessage(message !=null? message:responseData.getError());
                getRequestedGeoList();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.acceptReqRegion(String.valueOf(id),acceptObserver);
    }

    public void rejectRegion(Integer id) {
    }


    public interface RRListener {
        void showProgress();

        void hideProgress();

        void showMessage(String s);

        void updateRecyclerView(List<ReqGeoRegion.Region> locations);

        void resume();
    }
}
