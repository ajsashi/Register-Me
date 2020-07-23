package com.register.me.view.fragments.Client.portfolio.viewProductDetails;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;
import com.register.me.APIs.CRRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.CurrencyCode;
import com.register.me.model.data.model.CurrencyConversion;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.model.ViewDetails;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.ViewProductPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


public class ViewProductDetailsFragment extends BaseFragment implements IFragment, ViewProductPresenter.IViewProduct, Utils.UtilAlertInterface, Utils.UtilNetworkInterface {

    private static final String FRAGMENT_NAME = "ViewProducts";

    @Inject
    ViewProductPresenter viewProductPresenter;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    CRRENetworkCall crreNetworkCall;
    @BindView(R.id.content)
    LinearLayout contentLayout;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.bid_container)
    LinearLayout bidContainer;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.layout_PSubmittedBids)
    ConstraintLayout bidLayout;
    @BindView(R.id.viewProduct)
    View viewProduct;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.bid_header)
    TextView bidHeader;
    @BindView(R.id.txt_check)
    TextView projectStatus;
    @BindView(R.id.btn_layout)
    ConstraintLayout btnLayout;
    @BindView(R.id.cancelt_btn_layout)
    ConstraintLayout canceltBtnLayout;
    private int screen;
    private ArrayList<KeyValue> map;
    private boolean showBid;
    private List<ViewActCompProject.Comment> comments;
    private Observer<String> message;
    private Observer<Response<ResponseData>> declineObserver;
    private Observer<Response<CurrencyConversion>> currencyObserver;
    private Observer<ResponseData> submitObserver;
    private int userRole;
    private Observer<Response<CurrencyCode>> codeObserver;
    private Observer<Response<ResponseData>> cancelObserver;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        viewProductPresenter.init(getContext(), this);
        screen = constants.getviewScreenFrom();
        map = new ArrayList<>();
        fragmentChannel.setTitle(getResources().getString(R.string.view_product));
        /* if the screen value is 4 - current page is redirected from CCRE Role - Auctions in progress- View Icon*/
        if (viewProductPresenter.getScreen() == 4) {
            message = new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    if (utils.isOnline(getContext())) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                    dismissProgress();
                    crreNetworkCall.clearDisposable();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            declineObserver = new Observer<Response<ResponseData>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseData> responseDataResponse) {
                    Toast.makeText(getContext(), responseDataResponse.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                    crreNetworkCall.clearDisposable();
                    fragmentChannel.popUp();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            currencyObserver = new Observer<Response<CurrencyConversion>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<CurrencyConversion> currencyConversionResponse) {
                    Double usd = currencyConversionResponse.body().getConversionRates().getUSD();
                    utils.updateText(usd);
                    crreNetworkCall.clearDisposable();

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            submitObserver = new Observer<ResponseData>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(ResponseData responseData) {
                    Toast.makeText(getContext(), responseData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                    crreNetworkCall.clearDisposable();
                    fragmentChannel.popUp();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            codeObserver = new Observer<Response<CurrencyCode>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<CurrencyCode> currencyCodeResponse) {
                    utils.triggerAlert(currencyCodeResponse.body().getData().getCurrencydetails());
                    crreNetworkCall.clearDisposable();
                    alertResponse("TRIGGERALERT");

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            cancelObserver = new Observer<Response<ResponseData>>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Response<ResponseData> responseData) {
                    Toast.makeText(getContext(), responseData.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                    crreNetworkCall.clearDisposable();
                    fragmentChannel.popUp();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };

        } else if (viewProductPresenter.getScreen() == 5) {

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        crreNetworkCall.init(getContext(), message, this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        showProgress();
      /*  new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 150);*/
      /*
      * screen 3 - Client Role - Active Proojects - View Icon*/

        if (screen != 3 && screen != 5) {
            viewProductPresenter.triggerApi("");
        } else if (screen == 5) {
            viewProductPresenter.getMCRREProducts();
            return;
        } else {
            viewProductPresenter.triggerApi("ACP");
        }
        userRole = viewProductPresenter.getUserRole();
    }

    private void getHeaderList(ArrayList<KeyValue> keyValues) {
        map.clear();
        map.addAll(keyValues);
        setHeaderContainerData(map);
        showBid = viewProductPresenter.hasBids();
        if (showBid && userRole != 2) {
            bidLayout.setVisibility(View.VISIBLE);
            setBidContainerData();
        }
    }

    private void setBidContainerData() {
        List<ViewDetails.Crrebiddingdetail> crre = viewProductPresenter.getBidContainerData();
        bidContainer.removeAllViews();
        for (ViewDetails.Crrebiddingdetail crreItem : crre) {
            View inflater = viewProductPresenter.getBidView(crreItem);
            bidContainer.addView(inflater);
        }
    }


    private void setContentContainerData(ArrayList<KeyValue> data) {
        contentLayout.removeAllViews();
        for (KeyValue val : data) {
            View inflater = viewProductPresenter.getContentView(val);
            contentLayout.addView(inflater);
        }

    }


    private void setHeaderContainerData(ArrayList<KeyValue> map) {
        container.removeAllViews();
        for (KeyValue val : map) {
            View view = viewProductPresenter.getHeaderView(val);
            container.addView(view);
        }
    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_product;
    }

    public static ViewProductDetailsFragment newInstance() {
        return new ViewProductDetailsFragment();
    }

    @OnClick(R.id.viewProduct)
    public void clickViewProduct() {
        if (contentLayout.getVisibility() == View.VISIBLE) {

            contentLayout.setVisibility(View.GONE);
        } else {
            contentLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.txt_check)
    public void onClickProjectStatus() {
        fragmentChannel.showCommentScreen(comments, viewProductPresenter.getProjectAssignId());
    }

    @OnClick(R.id.submit_btn)
    public void onclickSubmitBtn() {
        if(crreNetworkCall.checkNetStatus()) {
            crreNetworkCall.getCurrencyCode(codeObserver);
        }

    }

    @OnClick(R.id.cancel_btn)
    public void onClickCancelBtn() {
        utils.showAlert(getContext(), 14, this);
    }

    @OnClick(R.id.decline_btn)
    public void onclickDeclineBtn() {
        utils.showAlert(getContext(), 12, this);
    }

    @Override
    public void buildContent(ArrayList<KeyValue> kv) {
        dismissProgress();
        scrollView.setVisibility(View.VISIBLE);
        setContentContainerData(kv);
        boolean isActive = viewProductPresenter.getIsActive();
        if (userRole == 2 && isActive) {
            btnLayout.setVisibility(View.VISIBLE);
        } else if (userRole == 2 && !isActive && viewProductPresenter.getBidStatus().equals("Submitted")) {
            canceltBtnLayout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void buildHeader(ArrayList<KeyValue> keyValue) {
        scrollView.setVisibility(View.VISIBLE);
        getHeaderList(keyValue);
    }


    @Override
    public void showErroMessage(String message) {
        utils.showToastMessage(getContext(),message);      }

    @Override
    public void buildTransactionUI(List<ViewActCompProject.Paymentdetail> paymentdetails) {
        if (paymentdetails.size() != 0) {
            bidLayout.setVisibility(View.VISIBLE);
            bidHeader.setText("Payment Details");
            bidContainer.removeAllViews();
            int i = 1;
            for (ViewActCompProject.Paymentdetail payItem : paymentdetails) {
                View inflater = viewProductPresenter.getBidContainerView(i, payItem);
                bidContainer.addView(inflater);
                i++;
            }
        }
    }


    @Override
    public void displayComments(List<ViewActCompProject.Comment> comments) {
        projectStatus.setVisibility(View.VISIBLE);
        this.comments = comments;
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onresume() {
       /* if (viewProductPresenter.getScreen() == 5) {
            viewProductPresenter.getMCRREProducts();
        }*/
       onResume();
    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("$DECLINE")) {
            crreNetworkCall.declineBid(constants.getProjectID(), declineObserver);
        } else if (success.contains("$TRIGGERAPI$.")) {
            success = success.replace("$TRIGGERAPI$.", "");
            crreNetworkCall.convertCurrency(success, currencyObserver);
        } else if (success.contains("$SUBMITBID$")) {
            success = success.replace("$SUBMITBID$", "");
            String[] splitData = success.split(":");
            ArrayList<String> key = new ArrayList<>();
            key.add("projectid");
            key.add("amount");
            key.add("currencytypeid");
            key.add("projectduration");
            key.add("days");
            key.add("remarks");
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(key.get(0), Integer.parseInt(constants.getProjectID()));
            for (int i = 1; i < splitData.length + 1; i++) {
                jsonObject.addProperty(key.get(i), splitData[i - 1]);
            }
            crreNetworkCall.submitBit(jsonObject, submitObserver);
        } else if (success.equals("TRIGGERALERT")) {
            utils.showAlert(getContext(), 13, this);
        } else if (success.contains("$CANCEL")) {
            showProgress();
            String[] data = success.split(":");
            Log.d("Data", data.length + "");
            JsonObject object = new JsonObject();
            object.addProperty("projectid", constants.getProjectID());
            object.addProperty("cancelremarks", data[1]);
            crreNetworkCall.cancelBid(object, cancelObserver);
           //Toast.makeText(getContext(), success, Toast.LENGTH_SHORT).show();
        } else if (success.equals("$ALERT$")) {
            Toast.makeText(getContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void refreshNetwork() {
        onResume();
    }
}
