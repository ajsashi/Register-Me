package com.register.me.presenter;

import android.content.Context;
import android.util.Patterns;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.HashMap;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 11:01.
 */
public class LoginPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilAlertInterface {

    private Context context;
    private ILogin loginListener;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    private ApiInterface apiInterface;

    public void init(Context context, ILogin loginListener) {
        this.context = context;
        this.loginListener = loginListener;
        ((BaseActivity) context).injector().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public boolean isLoggedIn() {
        String status = repo.getData(constants.getcacheIsLoggedKey());
        String userRole = repo.getData(constants.getcacheRoleKey());
        if(userRole !=null){
            switch (userRole){
                case "Client":
                    constants.setuserRole(0);
                    break;
                case "RRE":
                    constants.setuserRole(1);
                    break;
                case "CRRE":
                    constants.setuserRole(2);
                    break;
                case "MASTERCRRE":
                    constants.setuserRole(3);
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + userRole);
            }
        }

        if (status != null && status.equals("true")) {
            return true;
        }
        return false;
    }


    public void validation(String email, String password) {
        boolean valid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (email.isEmpty()) {
            loginListener.showErroMessage(context.getResources().getString(R.string.empty_email_alert));

        } else if (password.isEmpty()) {
            loginListener.showErroMessage(context.getResources().getString(R.string.empty_password_alert));
        } else if (valid) {

            apiCall(email, password);
        } else {
            loginListener.showErroMessage(context.getResources().getString(R.string.valid_email_alert));
        }

    }

    private void apiCall(String email, String password) {

        if (utils.isOnline(context)) {
            loginListener.showProgress();
            networkCall.login(apiInterface, email, password, "password", this);
        } else {
            loginListener.showErroMessage(context.getResources().getString(R.string.network_alert));
        }

    }

    public void forgotPassword() {
        utils.showAlert(context, 2, this);
    }


    private void extractSuccessResponse(LoginModel body) {
        String userName = body.getUserName();
        String userRole = body.getRoles();
        String token = body.getToken();
        String tokenType = body.getTokenType();
        HashMap<String, String> map = new HashMap<>();
        map.put(constants.getcacheIsLoggedKey(), "true");
        map.put(constants.getcacheUsernameKey(), userName);
        map.put(constants.getcacheRoleKey(), userRole);
        map.put(constants.getcacheTokenKey(), token);
        map.put(constants.getcacheTokenTypeKey(), tokenType);
        repo.storeBulkData(map);
        if(userRole !=null) {
            switch (userRole) {
                case "Client":
                    constants.setuserRole(0);
                    break;
                case "RRE":
                    constants.setuserRole(1);
                    break;
                case "CRRE":
                    constants.setuserRole(2);
                    break;
                case "MASTERCRRE":
                    constants.setuserRole(3);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + userRole);
            }
        }
        loginListener.navigate();
    }

    @Override
    public void alertResponse(String success) {
        String value = "";
        if (success.contains("$EMAIL$")) {
            value = success.replace("$EMAIL$", "");
            success = "$EMAIL$";
        } else if (success.contains("$ERROR$")) {
            value = success.replace("$ERROR$", "");
            success = "$ERROR$";
        }
        switch (success) {
            case "$EMAIL$":
                if (utils.isOnline(context)) {
                    loginListener.showProgress();
                    loginListener.clearFields();
                    networkCall.forgotPassword(apiInterface, value, this);
                } else {
                    loginListener.showErroMessage(context.getResources().getString(R.string.network_alert));
                }
                break;
            case "$ERROR$":
                loginListener.showErroMessage(value);
                break;

        }
    }

    @Override
    public void onCallSuccess(Object response) {
        loginListener.dismissProgress();
        if(response instanceof LoginModel){
            LoginModel body = (LoginModel) response;
            extractSuccessResponse(body);
        }else if( response instanceof ResponseBody){
            utils.showAlert(context, 3, this);
        }
    }

    @Override
    public void onCallFail(String message) {
        loginListener.dismissProgress();
        loginListener.showErroMessage(message);
    }

    @Override
    public void sessionExpired() {
        loginListener.dismissProgress();
        loginListener.showErroMessage("Session Expired");

        utils.sessionExpired(context, repo);
    }

    public String getRole() {
        return repo.getData(constants.getcacheRoleKey());
    }


    public interface ILogin {
        void showErroMessage(String message);

        void showProgress();

        void dismissProgress();

        void navigate();

        void clearFields();
    }
}
