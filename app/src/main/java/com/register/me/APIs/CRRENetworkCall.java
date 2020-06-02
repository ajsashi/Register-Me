package com.register.me.APIs;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.CurrencyCode;
import com.register.me.model.data.model.CurrencyConversion;
import com.register.me.model.data.model.CRREResponse;
import com.register.me.model.data.model.Error;
import com.register.me.model.data.model.LibraryFiles;
import com.register.me.model.data.model.MyActiveAuction;
import com.register.me.model.data.model.MyAuctionInProgress;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.SuccessCertificate;
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

public class CRRENetworkCall {
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

    public void getMyActiveAucitons(Observer<MyActiveAuction> activeAuctionObserver) {
        Observable<Response<MyActiveAuction>> observable = retrofitBuilder.getActiveAuctionList(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<MyActiveAuction> obs = Observable.just(response.body());
                            obs.subscribe(activeAuctionObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);

    }

    public void getMyAuctionInProgress(Observer<MyAuctionInProgress> inProgressObserver) {
        Observable<Response<MyAuctionInProgress>> observable = retrofitBuilder.getAuctionsInProgress(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<MyAuctionInProgress> obs = Observable.just(response.body());
                            obs.subscribe(inProgressObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void submitBit(JsonObject jsonObject, Observer<ResponseData> submitObserver) {

        Observable<Response<ResponseData>> observable = retrofitBuilder.submitBid(token, jsonObject)
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
                            obs.subscribe(submitObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void declineBid(String proId, Observer<Response<ResponseData>> declineObserver) {
        Observable<Response<ResponseData>> observable = retrofitBuilder.declineBid(token, proId)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<ResponseData>> obs = Observable.just(response);
                            obs.subscribe(declineObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void getCurrencyCode(Observer<Response<CurrencyCode>> cO) {
        Observable<Response<CurrencyCode>> observable = retrofitBuilder.getCurrencyCode(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<CurrencyCode>> obs = Observable.just(response);
                            obs.subscribe(cO);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void convertCurrency(String countryCode, Observer<Response<CurrencyConversion>> cO) {
        Observable<Response<CurrencyConversion>> observable = retrofitBuilder.getCurrency("https://prime.exchangerate-api.com/v5/76c1e88825f6439ec523bc7a/latest/" + countryCode)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<CurrencyConversion>> obs = Observable.just(response);
                            obs.subscribe(cO);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void getCertifyAndStory(Observer<Response<SuccessCertificate>> SCObserver) {
        Observable<Response<SuccessCertificate>> observable = retrofitBuilder.getcertifyAndStory(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<SuccessCertificate>> obs = Observable.just(response);
                            obs.subscribe(SCObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void cancelBid(JsonObject object,Observer<Response<ResponseData>> cancelObserver) {
        Observable<Response<ResponseData>> observable = retrofitBuilder.cancelBid(token,object)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<ResponseData>> obs = Observable.just(response);
                            obs.subscribe(cancelObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }
    public void requestNewCountry(JsonObject object,Observer<Response<ResponseData>> countryObserver) {
        Observable<Response<ResponseData>> observable = retrofitBuilder.requestNewCountry(token,object)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<ResponseData>> obs = Observable.just(response);
                            obs.subscribe(countryObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void getFiles(int id,Observer<Response<LibraryFiles>> fileObserver) {
        Observable<Response<LibraryFiles>> observable = retrofitBuilder.getLibraryFiles(token,id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<LibraryFiles>> obs = Observable.just(response);
                            obs.subscribe(fileObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }

    public void deleteFile(JsonObject jsonObject, Observer<Response<CRREResponse>> deleteFileObserver){
        Observable<Response<CRREResponse>> observable = retrofitBuilder.crreDeleteFiles(token,jsonObject)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<CRREResponse>> obs = Observable.just(response);
                            obs.subscribe(deleteFileObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
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


    public void uploadDocument(String token, JsonObject json, Observer<Response<CRREResponse>> uploadDocObserver) {
        Observable<Response<CRREResponse>> observable = retrofitBuilder.crreUploadFiles(token,json)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        checkNetStatus();
        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<Response<CRREResponse>> obs = Observable.just(response);
                            obs.subscribe(uploadDocObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired("");
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);
                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
        compositeDisposable.add(disposable);
    }
}
