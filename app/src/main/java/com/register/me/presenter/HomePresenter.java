package com.register.me.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import retrofit2.Retrofit;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class HomePresenter implements Utils.UtilAlertInterface, ClientNetworkCall.NetworkCallInterface {

    private final int PICK_FROM_GALLERY = 100;
    private View view;
    @Inject
    Constants constants;
    private Context context;
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
    private ApiInterface apiInterface;

    public HomePresenter() {

    }

    public void init(Context context) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
        int role = constants.getuserRole();
        int tab = constants.getTAB();
        switch (role) {
            case 0:
                switch (tab) {
                    case 1:
                        constants.setSelectedList(null);
                        view.showNewProject();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        view.showClientDashBoard();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + tab);
                }
                break;
            case 1:
                switch (tab) {
                    case 1:
                        view.showRREDashBoard();
                        break;
                    case 2:
                        view.showOnlineInter();
                        break;
                    case 3:
                        view.showPolicyTraining();
                        break;
                    case 4:
                        view.showCertification();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + tab);
                }
                break;
            case 2:
                switch (tab) {
                    case 1:
                        constants.setActiveAuction(true);
                        view.ActiveAuctions();
                        break;
                    case 2:
                        constants.setActiveAuction(false);
                        view.ActiveAuctions();
                        break;
                    case 3:
                        constants.setAcitiveProject(true);
                        view.ActiveProjects();
                        break;
                    case 4:
                        constants.setAcitiveProject(false);
                        view.CompletedProjects();
                        break;
                    case 5:
                        constants.setStory(true);
                        constants.setCertificate(false);
                        constants.setLibrary(false);
                        view.showStory();
                        break;
                    case 6:
                        constants.setStory(false);
                        constants.setCertificate(true);
                        constants.setLibrary(false);
                        view.showStory();
                        break;
                    case 7:
                        constants.setStory(false);
                        constants.setCertificate(false);
                        constants.setLibrary(true);
                        view.showStory();
                        Toast.makeText(context, "7", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + tab);
                }
                break;
            case 3:
                switch (tab){
                    case 1:
                        constants.setMasterScreen(1);
                        view.showClientDetails();
                        break;
                    case 2:
                        constants.setMasterScreen(2);
                        view.showRREDetails();
                        break;
                    case 3:
                        constants.setMasterScreen(3);
                        break;
                    case 4:
                        constants.setMasterScreen(4);
                        break;
                    case 5:
                        view.showMasterAvailability();
                        break;
                    case 6:
                        view.showGeoLocation();
                        break;
                    case 7:
                        view.showRequestedRegion();
                        break;
                    case 8:
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + tab);
                }
                Toast.makeText(context, tab+"", Toast.LENGTH_SHORT).show();
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + role);
        }
    }

    public void setView(View view) {
        this.view = view;
    }

    public void logout() {
        utils.showAlert(context, 5, this);
    }


    @Override
    public void alertResponse(String status) {
        if (status.equals("$LOGOUT")) {
            if (utils.isOnline(context)) {
                String token = repo.getData(constants.getcacheTokenKey());
                repo.storeData(constants.getcacheIsLoggedKey(), "false");
                repo.storeData(constants.getCACHE_USER_INFO(), null);
                networkCall.logout(apiInterface, token, this);
            }

        } else {
            view.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }


    public void pickImage() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            ((Activity) context).startActivityForResult(intent, PICK_FROM_GALLERY);
        }
    }

    public void apiUpdateAvatar(Bitmap bitmap) {
        if (utils.isOnline(context)) {
            /*  Resize Actual Image   */
            Bitmap compressedBmp = getResizedBitmap(bitmap, 500);

            /*   Convert bitmap  to base 64 */
            String encoded = getEncodedString(compressedBmp);

            String token = repo.getData(constants.getcacheTokenKey());
            JsonObject jObj = builder.getAvatarJson(encoded);
            networkCall.updateAvatar(apiInterface, token, jObj, this);
            compressedBmp.recycle();
        } else {
            view.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    private String getEncodedString(Bitmap compressedBmp) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compressedBmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }


    public String getProfileImage() {
        String data = repo.getData(constants.getCACHE_USER_INFO());
        GetUserInfoModel jS = new Gson().fromJson(data, GetUserInfoModel.class);
        return jS.getData().getUser().getImageUrl();
    }

    public String getUserName() {
        String data = repo.getData(constants.getCACHE_USER_INFO());
        GetUserInfoModel jS = new Gson().fromJson(data, GetUserInfoModel.class);


        return jS.getData().getUser().getFirstname() + " " + jS.getData().getUser().getLastName();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onCallSuccess(Object response) {
        if (response instanceof LogoutModel) {
            LogoutModel body = ((LogoutModel) response);
            Integer statusCode = body.getStatusCode();
            if (statusCode != null && statusCode == 200) {
                sessionExpired();
            }
        } else if (response instanceof AvatarModel) {
            String url = ((AvatarModel) response).getUrl();
            if (url != null) {
                repo.storeData(constants.getcacheUserProfileUrlKey(), url);
                view.updateProfileImage(url);
            }
        }
    }

    @Override
    public void onCallFail(String message) {
        if (message.contains("$LOGOUT$")) {
            sessionExpired();
        } else if (message.contains("$PROFILE$UPLOAD$FAILED$")) {
            view.showErrorMessage(context.getResources().getString(R.string.avatar_update_failed));

        }
    }

    @Override
    public void sessionExpired() {
        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(), null);
        utils.sessionExpired(context);
    }


    public interface View {

        void showClientDashBoard();

        void showNewProject();

        void showRREDashBoard();

        void showOnlineInter();

        void showErrorMessage(String message);

        void updateProfileImage(String url);

        void showPolicyTraining();

        void showCertification();

        void ActiveAuctions();

        void showStory();

        void ActiveProjects();

        void CompletedProjects();

        void showClientDetails();

        void showRREDetails();

        void showMasterAvailability();

        void showGeoLocation();

        void showRequestedRegion();
    }
}

