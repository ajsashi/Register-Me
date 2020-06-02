package com.register.me.presenter;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.CrreList;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.LocationModel;
import com.register.me.model.data.model.RequestRegion;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class InitiateRegistrationPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilAlertInterface {

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
    @Inject
    JsonBuilder builder;

    private Context context;
    private InitiateRegListener listener;
    private ApiInterface apiInterface;
    private String token;


    public void init(Context context, InitiateRegListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
        token = repo.getData(constants.getcacheTokenKey());
        getLocationList();
    }


    public void getLocationList() {
        apiCall(null,1);
    }

    public String geName() {
        return constants.getSelectedList().getProduct().getProductName();
    }

    public String getProductID() {
        return String.valueOf(constants.getSelectedList().getProductId());
    }

    public void triggerApi(ArrayList<KeyValue> map) {
        JsonObject obj = builder.initiateBidding(map);
        apiCall(obj,2);
    }

    private void apiCall(JsonObject obj, int callCase) {
        if (utils.isOnline(context)) {
            switch (callCase){
                case 1:
                    networkCall.getLocationList(apiInterface, token, this);
                    break;
                case 2:
                    networkCall.initBid(apiInterface, token, obj, this);
                    break;
                case 3:
                    networkCall.requestRegion(apiInterface, token, obj, this);
                    break;
                case 4:
                    networkCall.getCrreList(apiInterface,token,obj,this);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + callCase);
            }

        } else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    public void requestRegion() {
        utils.showAlert(context,9,this);
    }

    @Override
    public void alertResponse(String success) {
        String[] data = success.split(":");
        Toast.makeText(context, "region - "+data[0]+"\n description - "+data[1], Toast.LENGTH_SHORT).show();
        JsonObject object = new JsonObject();
        object.addProperty("regionname",data[0]);
        object.addProperty("description",data[1]);
        apiCall(object, 3);
    }

    @Override
    public void onCallSuccess(Object response) {
        int typeCase = getResponseType(response);
        switch (typeCase){
            case 1:
                RequestRegion resBody = (RequestRegion) response;
                String message = resBody.getData().getMessage();
                listener.showMessage(message);
                break;
            case 2:
                List<LocationModel.Location> list = ((LocationModel) response).getData().getLocations();
                ArrayList<Integer> locationId = new ArrayList<>();
                ArrayList<String> locationList = new ArrayList<>();
                locationId.add(-1);
                locationList.add("Select Region");
                for (LocationModel.Location item : list) {
                    locationId.add(item.getId());
                    locationList.add(item.getRegion());
                }
                listener.updateSpinner(locationId, locationList);
                break;
            case 3:
                listener.showMessage("Bid Submitted successfully");
                listener.navigate();
                break;
            case 4:
                CrreList crreList = (CrreList)response;
                List<CrreList.Expertlist> cList = crreList.getData().getExpertlist();
                listener.updateCrreSpinner(cList);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeCase);
        }
    }

    private int getResponseType(Object response) {
        if(response instanceof RequestRegion){
            return 1;
        }else if(response instanceof LocationModel){
            return 2;
        }else if(response instanceof AddProductModel){
            return 3;
        }else if(response instanceof CrreList){
            return 4;
        }
        return -1;
    }

    @Override
    public void onCallFail(String message) {
        if(message.equals("$NOCONTENT$")){
            listener.setMasterAssignment();
        }else {
        listener.showMessage(message);}

    }

    @Override
    public void sessionExpired() {
        listener.showMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(),"false");
        repo.storeData(constants.getCACHE_USER_INFO(),null);

        utils.sessionExpired(context);
    }

    public void getCrreList(String location_id) {
        JsonObject object = new JsonObject();
        object.addProperty("locationid",location_id);
        apiCall(object,4);

    }


    public interface InitiateRegListener {

        void showMessage(String msg);

        void updateSpinner(ArrayList<Integer> locationId, ArrayList<String> locationList);

        void navigate();

        void showProgress();

        void dismissProgress();

        void updateCrreSpinner(List<CrreList.Expertlist> cList);

        void setMasterAssignment();
    }
}
