package com.register.me.view.fragments.Client.auctions;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.model.ActiveAuction;
import com.register.me.presenter.ActiveAuctionPresenter;
import com.register.me.view.Adapter.AuctionAdapter;
import com.register.me.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 11-02-2020PM 06:58.
 */
public class ActiveAuctionFragment extends BaseFragment implements ActiveAuctionPresenter.IActiveAuction, AuctionAdapter.OnIconClickListener {

    @BindView(R.id.active_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.no_content_layout)
    LinearLayout noContentLayout;
    @BindView(R.id.pLayout)
    LinearLayout progressBarLayout;
    @Inject
    AuctionAdapter auctionAdapter;
    @Inject
    ActiveAuctionPresenter presenter;
    private List<ActiveAuction.Auctionsprogress> dataList;


    public ActiveAuctionFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active_auctions;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(getContext(), this);
        presenter.getAuctionList();
    }

        private void setAdapter(List<ActiveAuction.Auctionsprogress> data) {
            ArrayList<Object> dataList = new ArrayList<>(data);
            auctionAdapter.init(getContext(), dataList, this);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(auctionAdapter);
        }

    @Override
    public void showMessage(String message) {
        if (message.equals("400")) {
            enableNoContentLay();
        } else {
            KToast.customColorToast((Activity) getContext(), message, Gravity.BOTTOM, KToast.LENGTH_AUTO, R.color.red);
        }

    }

    @Override
    public void buildUI(List<ActiveAuction.Auctionsprogress> data) {
        if (dataList != null) {
            dataList.clear();
        }
        dataList = data;
        if (dataList.size() == 0) {
            enableNoContentLay();
        } else {
            setAdapter(data);
        }
    }

    private void enableNoContentLay() {
        noContentLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void onViewClick(int adapterPosition) {
        Integer id = dataList.get(adapterPosition).getId();
        presenter.setProjectID(String.valueOf(id));
        fragmentChannel.showViewProductDetails();

    }

    @Override
    public void onDirectAssignmentClick(int adapterPosition) {
        presenter.directAssignment(dataList.get(adapterPosition).getId());
    }

    @Override
    public void navigate() {
        presenter.getAuctionList();
    }

    @Override
    public void showProgress() {
        progressBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressBarLayout.setVisibility(View.GONE);
    }
}
