package com.register.me.APIs;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.data.model.ActiveAuction;
import com.register.me.model.data.model.ActiveCompProject;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.ChangePasswordModel;
import com.register.me.model.data.model.CrreList;
import com.register.me.model.data.model.Error;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LocationModel;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.model.PostReply;
import com.register.me.model.data.model.ProjectModel;
import com.register.me.model.data.model.RegisterModel;
import com.register.me.model.data.model.RequestRegion;
import com.register.me.model.data.model.UpdateProfileModel;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.model.ViewDetails;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 11:00.
 */
public class ClientNetworkCall {

    String serverDown = "Unable to connect! \n Sever is down at the moment";

    public void login(ApiInterface apiInterface, String email, String password, String type, NetworkCallInterface listener) {

        Call<LoginModel> call = apiInterface.getUserLogin(email, password, type);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                int code = response.code();

                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        try {
                            LoginModel.Error errorData = new Gson().fromJson(response.errorBody().charStream(), LoginModel.Error.class);
                            listener.onCallFail(errorData.getError());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("failure", t.getMessage());
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void signUp(ApiInterface apiInterface, JsonObject data, NetworkCallInterface listener) {
        Call<RegisterModel> registerCall = apiInterface.getUserSignUp(data);
        registerCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }


            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Log.d("", t.getMessage());
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void forgotPassword(ApiInterface apiInterface, String email, NetworkCallInterface listener) {
        Call<ResponseBody> call = apiInterface.forgotPassword(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");

                        break;
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void changePassword(ApiInterface apiInterface, String token, String ctPass, String newPass, NetworkCallInterface listener) {
        Call<ChangePasswordModel> call = apiInterface.changePassword(token, ctPass, newPass);
        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {

                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void logout(ApiInterface apiInterface, String token, NetworkCallInterface listener) {
        Call<LogoutModel> call = apiInterface.logout(token);
        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {


                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(response.code() + "$LOGOUT$");
                        break;
                    default:
                        listener.onCallFail(response.code() + "$LOGOUT$");
                        break;
                }

            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                listener.onCallFail(t.getMessage() + "$LOGOUT$");
            }
        });
    }

    public void updateAvatar(ApiInterface apiInterface, String token, JsonObject encoded, NetworkCallInterface listener) {
        Call<AvatarModel> call = apiInterface.updateProfileImage(token, encoded);
        call.enqueue(new Callback<AvatarModel>() {
            @Override
            public void onResponse(Call<AvatarModel> call, Response<AvatarModel> response) {

                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "$PROFILE$UPLOAD$FAILED$");
                        break;
                }


            }

            @Override
            public void onFailure(Call<AvatarModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void getUserDetails(ApiInterface apiInterface, String token, NetworkCallInterface listener) {
        Call<GetUserInfoModel> call = apiInterface.getUserDetails(token);
        call.enqueue(new Callback<GetUserInfoModel>() {
            @Override
            public void onResponse(Call<GetUserInfoModel> call, Response<GetUserInfoModel> response) {

                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                    default:
                        listener.onCallFail(response.code() + "");
                        break;
                }

            }

            @Override
            public void onFailure(Call<GetUserInfoModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });

    }

    public void updateUserInformation(ApiInterface apiInterface, String token, JsonObject data, NetworkCallInterface listener) {
        Call<UpdateProfileModel> call = apiInterface.updateUserProfile(token, data);
        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void getProductList(ApiInterface apiInterface, String token, NetworkCallInterface listener) {
        Call<GetProductModel> call = apiInterface.getProductList(token);
        call.enqueue(new Callback<GetProductModel>() {
            @Override
            public void onResponse(Call<GetProductModel> call, Response<GetProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 400:
                        listener.onCallFail(null);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(code + "");
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<GetProductModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });

    }

    public void addProduct(ApiInterface apiInterface, String token, JsonObject data, NetworkCallInterface listener) {
        Call<AddProductModel> call = apiInterface.addProduct(token, data);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 400:
                        Log.d("resonse", String.valueOf(response));
                        AddProductModel errorData = new Gson().fromJson(response.errorBody().charStream(), AddProductModel.class);
                        String message = errorData.getData().getMessage();
                        listener.onCallFail(message);
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<AddProductModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void editProduct(ApiInterface apiInterface, String token, JsonObject data, Integer productId, NetworkCallInterface listener) {
        Call<AddProductModel> call = apiInterface.editProduct(token, productId, data);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<AddProductModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void viewProductOrProject(ApiInterface apiInterface, String token, String key, String id, NetworkCallInterface listener) {
        HashMap<String, String> data = new HashMap<>();
        data.put(key, id);
        Call<ViewDetails> call = apiInterface.viewDetails(token, data);
        call.enqueue(new Callback<ViewDetails>() {
            @Override
            public void onResponse(Call<ViewDetails> call, Response<ViewDetails> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ViewDetails> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void getLocationList(ApiInterface apiInterface, String token, NetworkCallInterface listener) {
        Call<LocationModel> call = apiInterface.getLocation(token);
        call.enqueue(new Callback<LocationModel>() {
            @Override
            public void onResponse(Call<LocationModel> call, Response<LocationModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<LocationModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void initBid(ApiInterface apiInterface, String token, JsonObject data, NetworkCallInterface listener) {
        Call<AddProductModel> call = apiInterface.initiateBid(token, data);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 409:
                        AddProductModel error = new Gson().fromJson(response.errorBody().charStream(), AddProductModel.class);
                        listener.onCallFail(error.getData().getMessage());
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<AddProductModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void directAssignment(ApiInterface apiInterface, String token, JsonObject object, NetworkCallInterface listener) {


        Call<AddProductModel> call = apiInterface.directAssign(token, object);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<AddProductModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());

            }
        });
    }

    public void cancelProject(ApiInterface apiInterface, String token, JsonObject object, NetworkCallInterface listener) {
        Call<ResponseBody> call = apiInterface.cancelProject(token, object);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });

    }

    public void getProjectList(ApiInterface apiInterface, String token, String productId, NetworkCallInterface listener) {
        Call<ProjectModel> call = apiInterface.getProjectList(token, productId);
        call.enqueue(new Callback<ProjectModel>() {
            @Override
            public void onResponse(Call<ProjectModel> call, Response<ProjectModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }

            }

            @Override
            public void onFailure(Call<ProjectModel> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void getAuctionList(ApiInterface apiInterface, String token, NetworkCallInterface listener) {
        Call<ActiveAuction> call = apiInterface.getAuctionList(token);
        call.enqueue(new Callback<ActiveAuction>() {
            @Override
            public void onResponse(Call<ActiveAuction> call, Response<ActiveAuction> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ActiveAuction> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void getACProjectList(ApiInterface apiInterface, String token, NetworkCallInterface listener) {
        Call<ActiveCompProject> call = apiInterface.getACList(token);
        call.enqueue(new Callback<ActiveCompProject>() {
            @Override
            public void onResponse(Call<ActiveCompProject> call, Response<ActiveCompProject> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(response.code() + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ActiveCompProject> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void getACDetails(ApiInterface apiInterface, String token, String id, NetworkCallInterface listener) {
        Call<ViewActCompProject> call = apiInterface.getACDetail(token, id);
        call.enqueue(new Callback<ViewActCompProject>() {
            @Override
            public void onResponse(Call<ViewActCompProject> call, Response<ViewActCompProject> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<ViewActCompProject> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void requestRegion(ApiInterface apiInterface, String token, JsonObject obj, NetworkCallInterface listener) {
        Call<RequestRegion> call = apiInterface.requestRegion(token, obj);
        call.enqueue(new Callback<RequestRegion>() {
            @Override
            public void onResponse(Call<RequestRegion> call, Response<RequestRegion> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<RequestRegion> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void getCrreList(ApiInterface apiInterface, String token, JsonObject object, NetworkCallInterface listener) {
        Call<CrreList> call = apiInterface.getCrreList(token, object);
        call.enqueue(new Callback<CrreList>() {
            @Override
            public void onResponse(Call<CrreList> call, Response<CrreList> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 400:
                        listener.onCallFail("$NOCONTENT$");
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<CrreList> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public void postReply(ApiInterface apiInterface, String token, JsonObject object, NetworkCallInterface listener) {
        Call<PostReply> call = apiInterface.postReply(token, object);
        call.enqueue(new Callback<PostReply>() {
            @Override
            public void onResponse(Call<PostReply> call, Response<PostReply> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCallSuccess(response.body());
                        break;
                    case 500:
                        listener.onCallFail(serverDown);
                        break;
                    case 401:
                        Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                        String message = errorData.getMessage();
                        if (message.equals("Authorization has been denied for this request.")) {
                            listener.sessionExpired();
                        } else {
                            listener.onCallFail(message);
                        }
                        break;
                    default:
                        listener.onCallFail(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<PostReply> call, Throwable t) {
                listener.onCallFail(t.getMessage());
            }
        });
    }

    public interface NetworkCallInterface {

        void onCallSuccess(Object response);

        void onCallFail(String message);

        void sessionExpired();
    }
}
