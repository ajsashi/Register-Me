package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.model.CertifiedRREList;
import com.register.me.model.data.model.McrreList;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CRREDetailPresenter implements Utils.UtilNetworkInterface {

    Context context;
    private CrreListener listener;
    @Inject
    MasterNetworkCall masterNetworkCall;
    private Observer<String> message;
    private Observer<CertifiedRREList> listObserver;
    @Inject
    Utils utils;

    public void init(Context context, CrreListener listener) {
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

    public void getCrreList() {
        listObserver = new Observer<CertifiedRREList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CertifiedRREList crreList) {
                masterNetworkCall.clearDisposable();
                listener.dismissProgress();
                List<CertifiedRREList.Certifiedrredetail> list = crreList.getData().getCertifiedrredetails();
                if (list.size() > 0) {
                    listener.updateAdapter(list);
                }

            }

            @Override
            public void onError(Throwable e) {
                listener.dismissProgress();
                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onComplete() {

            }
        };
        listener.showProgress();
        masterNetworkCall.getCertifiedRREList(listObserver);
    }

    public interface CrreListener {

        void showProgress();

        void dismissProgress();

        void updateAdapter(List<CertifiedRREList.Certifiedrredetail> list);

        void showMessage(String s);

        void onresume();
    }
}
