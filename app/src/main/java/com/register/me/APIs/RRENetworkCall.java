package com.register.me.APIs;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.ApplicationRRESubmission;
import com.register.me.model.data.model.Error;
import com.register.me.model.data.model.PolicyTraining;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.RREComments;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.Steps;
import com.register.me.model.data.model.TimeSchedule;
import com.register.me.model.data.model.UploadDoc;
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

public class RRENetworkCall {
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
    private String errorMessage;

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

    public void getStepStatus(Observer<Integer> getStepObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<Steps>> observable = retrofitBuilder.getStepStatus(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation()).
                        observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = observable.subscribe(stepsResponse -> {
            int code = stepsResponse.code();

            switch (code) {
                case 200:
                    assert stepsResponse.body() != null;
                    Observable<Integer> obs = Observable.just(stepsResponse.body().getData().getCurrentstep());
//                    Observable<Integer> obs = Observable.just(3);
                    obs.subscribe(getStepObserver);

                    break;
                case 401:
                    Error errorData = new Gson().fromJson(stepsResponse.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
//                    throw new IllegalStateException("Unexpected Error: " + code);
            }
        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);
    }

    public void viewRREApplication(Observer<RREApplication> getApplicationObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<RREApplication>> observable = retrofitBuilder.getRREApplication(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<RREApplication> obs = Observable.just(response.body());
                            obs.subscribe(getApplicationObserver);
                            break;
                        case 400:
                            Error error_Data = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String msg = error_Data.getMessage();
                            errorMessage(msg);
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

    public void submitRREApplication(JsonObject data, Observer<ApplicationRRESubmission> submitObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<ApplicationRRESubmission>> observable = retrofitBuilder.submitRREApplicationForm(token, data)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> {

            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<ApplicationRRESubmission> obs = Observable.just(response.body());
                    obs.subscribe(submitObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
//                    throw new IllegalStateException("Unexpected Error: " + code);

            }
        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);

    }

    public void uploadDocument(Context context, Observer<String> messageObserver, Utils.UtilNetworkInterface networkInterface, String token, JsonObject json, Observer<UploadDoc> uploadDocObserver) {
        this.context = context;
        this.messageObserver = messageObserver;
        this.listener = networkInterface;
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<UploadDoc>> observable = retrofitBuilder.uploadDoc(token, json)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());


        Disposable disposable = observable.subscribe(response -> {
            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<UploadDoc> obs = Observable.just(response.body());
                    obs.subscribe(uploadDocObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
//                    throw new IllegalStateException("Unexpected Error: " + code);
            }
        }, error -> {
            Log.d("OkHttp -------", error.getLocalizedMessage());
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);

    }

    public void postComment(String token, JsonObject json, Observer<RREComments> commentsObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<RREComments>> observable = retrofitBuilder.rrePostReply(token, json)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> {
            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<RREComments> obs = Observable.just(response.body());
                    obs.subscribe(commentsObserver);
                    break;
                case 400:
                    Error error_Data = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String msg = error_Data.getData().getMessage();
                    errorMessage(msg);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
//                    throw new IllegalStateException("Unexpected Error: " + code);
            }

        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);


    }

    public void getAvailableTimeSchedule(Observer<TimeSchedule> scheduleObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<TimeSchedule>> observable = retrofitBuilder.getAvailableTime(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = observable.subscribe(response -> {
            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<TimeSchedule> obs = Observable.just(response.body());
                    obs.subscribe(scheduleObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
//                    throw new IllegalStateException("Unexpected Error: " + code);
            }

        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);

    }

    public void submitCancelSlot(JsonObject object, Observer<ResponseData> slotObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<ResponseData>> observable = retrofitBuilder.submitCancelSchedule(token, object)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> {
            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<ResponseData> obs = Observable.just(response.body());
                    obs.subscribe(slotObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
//                    throw new IllegalStateException("Unexpected Error: " + code);
            }

        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);


    }

    public void getQuestions(Observer<PolicyTraining> questionObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<PolicyTraining>> observable = retrofitBuilder.getTrainingQuetions(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> {
            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<PolicyTraining> obs = Observable.just(response.body());
                    obs.subscribe(questionObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
                    //throw new IllegalStateException("Unexpected Error: " + code);
            }

        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);

    }

    public void trainingStatus(JsonObject object, Observer<ResponseData> statusObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<ResponseData>> observable = retrofitBuilder.postTrainingStatus(token, object)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> {
            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<ResponseData> obs = Observable.just(response.body());
                    obs.subscribe(statusObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
                    //throw new IllegalStateException("Unexpected Error: " + code);
            }
        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);

    }

    public void submitCertificate(Observer<ResponseData> certificateObserver) {
        if (!checkNetStatus()) {
            return;
        }
        Observable<Response<ResponseData>> observable = retrofitBuilder.submitCertificate(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = observable.subscribe(response -> {
            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<ResponseData> obs = Observable.just(response.body());
                    obs.subscribe(certificateObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired("");
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
//                    throw new IllegalStateException("Unexpected Error: " + code);
            }
        }, error -> {
            errorMessage(error.getMessage());
        });
        compositeDisposable.add(disposable);

    }

    private void errorMessage(String s) {
        errorMessage = s;
       // checkNetStatus();
        Observable<String> messageObs = Observable.just(errorMessage);
        messageObs.subscribe(messageObserver);
    }

    public void sessionExpired(String message) {
        if (message.isEmpty()) {
            message = "Session Expired";
        }
        Observable<String> messageObs = Observable.just(message);

        utils.sessionExpired(context, repo);
        messageObs.subscribe(messageObserver);
    }

    public void clearDisposable() {
        compositeDisposable.clear();
    }

    public synchronized boolean checkNetStatus() {
        if (checkNetwork()) {
            if (!utils.checkAlert()) {
                utils.showNetworkAlert(context, listener);

            }
            return false;
        } else {
            if (utils.checkAlert()) {
                utils.dismissAlert();
            }
        }
        /*if(errorMessage!=null) {
            Observable<String> messageObs = Observable.just(errorMessage);
            messageObs.subscribe(messageObserver);
            errorMessage=null;
        }*/
        return true;
    }

    private boolean checkNetwork() {
        if (!utils.isOnline(context)) {
            return true;
        }
        return false;
    }
}
