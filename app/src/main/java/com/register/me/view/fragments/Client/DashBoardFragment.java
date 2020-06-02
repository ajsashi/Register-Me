package com.register.me.view.fragments.Client;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.presenter.DashBoardPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import javax.inject.Inject;

import butterknife.OnClick;


public class DashBoardFragment extends BaseFragment implements IFragment, DashBoardPresenter.View {

    private static final String FRAGMENT_NAME = "DashBoard";

    @Inject
    DashBoardPresenter dashBoardPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        dashBoardPresenter.setView(this);
        fragmentChannel.setTitle(getResources().getString(R.string.client_dashboard));
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void dispose() {
        dashBoardPresenter.dispose();
    }

    @Override
    public void restore() {

    }

    @Override
    public void setTitle() {

    }

    @OnClick(R.id.img_proj_portfolio)
    public void onPortFolioClick() {
        if (fragmentChannel != null) {
            fragmentChannel.showPortFolio();
        }
    }

    @OnClick(R.id.img_act_auction)
    public void onActiveAuctionClick() {
        if (fragmentChannel != null) {
            fragmentChannel.showAuctions();
        }
    }

    @OnClick(R.id.img_act_projects)
    public void onActiveProjectsClick() {
        if (fragmentChannel != null) {
            fragmentChannel.showActiveProjects();
        }
    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dashboard;
    }

    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }
}
