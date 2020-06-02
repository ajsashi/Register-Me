package com.register.me.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.TimeSlotKV;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class AddAvailabilityPresenter implements Utils.UtilNetworkInterface {
    private Context context;
    private AvailabilityListener listener;

    @Inject
    MasterNetworkCall masterNetworkCall;
    private Observer<String> message;
    private Observer<ResponseData> availableObserver;

    public void init(Context context, AvailabilityListener listener) {
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
            }

            @Override
            public void onError(Throwable e) {
                listener.showMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.init(context, message, this);
    }

    public void submitApi(String selectedDate, ArrayList<TimeSlotKV> fromToTime) {

        availableObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                listener.showMessage(responseData.getData().getMessage());
                listener.hideProgress();
            }

            @Override
            public void onError(Throwable e) {
                listener.hideProgress();
                listener.showMessage("availableObserver " + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        StringBuilder from = new StringBuilder();
        StringBuilder to = new StringBuilder();

        final int size = fromToTime.size();
        for (int i = 0; i < size; i++) {
            if (i < size-1) {
                from.append(fromToTime.get(i).getFromTime() + ",");
                to.append(fromToTime.get(i).getToTime() + ",");
            }else if(i==size-1) {
                from.append(fromToTime.get(i).getFromTime() );
                to.append(fromToTime.get(i).getToTime() );
            }
        }
        JsonObject object = new JsonObject();
        object.addProperty("date", selectedDate);
        object.addProperty("fromtime", from.toString());
        object.addProperty("totime", to.toString());
        listener.showProgress();
        masterNetworkCall.addAvailability(object, availableObserver);
    }

    @Override
    public void refreshNetwork() {

    }

    public interface AvailabilityListener {

        void showProgress();
        void hideProgress();
        void showMessage(String s);
    }
}
