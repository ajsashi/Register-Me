package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.McrreList;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MasterCRREDetailPresenter implements Utils.UtilNetworkInterface {

    Context context;
    private McrreListener listener;
    @Inject
    MasterNetworkCall masterNetworkCall;
    @Inject
    Utils utils;
    private Observer<String> message;
    private Observer<McrreList> listObserver;

    public void init(Context context, McrreListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                listener.dismissProgress();
                if (utils.isOnline(context)) {
                    listener.showMessage(s);
                }
                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {
                listener.showMessage(e.getMessage());
                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.init(context, message, this);
    }

    @Override
    public void refreshNetwork() {
        listener.onresume();
    }

    public void getMasterCrreList() {
        listObserver = new Observer<McrreList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(McrreList mcrreList) {
                listener.dismissProgress();
                List<McrreList.Mastercrre> list = mcrreList.getData().getMastercrre();
                if (list.size() > 0) {
                    listener.updateAdapter(list);
                }
                masterNetworkCall.clearDisposable();

            }

            @Override
            public void onError(Throwable e) {
                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onComplete() {

            }
        };
        masterNetworkCall.getMcrreList(listObserver);
    }

    public interface McrreListener {

        void showProgress();

        void dismissProgress();

        void updateAdapter(List<McrreList.Mastercrre> list);

        void showMessage(String s);

        void onresume();
    }
}
