package com.register.me.view.fragments.Master;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.AuctionWon;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.AuctionsWonPresenter;
import com.register.me.view.Adapter.AuctionsWonAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AuctionsWonFragment extends BaseFragment implements IFragment, AuctionsWonPresenter.AuctionsWonListener, AuctionsWonAdapter.OnIconClickListener {
    @BindView(R.id.p_Bar)
    ConstraintLayout pLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBar;
    @BindView(R.id.auction_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_content_layout)
    LinearLayout noContentLayout;
    @Inject
    AuctionsWonPresenter presenter;
    private Context context;
    @Inject
    Utils utils;
    @Inject
    AuctionsWonAdapter adapter;


    public static IFragment newInstance() {
        return new AuctionsWonFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_auctions_won;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        this.context = getContext();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    private void setAdapter(List<AuctionWon.AuctionsWonData> list) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.init(context, list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("My Active Projects");
        showProgress();
        presenter.getAuctionsList();
    }

    @Override
    public String getFragmentName() {
        return "AuctionsWonFragment";
    }

    public void showProgress() {
        if (pLayout.getVisibility() == View.GONE) {
            pLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        pLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateList(List<AuctionWon.AuctionsWonData> auctionswon) {
        hideProgress();
        if (auctionswon.size() > 0) {
            setAdapter(auctionswon);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewIconClick(Integer productId) {
        presenter.setProductId(productId);
        fragmentChannel.showViewProductDetails();
    }
}
