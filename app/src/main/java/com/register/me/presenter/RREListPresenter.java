package com.register.me.presenter;

import android.content.Context;

import com.register.me.view.BaseActivity;

public class RREListPresenter {

    private RREListener listener;
    private Context context;

    public void init(Context context, RREListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity)context).injector().inject(this);
    }

    public interface RREListener {

        void showProgress();

        void dismissProgress();

        void showMessage();

    }
}
