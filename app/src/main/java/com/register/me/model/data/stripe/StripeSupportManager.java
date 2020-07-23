package com.register.me.model.data.stripe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


import com.register.me.APIs.ApiInterface;
import com.register.me.model.data.model.ChargeTokenResponse;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StripeSupportManager {
    Context context;
    private String amount;
    private String description;
    private Retrofit retrofit;
    private Activity activity;
//    private String STRIPE_PUBLISHABLE_KEY="pk_test_eUC7kUh8rc5utY020gjdPOUX00619DrNhi";
    private String STRIPE_PUBLISHABLE_KEY="pk_live_ojXnPe1xXJbOVUxeLpV3uIef00VIJNS5zT";

    public void init(Context context) {
        this.context = context;
//        ((BaseActivity) context).injector().inject(this);
    }

    /*public void googleInit(Activity activity) {
        this.activity = activity;
        if (paymentsClient == null) {
            createPaymentClient();
        }
        isReadyToPay();
    }

    private void createPaymentClient() {
        paymentsClient =
                Wallet.getPaymentsClient(activity,
                        new Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                                .build());

    }*/

    public void setStripeInput(String amount, String reason) {
//        amount="200";
        float intAmount = Float.parseFloat(amount) * 100;
        this.amount = String.valueOf((int)intAmount);
        this.description = reason;
    }


    public void convertCardInfoToToken(Card card) {
        Stripe stripe = new Stripe(context, PaymentConfiguration.getInstance(context).getPublishableKey());
        stripe.createCardToken(card, new ApiResultCallback<Token>() {
            @Override
            public void onSuccess(Token token) {
//                Toast.makeText(context, token.getId().toString(), Toast.LENGTH_SHORT).show();
                chargeToken(token);
            }

            @Override
            public void onError(@NotNull Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("CheckResult")
    public void chargeToken(Token token) {
        ApiInterface apiInterface = setUpRetrofit().create(ApiInterface.class);
        Observable<Response<ChargeTokenResponse>> call;
        call = apiInterface.chargeToken("Bearer sk_test_eGDhfSZT0mm8buct5cu3YFiE00PPA3zqpp", amount, "usd", description, token.getId())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        call.subscribe(response -> {
                    int code = response.code();
                    ResponseBody error = response.errorBody();
                    if(error!=null){
                        Toast.makeText(context, "Check log", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    switch (code) {
                        case 200:
                            String sellerMessage = response.body().getOutcome().getSellerMessage();
                            if (sellerMessage.contains("Payment complete")) {
                                EventBus.getDefault().post(response.body().getReceiptUrl());
//                                listener.stripCompleted(response.body().getReceiptUrl());
                            }
                            break;
                        default:
//                            listener.stripCompleted(response.body().getStatus());
                            if (response.body() == null && response.errorBody() != null) {


                            }
                            Toast.makeText(context, "Code : " + code + " - Message : " + response.message(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                },
                error -> {
                    Toast.makeText(context, "Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );


    }

    private Retrofit setUpRetrofit() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stripe.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

   /* private void isReadyToPay() {
        IsReadyToPayRequest request = IsReadyToPayRequest.newBuilder()
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                .build();
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(
                new OnCompleteListener<Boolean>() {
                    public void onComplete(Task<Boolean> task) {
                        try {
                            boolean result =
                                    task.getResult(ApiException.class);
                            if (result == true) {
                            } else {
                                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        } catch (ApiException exception) {
                        }
                    }
                });
    }

    public PaymentDataRequest createPaymentDataRequest() {

        PaymentDataRequest.Builder request =
                PaymentDataRequest.newBuilder()
                        .setTransactionInfo(
                                TransactionInfo.newBuilder()
                                        .setTotalPriceStatus(WalletConstants.TOTAL_PRICE_STATUS_FINAL)
                                        .setTotalPrice("1")
                                        .setCurrencyCode("USD")
                                        .build())
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_CARD)
                        .addAllowedPaymentMethod(WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD)
                        .setEmailRequired(true)
                        .setPhoneNumberRequired(true)
                        .setCardRequirements(
                                CardRequirements.newBuilder()
                                        .addAllowedCardNetworks(Arrays.asList(
                                                WalletConstants.CARD_NETWORK_AMEX,
                                                WalletConstants.CARD_NETWORK_DISCOVER,
                                                WalletConstants.CARD_NETWORK_VISA,
                                                WalletConstants.CARD_NETWORK_MASTERCARD))
                                        .setBillingAddressRequired(true)
                                        .build());


        request.setPaymentMethodTokenizationParameters(createTokenizationParameters());
        return request.build();
    }

    private PaymentMethodTokenizationParameters createTokenizationParameters() {
        return PaymentMethodTokenizationParameters.newBuilder()
                .setPaymentMethodTokenizationType(WalletConstants.PAYMENT_METHOD_TOKENIZATION_TYPE_PAYMENT_GATEWAY)
                .addParameter("gateway", "stripe")
//                .addParameter("stripe:publishableKey", "pk_test_TpwrHuchKh4oBpynQaTPUMe100tCs1Q85c")
                .addParameter("stripe:publishableKey", STRIPE_PUBLISHABLE_KEY)
                .addParameter("stripe:version", "2018-11-08")
                .build();
    }

    public PaymentsClient getPaymentClient() {
        if (paymentsClient == null) {
            createPaymentClient();
        }
        return paymentsClient;
    }*/
}
