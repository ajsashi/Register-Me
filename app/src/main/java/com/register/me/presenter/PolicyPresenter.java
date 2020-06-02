package com.register.me.presenter;

import android.content.Context;

import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

public class PolicyPresenter implements Utils.UtilAlertInterface {
    Context context;

    @Inject
    Utils utils;
    private IPolicy listener;

    public void init(Context context,IPolicy listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity)context).injector().inject(this);
    }

    public void showAlert() {
        utils.showAlert(context,11,this);
    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("$ALERT$")) {
            listener.showMessage("Please fill the comment field");
        }else {
            listener.triggerApi(success);
        }
    }

    public interface IPolicy{
        void showMessage(String message);

        void triggerApi(String success);

    }
}
