package com.register.me.presenter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.ChangePasswordModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 17-02-2020PM 03:35.
 */
public class ChangePasswordPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilAlertInterface {

    private Context context;

    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;
    private IChangePassword listener;
    private Resources resources;
    public void init(Context context, IChangePassword listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        resources = Objects.requireNonNull(context.getResources());
    }


    public String validate(String ctPass, String newPass, String conPass) {
        Pattern pattern = Pattern.compile(
                constants.getPasswordPattern());

        if (!ctPass.isEmpty() && !newPass.isEmpty() && !conPass.isEmpty()) {
            Matcher newMatcher = pattern.matcher(newPass);
            if (ctPass.equals(newPass)) {
//                listener.showErrorMessage(context.getResources().getString(R.string.same_password_alert));
                return context.getResources().getString(R.string.same_password_alert);
            }
            else if(!newPass.equals(conPass)){
                return "Password Mismatch";
            }
            else if(!newMatcher.matches())
            {
//                listener.showErrorMessage(context.getResources().getString(R.string.password_alert));
                return context.getResources().getString(R.string.password_alert);
            } else {
                apicall(ctPass, newPass);
            }
        } else {

//            listener.showErrorMessage("All fields are mandatory");
            return "All fields are mandatory";
        }
        return "";
    }

    private void apicall(String ctPass, String newPass) {
        if (utils.isOnline(context)) {
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            networkCall.changePassword(apiInterface, token, ctPass, newPass, this);
        } else {
            listener.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("$Success$"))
            listener.popUp();
    }

    public boolean validate(ImageView checkIcon) {
        return checkIcon.getVisibility() == View.VISIBLE && checkIcon.getDrawable().getConstantState()
                == (resources.getDrawable(R.drawable.checked, null).getConstantState());
    }

    @Override
    public void onCallSuccess(Object response) {
        if(response instanceof ChangePasswordModel){
            ChangePasswordModel body = ((ChangePasswordModel) response);
            int statuscode = body.getStatusCode();
            String message = body.getData().getMessage();
            boolean status = body.getData().getStatus();
            if (statuscode == 200) {
                if (status) {
                    utils.showAlert(context, 4, this);
                } else {
                    listener.showErrorMessage(message);
                }
            }
        }
    }

    @Override
    public void onCallFail(String message) {
        listener.showErrorMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.showErrorMessage("Session Expired");

        utils.sessionExpired(context, repo);
    }


    public interface IChangePassword{
        void showErrorMessage(String message);

        void popUp();
        void showProgress();
        void hideProgress();


    }
}
