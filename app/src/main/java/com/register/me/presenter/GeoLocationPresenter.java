package com.register.me.presenter;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.GeographicLocation;
import com.register.me.model.data.model.ResponseData;
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
    private Integer ID;
    private Observer<ResponseData> editObserver;
    private String region;
    private Observer<ResponseData> removeObserver;
    private boolean isAlertShowing;

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
                masterNetworkCall.clearDisposable();
                listener.hideProgress();
                if (utils.isOnline(context)) {
                    listener.showMessage(s);
                }
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
                masterNetworkCall.clearDisposable();
                listener.hideProgress();
                final List<GeographicLocation.Location> locations = geographicLocation.getData().getLocations();
                if (locations != null) {
                    listener.updateRecyclerView(locations);
                }
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

    public void showAlertDialog(String s) {
        if(!isAlertShowing) {
            isAlertShowing = true;
            switch (s) {
                case "Edit":
                    if (getRegion() != null || getRegion().isEmpty()) {
                        utils.setEditRegion(getRegion());
                        utils.showAlert(context, 22, this);
                    } else {
                        listener.showMessage("Edit region is empty");
                    }
                    break;
                case "Cancel":
                    utils.showAlert(context, 23, this);
                    break;
                case "Add":
                    utils.showAlert(context, 19, this);
                    break;

            }
        }

    }

    @Override
    public void alertResponse(String alert) {
        isAlertShowing= false;
        String response = "";
        if (alert.contains("$EDIT:")) {
            response = alert;
            alert = "$EDIT:";
        }else if (alert.contains("$ADD$")){
            response =alert.replace("$ADD$","");
            alert="$ADD";
        }
        switch (alert) {
            case "$EDIT:":
                editRegion(response);
                break;
            case "$DELETE":
                removeRegion();
                break;

            case "$ADD":
                Observer<ResponseData> locObserver = new Observer<ResponseData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseData responseData) {
                        masterNetworkCall.clearDisposable();
                        String data = responseData.getData().getMessage();
                        if (data != null && !data.isEmpty()) {
                            Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                            getGeoList();
                        } else {
                            if (responseData.getError() != null) {
                                Toast.makeText(context, responseData.getError(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
                masterNetworkCall.addNewGeo(response, locObserver);
                break;
            case "$ALERT$":
                utils.showToastMessage(context,"Please Enter Geographic Location");
            case "$Dismiss$":
                break;
        }
//        Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
    }

    private void editRegion(String response) {
        JsonObject object = new JsonObject();
        object.addProperty("id", ID);
        final String region = response.split(":")[1];
        object.addProperty("Region", region);
        editObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                masterNetworkCall.clearDisposable();
                listener.hideProgress();
                String message = responseData.getData().getMessage();
                if (message != null) {
                    listener.showMessage(message);
                }
                getGeoList();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        listener.showProgress();
        masterNetworkCall.editRegion(object, editObserver);
    }

    private void removeRegion() {
        removeObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                masterNetworkCall.clearDisposable();
                listener.hideProgress();
                String msg = responseData.getData().getMessage();
                if (msg != null) {
                    listener.showMessage(msg);
                } else {
                    listener.showMessage(responseData.getError().toString());
                }
            }

            @Override
            public void onError(Throwable e) {
                listener.showMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        listener.showProgress();
        masterNetworkCall.removeRegion(String.valueOf(ID), removeObserver);
    }

    public void setId(Integer id) {
        this.ID = id;
    }

    public void setRegion(String geographiclocation) {
        region = geographiclocation;
    }

    public String getRegion() {
        return region;
    }

    public interface GeoListener {

        void showProgress();

        void hideProgress();

        void showMessage(String s);

        void updateRecyclerView(List<GeographicLocation.Location> locations);

        void resume();
    }
}
