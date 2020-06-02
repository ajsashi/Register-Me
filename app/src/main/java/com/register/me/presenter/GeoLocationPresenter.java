package com.register.me.presenter;

import android.content.Context;
import android.widget.Toast;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.GeographicLocation;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class GeoLocationPresenter implements Utils.UtilNetworkInterface, Utils.UtilAlertInterface {
    private Context context;
    private GeoListener listener;
    @Inject
    Utils utils;

    @Inject
    MasterNetworkCall masterNetworkCall;
    private Observer<String> message;
    private Observer<GeographicLocation> geoObserver;

    public void init(Context context, GeoListener listener) {
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

    public void getGeoList() {
        geoObserver = new Observer<GeographicLocation>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GeographicLocation geographicLocation) {
                listener.hideProgress();
                listener.updateRecyclerView(geographicLocation.getData().getLocations());
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
        masterNetworkCall.getGeoLocation(geoObserver);
    }

    public void showAlertDialog() {
        utils.showAlert(context, 19, this);
    }

    @Override
    public void alertResponse(String success) {
        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }


    public interface GeoListener {

        void showProgress();

        void hideProgress();

        void showMessage(String s);

        void updateRecyclerView(List<GeographicLocation.Location> locations);

        void resume();
    }
}
