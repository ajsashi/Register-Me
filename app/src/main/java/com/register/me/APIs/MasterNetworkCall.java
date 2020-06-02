package com.register.me.APIs;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.CertificateStatus;
import com.register.me.model.data.model.Client;
import com.register.me.model.data.model.ClientProductList;
import com.register.me.model.data.model.Error;
import com.register.me.model.data.model.GeographicLocation;
import com.register.me.model.data.model.MasterDash;
import com.register.me.model.data.model.RRE;
import com.register.me.model.data.model.ReqGeoRegion;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.ScheduleList;
import com.register.me.model.data.model.UserInfo;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MasterNetworkCall {
    @Inject
    Retrofit retrofit;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    private Observer<String> messageObserver;
    private Context context;
    private ApiInterface retrofitBuilder;
    private String token;
    private CompositeDisposable compositeDisposable;
    private Utils.UtilNetworkInterface listener;

    public void init(Context context, Observer<String> message, Utils.UtilNetworkInterface listener) {
        this.context = context;
        this.listener = listener;
        messageObserver = message;
        ((BaseActivity) context).injector().inject(this);
        retrofitBuilder = retrofit.create(ApiInterface.class);
        token = repo.getData(constants.getcacheTokenKey());
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        } else {
            clearDisposable();
            compositeDisposable = new CompositeDisposable();
        }
    }

    public void getDashBoardData(Observer<MasterDash> dashObserver) {
        Observable<Response<MasterDash>> observable = retrofitBuilder.getMasterDashboard(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<MasterDash> obs = Observable.just(response.body());
                            obs.subscribe(dashObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    final String message = error.getMessage();
                    if(message.contains("Unable to resolve host"))
                        if (!utils.checkAlert()) {
                            utils.showNetworkAlert(context, listener);
                        }
                    errorMessage(message);
                });
        compositeDisposable.add(disposable);

    }

    public void getClientList(Observer<Client> clientObserver) {
        Observable<Response<Client>> observable = retrofitBuilder.getclientdetails(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Client> obs = Observable.just(response.body());
                            obs.subscribe(clientObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void getRREList(Observer<RRE> RREObserver) {
        Observable<Response<RRE>> observable = retrofitBuilder.getRREDetails(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<RRE> obs = Observable.just(response.body());
                            obs.subscribe(RREObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void getUserInformation(JsonObject object,Observer<UserInfo> InfoObserver) {
        Observable<Response<UserInfo>> observable = retrofitBuilder.getUserInfo(token,object)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<UserInfo> obs = Observable.just(response.body());
                            obs.subscribe(InfoObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void approveApplication(String id,String useCase, Observer<ResponseData> aplObserver, JsonObject object) {
        Observable<Response<ResponseData>> observable = null;

        switch (useCase){
            case "1":
                observable = retrofitBuilder.approveApplication(token,id)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case "2":
                observable = retrofitBuilder.approveInterview(token,object)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case "3":
                observable = retrofitBuilder.approveTraining(token,id)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
                break;
            case "4":
                observable = retrofitBuilder.approveCertificate(token,id)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
                break;

        }
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ResponseData> obs = Observable.just(response.body());
                            obs.subscribe(aplObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }



    public void getClientProductList(String id,Observer<ClientProductList> listObserver) {
        Observable<Response<ClientProductList>> observable = retrofitBuilder.getClientProductDetails(token,id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ClientProductList> obs = Observable.just(response.body());
                            obs.subscribe(listObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void getCertificateStatus(String id,Observer<CertificateStatus> statusObserver) {
        Observable<Response<CertificateStatus>> observable = retrofitBuilder.getCertificateStatus(token,id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<CertificateStatus> obs = Observable.just(response.body());
                            obs.subscribe(statusObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }


    public void getInterviewList(Observer<ScheduleList> listObserver) {
        Observable<Response<ScheduleList>> observable = retrofitBuilder.getInterviewSchedule(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ScheduleList> obs = Observable.just(response.body());
                            obs.subscribe(listObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void approveSchedule(String id ,Observer<ResponseData> approveObserver) {
        Observable<Response<ResponseData>> observable = retrofitBuilder.approveSchedule(token,id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ResponseData> obs = Observable.just(response.body());
                            obs.subscribe(approveObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void cancelSchedule(String id ,Observer<ResponseData> cancelObserver) {
        Observable<Response<ResponseData>> observable = retrofitBuilder.cancelSchedule(token,id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ResponseData> obs = Observable.just(response.body());
                            obs.subscribe(cancelObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void addAvailability(JsonObject object ,Observer<ResponseData> availableObserver) {
        Observable<Response<ResponseData>> observable = retrofitBuilder.addAvailability(token,object)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ResponseData> obs = Observable.just(response.body());
                            obs.subscribe(availableObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void getGeoLocation(Observer<GeographicLocation> geoObserver) {
        Observable<Response<GeographicLocation>> observable = retrofitBuilder.getGeoLocationList(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<GeographicLocation> obs = Observable.just(response.body());
                            obs.subscribe(geoObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }


    public void getReqGeoLocation(Observer<ReqGeoRegion> geoObserver) {
        Observable<Response<ReqGeoRegion>> observable = retrofitBuilder.getRequestedGeoList(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ReqGeoRegion> obs = Observable.just(response.body());
                            obs.subscribe(geoObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void acceptReqRegion(String id ,Observer<ResponseData> acceptObserver) {
        Observable<Response<ResponseData>> observable = retrofitBuilder.acceptRequestedRegion(token,id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<ResponseData> obs = Observable.just(response.body());
                            obs.subscribe(acceptObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
//                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    private void errorMessage(String s) {
        Observable<String> messageObs = Observable.just(s);
        messageObs.subscribe(messageObserver);
    }

    public void sessionExpired(String message) {
        if (message.isEmpty()) {
            message = "Session Expired";
        }
        Observable<String> messageObs = Observable.just(message);

        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(), null);
        utils.sessionExpired(context);
        messageObs.subscribe(messageObserver);
    }

    public void clearDisposable() {
        compositeDisposable.clear();
    }

    public void checkNetStatus() {
        if (checkNetwork()) {
            if (!utils.checkAlert()) {
                utils.showNetworkAlert(context, listener);
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

}
