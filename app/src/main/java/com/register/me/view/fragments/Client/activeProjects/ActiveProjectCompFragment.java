package com.register.me.view.fragments.Client.activeProjects;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.register.me.model.data.util.Utils;
import com.register.me.view.Adapter.ProjectAdapter;
import com.register.me.R;
import com.register.me.model.data.model.ActiveCompProject;
import com.register.me.presenter.ActiveCompProjectPresenter;
import com.register.me.presenter.ActiveCompProjectPresenter.IACProject;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 12-02-2020PM 12:44.
 */
public class ActiveProjectCompFragment extends BaseFragment implements IFragment, IACProject, ProjectAdapter.ItemClickListener {

    @Inject
    ProjectAdapter adapter;
    @BindView(R.id.active_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.pLayout)
    LinearLayout pLayout;
    @BindView(R.id.noContentText)
    TextView noContentTxt;
    @BindView(R.id.txtBid_country)
    TextView txtBidCountry;
    @BindView(R.id.txt_RegClient)
    TextView txtRegClient;
    @BindView(R.id.no_content_layout)
    LinearLayout noContentLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout pBar;
    @BindView(R.id.sub_header)
    TextView subHeader;
    @Inject
    ActiveCompProjectPresenter presenter;
    private boolean isActive;
    @Inject
    Utils utils;

    public static IFragment newInstance() {
        return new ActiveProjectCompFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active_project_sub;
    }

    @Override
    public String getFragmentName() {
        return "ActiveProjectCompFragment";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injector().inject(this);

        presenter.init(getContext(), this);
        isActive = presenter.getIsActive();
        presenter.getActiveCompleteList();
        String role = presenter.getUserRole();
        if (isActive) {
            subHeader.setText("Active Projects");
            if(role.equals("2")){
                txtRegClient.setText("Client");
                txtBidCountry.setText("Country");
            }else {
            txtBidCountry.setText("Bid Amount \n $");}
        } else {
            subHeader.setText("Completed Projects");
            if(role.equals("2")) {
                txtRegClient.setText("Client");
            }
            txtBidCountry.setText("Country");
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void showMessage(String msg) {
        utils.showToastMessage(getContext(),msg);
    }

    @Override
    public void updateActiveProject(List<ActiveCompProject.ActiveProjectDetail> activeList) {
        if (activeList==null ||activeList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noContentLayout.setVisibility(View.VISIBLE);
            noContentTxt.setHint("No Active Projects Available!");
        } else {
            ArrayList<Object> actList = new ArrayList<>(activeList);
            adapter.init(getContext(), actList, this,presenter.getUserRole());
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    public void updateCompleteProject(List<ActiveCompProject.CompletedProjectDetail> completedList) {
        if (completedList==null||completedList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            noContentLayout.setVisibility(View.VISIBLE);
            noContentTxt.setHint("No Completed Projects Available!");
        } else {
            ArrayList<Object> compList = new ArrayList<>(completedList);
            adapter.init(getContext(), compList, this,presenter.getUserRole());
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void showProgress() {
        pLayout.setVisibility(View.VISIBLE);
        pBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {

        pLayout.setVisibility(View.GONE);
        pBar.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("PROJECTS");
    }

    @Override
    public void onItemClick(int id) {

        presenter.setId(id);

        fragmentChannel.showViewProductDetails();
    }

    @Override
    public void onPayClicked(ActiveCompProject.ActiveProjectDetail actProject) {
        fragmentChannel.showProductInfo(actProject);
    }
}
