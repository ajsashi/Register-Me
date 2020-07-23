package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AuctionWon;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class AuctionsWonPresenter implements Utils.UtilNetworkInterface {
    private Observer<String> message;
    Context context;
    AuctionsWonListener listener;
    @Inject
    MasterNetworkCall networkCall;
    @Inject
    Utils utils;
    @Inject
    Constants constants;

    public void init(Context context, AuctionsWonListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                utils.showToastMessage(context, s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        networkCall.init(context, message, this);
    }

    public void getAuctionsList() {
        Observer<AuctionWon> auctionObserver = new Observer<AuctionWon>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(AuctionWon auctionWon) {
                final AuctionWon.Data data = auctionWon.getData();
                if (data != null) {
                    if (data.getAuctionswon() != null) {
                        listener.updateList(data.getAuctionswon());
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
        networkCall.getAutionList(auctionObserver);
    }

    @Override
    public void refreshNetwork() {
listener.onResume();
    }

    public void setProductId(Integer productId) {
        constants.setviewScreenFrom(5);
        constants.setProductID(String.valueOf(productId));
        constants.setProjectID(null);

    }

    public interface AuctionsWonListener {
        void showProgress();

        void hideProgress();

        void updateList(List<AuctionWon.AuctionsWonData> auctionswon);

        void onResume();
    }
}
