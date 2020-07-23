package com.register.me.view.fragments.Master;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.register.me.R;
import com.register.me.model.data.model.McrreList;
import com.register.me.model.data.model.RRE;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.MasterCRREDetailPresenter;
import com.register.me.view.Adapter.MasterCRREAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MasterCRREListFragment extends BaseFragment implements IFragment, MasterCRREDetailPresenter.McrreListener, MasterCRREAdapter.OnIconClickListener {
    @BindView(R.id.client_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.sub_header)
    TextView subheader;
    @BindView(R.id.no_content_layout)
    TextView no_content_layout;
    @Inject
    MasterCRREDetailPresenter presenter;
    @Inject
    MasterCRREAdapter adapter;
    @Inject Utils utils;


    public static IFragment newInstance() {
        return new MasterCRREListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cl_rr_ma_detail;
    }

    @Override
    public String getFragmentName() {
        return "MasterCRREDetailFragment";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);

    }

    private void setAdapter(List<McrreList.Mastercrre> mcrreList) {
        List<Object> objects = new ArrayList<>(mcrreList);
        adapter.init(getContext(), objects, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subheader.setText("Master CRRE List");
        presenter.getMasterCrreList();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("Master CRRE Dashboard");

    }

    @Override
    public void onresume() {
        presenter.getMasterCrreList();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void updateAdapter(List<McrreList.Mastercrre> list) {
        if(list!=null && list.size()>0) {
            setAdapter(list);
        }else {
            no_content_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showMessage(String s) {
        utils.showToastMessage(getContext(),s);
    }

    @Override
    public void onViewClick(Integer id) {
//Nil
    }

    @Override
    public void onViewRREClick(RRE.RREdetail data) {
//Nil
    }
}
