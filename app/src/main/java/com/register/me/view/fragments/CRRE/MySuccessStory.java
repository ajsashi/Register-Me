package com.register.me.view.fragments.CRRE;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.register.me.APIs.CRRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.SuccessCertificate;
import com.register.me.model.data.util.Utils;
import com.register.me.view.Adapter.LibraryAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class MySuccessStory extends BaseFragment implements IFragment, Utils.UtilNetworkInterface, Utils.UtilAlertInterface,LibraryAdapter.OnItemClickListener {

    @BindView(R.id.txt_POffered)
    TextView txt_POffered;
    @BindView(R.id.sub_header)
    TextView sub_header;
    @BindView(R.id.txt_CRREBidded)
    TextView txt_CRREBidded;
    @BindView(R.id.txt_won)
    TextView txt_won;
    @BindView(R.id.txt_value_region)
    TextView txtValueRegion;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.txt_ct_income)
    TextView txt_ct_income;
    @BindView(R.id.txt_life_income)
    TextView txt_life_income;
    @BindView(R.id.potential)
    TextView potential;
    @BindView(R.id.base)
    ConstraintLayout base;
    @BindView(R.id.base_certificate)
    ConstraintLayout base_certificate;
    @BindView(R.id.base_recycler)
    ConstraintLayout base_recycler;
    @BindView(R.id.grid_recyclerView)
    RecyclerView gridRecyclerView;
    @BindView(R.id.progressbar)
    ConstraintLayout progressbar;

    @Inject
    Utils utils;
    @Inject
    Constants constants;
    @Inject
    CRRENetworkCall crreNetworkCall;
    @Inject
    LibraryAdapter libraryAdapter;
    private Observer<String> message;
    private Observer<Response<SuccessCertificate>> SCObserver;
    private Response<SuccessCertificate> scResponse;
    private List<SuccessCertificate.Uncertifiedregion> unCertifiedRegion;
    private Observer<Response<ResponseData>> countryObserver;

    public static IFragment newInstance() {
        return new MySuccessStory();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_success_story;
    }

    @Override
    public String getFragmentName() {
        return "MySuccessStory";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                crreNetworkCall.clearDisposable();
                if (utils.isOnline(getContext())) {
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
                dismissProgress();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        SCObserver = new Observer<Response<SuccessCertificate>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<SuccessCertificate> successCertificateResponse) {
                crreNetworkCall.clearDisposable();
                scResponse = successCertificateResponse;
                dismissProgress();
                buildUI();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        countryObserver = new Observer<Response<ResponseData>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Response<ResponseData> responseDataResponse) {
                crreNetworkCall.clearDisposable();
                dismissProgress();
                Toast.makeText(getContext(), responseDataResponse.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        crreNetworkCall.init(getContext(), message, this);
    }

    @Override
    public void onResume() {
        super.onResume();

        crreNetworkCall.getCertifyAndStory(SCObserver);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        if(constants.isStory())
        {
            sub_header.setText("Success Story");
            fragmentChannel.setTitle("Success Story");
        }else if(constants.isCertificate()){
            sub_header.setText("My Certificate");
            fragmentChannel.setTitle("My Certificate");
        }else if(constants.isLibrary()) {
            sub_header.setText("My Library");
            fragmentChannel.setTitle("My Library");
        }
    }

    private void buildUI() {

        SuccessCertificate.MysuccessStory data = scResponse.body().getData().getMysuccessStory();
        if (constants.isStory()) {
            txt_POffered.setText(data.getOffered() + "");
            txt_CRREBidded.setText(data.getSubmittedBid() + "");
            txt_won.setText(data.getBidWon() + "");
            txt_ct_income.setText(data.getCurrentYear());
            txt_life_income.setText(data.getLifttime());
            potential.setText(data.getPotential());
            base.setVisibility(View.VISIBLE);
        } else if(constants.isCertificate()){
            SuccessCertificate.Data data1 = scResponse.body().getData();
            ratingBar.setRating(data1.getRating());
            txtValueRegion.setText(data1.getRegions());
            unCertifiedRegion = data1.getUncertifiedregions();
            base_certificate.setVisibility(View.VISIBLE);
        }else if(constants.isLibrary()){
            List<SuccessCertificate.Certifiedregion> regions = scResponse.body().getData().getCertifiedregions();
            libraryAdapter.init(getContext(),regions,this);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            gridRecyclerView.setLayoutManager(layoutManager);
            gridRecyclerView.setAdapter(libraryAdapter);
            base_recycler.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void refreshNetwork() {
        onResume();
    }

    private void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @OnClick(R.id.txt_newCountry)
    public void newCountry() {
        utils.setCountrySpinner(unCertifiedRegion,getContext(),15,this);

    }

    @Override
    public void alertResponse(String success) {
        if(success.contains("$LocationID")){
            showProgress();
            success = success.replace("$LocationID:", "");
            JsonObject object = new JsonObject();
            object.addProperty("locationids", success);
            crreNetworkCall.requestNewCountry(object, countryObserver);

        }
    }

    @Override
    public void onViewItemClick(int position) {
        fragmentChannel.showPersonalLibrary(position);
    }
}
