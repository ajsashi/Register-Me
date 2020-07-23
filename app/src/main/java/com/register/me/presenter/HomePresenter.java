package com.register.me.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;

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

public class HomePresenter implements Utils.UtilAlertInterface, ClientNetworkCall.NetworkCallInterface, Utils.UtilNetworkInterface {

    private final int PICK_FROM_GALLERY = 100;
    private View view;
    @Inject
    Constants constants;
    private Context context;
    private static int selectedTab = -1;
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

    //    init function defines the home screen display and click events based on client, rre, crre, and mcrre role
    public void init(Context context) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
        int role = constants.getuserRole();
        int tab = constants.getTAB();

        switch (role) {
            /*Client screen*/
            /*Switch case are the tab number in Client Home Screen*/
            case 0:
                switch (tab) {
                    /*Redirects to Tab New Product*/
                    case 1:
                        constants.setSelectedList(null);
                        view.showNewProject();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    /*Redirects to Tab Dashboard*/
                    case 4:
                        view.showClientDashBoard();
                        break;
                    default:
                        //resetData();
                }

                break;
            /*RRE screen*/
            /*Switch case are the tab number in RRE Home Screen*/
            case 1:
                switch (tab) {
                    /*Redirects to Tab Application Submission*/
                    case 1:
                        view.showRREDashBoard();
                        break;
                    case 2:
                        /*Redirects to Tab Online InterView */
                        view.showOnlineInter();
                        break;
                    case 3:
                        /*Redirects to Tab Policy Training*/
                        view.showPolicyTraining();
                        break;
                    case 4:
                        /*Redirects to Tab Certificate*/
                        view.showCertification();
                        break;
                    default:
                        resetData();
                }
                break;
            /*CRRE Screen*/
            /*Switch case are the tab number in CRRE Home Screen*/
            case 2:
                switch (tab) {
                    /*Redirects to Tab My Active Auction*/
                    case 1:
                        constants.setActiveAuction(true);
                        view.ActiveAuctions();
                        break;
                    /*Redirects to Tab Auction InProgress*/
                    case 2:
                        constants.setActiveAuction(false);
                        view.ActiveAuctions();
                        break;
                    /*Redirects to Tab Active Project*/
                    case 3:
                        constants.setAcitiveProject(true);
                        view.ActiveProjects();
                        break;
                    /*Redirects to Tab Completed Project*/
                    case 4:
                        constants.setAcitiveProject(false);
                        view.CompletedProjects();
                        break;
                    /*Redirects to Tab My Success Story*/
                    case 5:
                        constants.setStory(true);
                        constants.setCertificate(false);
                        constants.setLibrary(false);
                        view.showStory();
                        break;
                    /*Redirects to Tab Certificate Status*/
                    case 6:
                        constants.setStory(false);
                        constants.setCertificate(true);
                        constants.setLibrary(false);
                        view.showStory();
                        break;
                    /*Redirects to Tab Library */
                    case 7:
                        constants.setStory(false);
                        constants.setCertificate(false);
                        constants.setLibrary(true);
                        view.showStory();
                        break;

                    default:
                        //resetData();
                }

                break;
            /*Master CRRE Screen*/
            /*Switch case are the tab number in Master CRRE Home Screen*/
            case 3:
                switch (tab) {
                    /*Redirects to Tab Client Details */
                    case 1:
                        constants.setMasterScreen(1);
                        view.showClientDetails();
                        break;
                    /*Redirects to Tab RRE Details */
                    case 2:
                        constants.setMasterScreen(2);
                        view.showRREDetails();
                        break;
                    /*Redirects to Tab CRRE Details */
                    case 3:
                        constants.setMasterScreen(3);
                        view.showCRREDetails();
                        break;
                    case 4:
                        /*Redirects to Tab Master CRRE */
                        constants.setMasterScreen(4);
                        view.showMRREDetails();
                        break;
                    case 5:
                        /*Redirects to Tab Master Availability */
                        view.showMasterAvailability();
                        break;
                    case 6:
                        /*Redirects to Tab Geographic Location */
                        view.showGeoLocation();
                        break;
                    case 7:
                        /*Redirects to Tab My Active Projects */
                        view.showAuctionsWon();
                        break;
                    case 8:
                        //NIL
                        break;
                    default:

                }

//                Toast.makeText(context, tab+"", Toast.LENGTH_SHORT).show();
                break;

            default:
                //throw new IllegalStateException("Unexpected value: " + role);
        }
    }

    /*In Low memory device the constant values are cleard when app goes to background, to handle this we have stored the data in cache
     * and retrieved it, when constants values are  null*/
    private void resetData() {
        String userRole = repo.getData(constants.getcacheRoleKey());
        String tab = repo.getData(constants.getCACHE_SELECTED_TAB());
        constants.setTAB(Integer.parseInt(tab));
        if (userRole != null) {
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
            }
        }
        init(context);
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
            } else {
                utils.showNetworkAlert(context, this);
            }

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
        if (data != null) {
            GetUserInfoModel jS = new Gson().fromJson(data, GetUserInfoModel.class);
            if (jS == null) {
                return "";
            }
            GetUserInfoModel.Data jsData = jS.getData();
            if (jsData != null) {
                GetUserInfoModel.User user = jsData.getUser();
                if (user != null) {
                    String url = user.getImageUrl();
                    if (url != null) {
                        return url;
                    }

                }
            }
        }

        return "";
    }

    public String getUserName() {
        String data = repo.getData(constants.getCACHE_USER_INFO());
        GetUserInfoModel jS = new Gson().fromJson(data, GetUserInfoModel.class);
        String fname="";
        String lname="";
        if (jS!=null &&jS.getData() != null) {
            if (jS.getData().getUser() != null) {
                final String firstname = jS.getData().getUser().getFirstname();
                if (firstname != null) {
                    fname = firstname;
                    final String lastName = jS.getData().getUser().getLastName();
                    if (lastName != null) {
                        lname = lastName;
                    }
                }
            }
        }
if(!fname.isEmpty()){
    if(!lname.isEmpty()){
return fname+" "+lname;
    }else {
        return lname;
    }
}
        return "-";
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
        utils.sessionExpired(context, repo);
    }

    @Override
    public void refreshNetwork() {
        alertResponse("$LOGOUT");
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

        void showMRREDetails();

        void showCRREDetails();

        void showAuctionsWon();
    }
}

