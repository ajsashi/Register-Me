package com.register.me.presenter;

import android.content.Context;
import android.util.Patterns;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.RegisterModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 15-02-2020PM 04:15.
 */
public class SignUpPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilNetworkInterface {

    private Context context;
    @Inject
    Constants constants;
    @Inject
    JsonBuilder jsonBuilder;
    @Inject
    Utils utils;
    @Inject
    Retrofit retrofit;
    @Inject
    CacheRepo repo;
    @Inject
    ClientNetworkCall networkCall;
    private ISignUp listener;

    public void init(Context context, ISignUp listener) {
        this.context = context;
        this.listener = listener;

        ((BaseActivity) context).injector().inject(this);
    }

    public void validation(String email, String password, String role) {
        Pattern pattern = Pattern.compile(
                constants.getPasswordPattern());
        Matcher matcher = pattern.matcher(password);
        if (!email.isEmpty() && !password.isEmpty() && !role.isEmpty() && !role.equals("Select Role")) {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                utils.showToastMessage(context,context.getString(R.string.valid_email_alert));
            } else if (!matcher.matches()) {
                utils.showToastMessage(context,context.getString(R.string.password_alert));
            } else {
                JsonObject data = jsonBuilder.getUserSignUpJson(email, password, role);
                apiCall(data);
            }
        } else {
            utils.showToastMessage(context,"Input Field Missing");
        }
    }

    private void apiCall(JsonObject data) {
        if (checkNetStatus()) {
            listener.showProgress();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            networkCall.signUp(apiInterface, data, this);
        }
    }


    public boolean checkNetStatus() {
        if (checkNetwork()) {
            if (!utils.checkAlert()) {
                utils.showNetworkAlert(context, this);
            }
            return false;
        } else {
            if (utils.checkAlert()) {
                utils.dismissAlert();
            }
        }
        return true;
    }

    private boolean checkNetwork() {
        if (!utils.isOnline(context)) {
            return true;
        }
        return false;
    }
    @Override
    public void onCallSuccess(Object response) {
        if(response instanceof RegisterModel){
            listener.dismissProgress();
            RegisterModel.Data body = ((RegisterModel) response).getData();
            boolean status = body.getStatus();
            String message = body.getMessage();
            if (status && message.equals("New user is added successfully.")||status && message.equals("New RRE is registered successfully.")) {
                listener.showAlert();
            }else if(!status && message.equals("Email Address already registered")){
                listener.showErrorMessage(message);
            }
        }
    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();
        listener.showErrorMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.dismissProgress();
        listener.showErrorMessage("Session Expired");

        utils.sessionExpired(context, repo);
    }

    @Override
    public void refreshNetwork() {
        listener.onRefresh();
    }

    public interface ISignUp {
        void showProgress();

        void dismissProgress();

        void showAlert();

        void showErrorMessage(String message);

        void onRefresh();
    }
}
