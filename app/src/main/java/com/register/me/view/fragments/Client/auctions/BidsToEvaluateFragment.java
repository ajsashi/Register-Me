package com.register.me.view.fragments.Client.auctions;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.view.Adapter.AuctionAdapter;
import com.register.me.R;
import com.register.me.model.data.model.ActiveAuction;
import com.register.me.presenter.ActiveAuctionPresenter;
import com.register.me.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 11-02-2020PM 06:58.
 */
public class BidsToEvaluateFragment extends BaseFragment implements AuctionAdapter.OnIconClickListener, ActiveAuctionPresenter.IActiveAuction {

    @BindView(R.id.bid_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_content_layout)
    LinearLayout noContentLayout;
    @Inject
    AuctionAdapter auctionAdapter;
    @Inject
    ActiveAuctionPresenter presenter;
    private static List<ActiveAuction.Bidsreadytoevaluate_> list;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bid_evaluation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }

    @Override
    public void onResume() {
        super.onResume();

        switchLayout();

    }

    private void switchLayout() {
        list = presenter.getBidList();

        if (list == null) {
            noContentLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            SetAdapter();
        }
    }

    private void SetAdapter() {
        ArrayList<Object> dataList = new ArrayList<>(list);
        auctionAdapter.init(getContext(), dataList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(auctionAdapter);
    }


    @Override
    public void showMessage(String message) {

    }

    @Override
    public void buildUI(List<ActiveAuction.Auctionsprogress> data) {
        //Do Nothing
    }


    @Override
    public void navigate() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void onViewClick(int adapterPosition) {
        Integer id = list.get(adapterPosition).getId();
        presenter.setProjectID(String.valueOf(id));
        fragmentChannel.showViewProductDetails();
    }

    @Override
    public void onDirectAssignmentClick(int adapterPosition) {
        presenter.directAssignment(list.get(adapterPosition).getId());

    }
}
