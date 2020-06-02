package com.register.me.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.ProjectModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 24-02-2020PM 06:57.
 */
public class CountryPresenter implements Utils.UtilAlertInterface, ClientNetworkCall.NetworkCallInterface {

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
    private ICountryListener listener;

    public void init(Context context, ICountryListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
    }

    public String getNumber() {
        return constants.getSelectedList().getProduct().getProductNumber();
    }

    public String getName() {
        return constants.getSelectedList().getProduct().getProductName();
    }

/*
    public List<GetProductModel.Project> getCountryList() {
        return constants.getSelectedList().getProject();
    }*/

    public void setProject(String id) {
        constants.setProjectID(id);

    }

    public void setViewScreen() {
        constants.setviewScreenFrom(2);
    }

    public void directAssign() {
        utils.showAlert(context, 7, this);
    }

    @Override
    public void alertResponse(String msg) {
        String reason = "";
        if (msg.contains("$SUCCESS$")) {
            reason = msg.replace("$SUCCESS$", "");
            msg = "$SUCCESS$";
        }
        switch (msg) {
            case "$OK$":

                apiCall(1, null);
                break;
            case "$SUCCESS$":
                apiCall(2, reason);
                break;
            case "$ALERT$":
                listener.showMessage("Please enter the reason for cancellation");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + msg);
        }

    }

    private void apiCall(int i, String reason) {
        if (utils.isOnline(context)) {
            listener.showProgress();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            String id = constants.getProjectID();
            JsonObject object = new JsonObject();
            object.addProperty("projectid", id);
            switch (i) {
                case 1:
                    networkCall.directAssignment(apiInterface, token, object, this);
                    break;
                case 2:
                    object.addProperty("cancelreason", reason);
                    networkCall.cancelProject(apiInterface, token, object, this);
                    break;
                case 3:
                    String productID = constants.getProductID();
                    networkCall.getProjectList(apiInterface, token, productID, this);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i);
            }
        } else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    public void cancelProject() {
        utils.showAlert(context, 8, this);
    }

    public void triggerApi() {
        apiCall(3, null);
    }

    @Override
    public void onCallSuccess(Object response) {
        listener.dismissProgress();
        if (response instanceof ProjectModel) {
            ProjectModel resBody = (ProjectModel) response;
            List<ProjectModel.Project> projects = resBody.getData().getProjects();
            listener.updateUI(projects);
        } else if (response instanceof AddProductModel) {
//            listener.navigate();
            triggerApi();
        } else if (response instanceof ResponseBody) {
            triggerApi();
        }

    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();

        listener.showMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.dismissProgress();
        listener.showMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(),null);
        utils.sessionExpired(context);
    }


    public interface ICountryListener {

        void showMessage(String string);

        void navigate();

        void updateUI(List<ProjectModel.Project> projects);

        void showProgress();

        void dismissProgress();
    }
}
