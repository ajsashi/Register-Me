package com.register.me.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.model.UpdateProfileModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 18-02-2020PM 04:45.
 */
public class PersonalInfoPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilAlertInterface, Utils.UtilNetworkInterface {

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
    @Inject
    JsonBuilder builder;
    private Context context;
    private IPersonalInfo listener;
    private boolean OrgHasData;
    private boolean isEmail;
    private boolean isSMS;

    public void init(Context context, IPersonalInfo listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
    }

    public int getRole() {
        return constants.getuserRole();
    }

    public boolean hasORG() {
        return OrgHasData;
    }

    public boolean isEmail() {
        return isEmail;
    }

    public boolean isSMS() {
        return isSMS;
    }

    public void getUserDetails() {
       /* String data = repo.getData(constants.getCACHE_USER_INFO());
        if(data!=null&&!force){
            GetUserInfoModel body = new Gson().fromJson(data, GetUserInfoModel.class);
            onUserDataFetched(body);
            return;
        }*/
        if (utils.isOnline(context)) {
            listener.showProgress();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());

            networkCall.getUserDetails(apiInterface, token, this);
        } else {
            //listener.showErrorMessage(context.getResources().getString(R.string.network_alert));
            utils.showNetworkAlert(context, this);
        }
    }

    public void validate(ArrayList<QandA> info) {
        boolean isTelephone = false;
        boolean isCellphone = false;

        for (QandA item : info) {
            final String quest = item.getQuestion().toLowerCase();
            if (item.getAnswer() == null || item.getAnswer().isEmpty()) {
                if(!quest.equals("telephone")&&!quest.equals("cell phone")){
                    listener.showErrorMessage("Please enter " + item.getQuestion());
                    return;
                }

            }
            else if (item.getQuestion().equals("Notification") && getRole() == 0 && item.getAnswer().equals("false")) {
                listener.showErrorMessage("Please enable " + item.getQuestion());
                return;
            }else {
                if(quest.equals("telephone")){
                    isTelephone=true;
                    break;
                }else if(quest.equals("cell phone")){
                    isCellphone=true;
                    break;
                }
            }

        }
        if(isTelephone||isCellphone){
            updateUser(info);
        }else {
            listener.showErrorMessage("Either telephone or cell phone is mandatory");

        }
    }


    /*if(item.getQuestion().toLowerCase().equals("telephone")){
                if (item.getAnswer()== null||item.getAnswer().isEmpty()) {
                    eitherOption =false;
                }else {
                    eitherOption= true;
                }
            }else if(item.getQuestion().toLowerCase().equals("cell phone")){
                if ((item.getAnswer()== null||item.getAnswer().isEmpty())&&!eitherOption) {
                    listener.showErrorMessage("Either Telephone or Cell phone is mandatory");
                }
            }*/
    private void updateUser(ArrayList<QandA> info) {
        JsonObject data = builder.getUserUpdateJson(info);
        Log.d("data", data.toString());
        apiCall(data);
    }

    public void checkNetStatus() {
        if (checkNetwork()) {
            if (!utils.checkAlert()) {
                utils.showNetworkAlert(context, this);
            }
        } else {
            if (utils.checkAlert()) {
                utils.dismissAlert();
            }
        }
    }

    private boolean checkNetwork() {
        if (!utils.isOnline(context)) {
            return true;
        }
        return false;
    }

    private void apiCall(JsonObject data) {
        if (utils.isOnline(context)) {
            listener.showProgress();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            networkCall.updateUserInformation(apiInterface, token, data, this);
        } else {
            utils.showNetworkAlert(context, this);
            // listener.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }


    @Override
    public void alertResponse(String success) {
        if (getRole() == 0) {
            listener.exitScreen();
        }else if(success.contains("$EMAIL")){
            Toast.makeText(context, success.replace("$EMAIL:",""), Toast.LENGTH_SHORT).show();
        }
    }

    public int getInputType(int inputType) {
        return utils.getInputType(inputType);
    }

    @Override
    public void onCallSuccess(Object response) {
        listener.dismissProgress();
        if (response instanceof UpdateProfileModel) {
            utils.showAlert(context, 6, this);
        } else if (response instanceof GetUserInfoModel) {
            GetUserInfoModel body = ((GetUserInfoModel) response);
            GetUserInfoModel.User user = body.getData().getUser();
            ArrayList<QandA> personalInfo = new ArrayList<QandA>();
            ArrayList<QandA> countryInfo = new ArrayList<QandA>();
            ArrayList<QandA> notificaionInfo = new ArrayList<QandA>();
            repo.storeData(constants.getCACHE_USER_INFO(), new Gson().toJson(body));

            /*
             * 1 - text
             * 2 - email
             * 3 - password
             * 4 - number
             * */
            /*
             * 1 - action_next;
             * 2 - action_done
             * 2 - password
             * */

            personalInfo.add(new QandA("First Name", user.getFirstname() == null ? "" : user.getFirstname(), 1, 1, 1, "firstname", null, null));
            personalInfo.add(new QandA("Last Name", user.getLastName() == null ? "" : user.getLastName(), 1, 1, 1, "lastname", null, null));
            personalInfo.add(new QandA("Email", user.getEmailAddress() == null ? "" : user.getEmailAddress(), 1, 2, 1, "email", null, null));
            personalInfo.add(new QandA("Telephone", user.getTelephone() == null ? "" : user.getTelephone(), 1, 4, 1, "telephone", null, null));
            personalInfo.add(new QandA("Cell Phone", user.getCellPhone() == null || user.getCellPhone().equals("null") ? "" : user.getCellPhone(), 1, 4, 2, "cellphone", null, null));

            countryInfo.add(new QandA("Company Name", user.getOrganization() == null ? "" : user.getOrganization().getCompanyName(), 1, 1, 1, "companyname", null, null));
            countryInfo.add(new QandA("Division", user.getOrganization() == null ? "" : user.getOrganization().getDivision(), 1, 1, 1, "division", null, null));
            countryInfo.add(new QandA("Address 1", user.getOrganization() == null ? "" : user.getOrganization().getAddress(), 1, 1, 1, "address1", null, null));
            countryInfo.add(new QandA("Address 2", user.getOrganization() == null ? "" : user.getOrganization().getAddress2(), 1, 1, 1, "address2", null, null));
            countryInfo.add(new QandA("City", user.getOrganization() == null ? "" : user.getOrganization().getCity(), 1, 1, 1, "city", null, null));
            countryInfo.add(new QandA("State", user.getOrganization() == null ? "" : user.getOrganization().getState(), 1, 1, 1, "state", null, null));
            countryInfo.add(new QandA("Postal Code", user.getOrganization() == null ? "" : user.getOrganization().getPostalCode(), 1, 1, 1, "postalcode", null, null));
            countryInfo.add(new QandA("Country", user.getOrganization() == null ? "" : user.getOrganization().getCountry(), 1, 1, 1, "country", null, null));

            if (user.getEmailNotification() != null) {
                isEmail = user.getEmailNotification();
            }
            if (user.getSmsNotification() != null) {
                isSMS = user.getSmsNotification();
            }
//            notificaionInfo.add(new QandA("Notification", user.getOrganization() == null ? "" : String.valueOf(user.getEmailNotification()), 2, 0, 0, "emailNotification", null, null));

            if (user.getOrganization() == null) {
                OrgHasData = false;
            } else {
                OrgHasData = true;
            }
            listener.updateUI(personalInfo, countryInfo);
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
        utils.dismissAlert();
        listener.refresh();
    }

    public void showAlert() {
        utils.showAlert(context,25,this);
    }


    public interface IPersonalInfo {

        void showErrorMessage(String mesage);

        void updateUI(ArrayList<QandA> info, ArrayList<QandA> subinfo);

        void errorFetchingData(String s);

        void exitScreen();

        void dismissProgress();

        void showProgress();

        void refresh();
    }
}
