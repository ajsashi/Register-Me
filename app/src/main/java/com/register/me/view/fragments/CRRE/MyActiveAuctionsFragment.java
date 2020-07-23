package com.register.me.view.fragments.CRRE;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.APIs.CRRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.MyActiveAuction;
import com.register.me.model.data.model.MyAuctionInProgress;
import com.register.me.model.data.util.Utils;
import com.register.me.view.Adapter.CrreAuctionAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MyActiveAuctionsFragment extends BaseFragment implements IFragment, Utils.UtilNetworkInterface, CrreAuctionAdapter.OnItemClickListener {

    @BindView(R.id.crre_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.pLayout)
    LinearLayout pLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBar;
    @BindView(R.id.no_content_layout)
    LinearLayout no_content_layout;
    @Inject
    Utils utils;

    @Inject
    CRRENetworkCall crreNetworkCall;
    @Inject
    CrreAuctionAdapter adapter;
    @Inject
    Constants constants;
    private Observer<String> message;
    private Observer<MyActiveAuction> auctionObservable;
    private MyActiveAuction activeList;
    private MyAuctionInProgress inProgressList;
    private Observer<MyAuctionInProgress> inprogressObservable;
    private boolean activeAuction;

    public static IFragment newInstance() {
        return new MyActiveAuctionsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_auctions;
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
         activeAuction = constants.isActiveAuction();
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                dismissProgress();
                if (utils.isOnline(getContext())) {
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
                crreNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        auctionObservable = new Observer<MyActiveAuction>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MyActiveAuction myActiveAuction) {
                activeList = myActiveAuction;
                List<MyActiveAuction.ActiveBiddingdetail> activeBiddingdetails = activeList.getData().getActiveBiddingdetails();
                if (activeBiddingdetails != null && activeBiddingdetails.size() != 0) {
                    setAdapter();
                } else {
                    recyclerView.setVisibility(View.GONE);
                    no_content_layout.setVisibility(View.VISIBLE);
                }
                crreNetworkCall.clearDisposable();
                dismissProgress();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        inprogressObservable = new Observer<MyAuctionInProgress>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MyAuctionInProgress myAuctionInProgress) {

                inProgressList = myAuctionInProgress;
                List<MyAuctionInProgress.BiddingProgressDetail> dataList = inProgressList.getData().getBiddingProgressDetails();
                if (dataList != null && dataList.size() > 0) {
                    setAdapter();
                } else {
                    recyclerView.setVisibility(View.GONE);
                    no_content_layout.setVisibility(View.VISIBLE);
                }
                crreNetworkCall.clearDisposable();
                dismissProgress();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void setAdapter() {

        if (activeAuction) {
            adapter.init(activeList.getData().getActiveBiddingdetails(), this);
        } else {
            adapter.subInit(inProgressList.getData().getBiddingProgressDetails(), this);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        crreNetworkCall.init(getContext(), message, this);
        if (activeAuction) {
            fragmentChannel.setTitle("Active Auctions");
            crreNetworkCall.getMyActiveAucitons(auctionObservable);
        } else {
            fragmentChannel.setTitle("Auctions InProgress");
            crreNetworkCall.getMyAuctionInProgress(inprogressObservable);
        }
    }

    @Override
    public void refreshNetwork() {
        onResume();
    }

    private void showProgress() {
        pLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        pLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onViewClick(Integer productId, Integer projectId) {
        constants.setProductID(null);
        constants.setProjectID(String.valueOf(projectId));
        constants.setviewScreenFrom(4);
        fragmentChannel.showViewProductDetails();
    }
}

