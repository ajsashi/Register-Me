package com.register.me.presenter;

import android.content.Context;
import android.widget.Toast;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.ScheduleList;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MasterAvailabilityPresenter implements Utils.UtilNetworkInterface,Utils.UtilAlertInterface {
    private Context context;

    Observer<ScheduleList> schList;

    @Inject
    MasterNetworkCall masterNetworkCall;
    @Inject
    Utils utils;
    private MasterAvailabilityListener listener;
    private Observer<ScheduleList> listObserver;
    private Observer<String> message;
    private Observer<ResponseData> approveObserver;
    private Observer<ResponseData> cancelObserver;
    private Integer cancelId;


    public void init(Context context, MasterAvailabilityListener listener) {
        ((BaseActivity) context).injector().inject(this);
        this.context = context;
        this.listener = listener;
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                masterNetworkCall.clearDisposable();
                listener.hideProgress();
                if (utils.isOnline(context)) {
                listener.showMessage(s); }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.init(context, message, this);
    }

    public void getInterviewList() {
        listener.showProgress();
        listObserver = new Observer<ScheduleList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ScheduleList scheduleList) {
                listener.updateScheduleList(scheduleList.getData().getSchedules());
                listener.hideProgress();
                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {
                listener.showMessage(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        masterNetworkCall.getInterviewList(listObserver);
    }

    @Override
    public void refreshNetwork() {
        listener.hideProgress();
        masterNetworkCall.checkNetStatus();
    }

    public void approveSchedule(Integer id) {
        approveObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                final String message = responseData.getData().getMessage();
                if (message !=null) {
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }
                getInterviewList();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.approveSchedule(String.valueOf(id),approveObserver);
    }

    public void cancelSchedule(Integer id) {
        cancelId = id;
        utils.showAlert(context,18,this);

    }

    @Override
    public void alertResponse(String message) {
        listener.showProgress();
        cancelObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                listener.showMessage(responseData.getData().getMessage());
                getInterviewList();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.cancelSchedule(String.valueOf(cancelId),cancelObserver);
    }

    public interface MasterAvailabilityListener {

        void updateScheduleList(List<ScheduleList.Schedule> schedules);

        void showMessage(String s);

        void showProgress();

        void hideProgress();
    }
}
