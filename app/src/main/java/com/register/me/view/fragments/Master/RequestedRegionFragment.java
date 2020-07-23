package com.register.me.view.fragments.Master;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.register.me.R;
import com.register.me.model.data.model.ReqGeoRegion;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.RequestedRegionPresenter;
import com.register.me.view.Adapter.ReqRegionAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class RequestedRegionFragment extends BaseFragment implements IFragment, RequestedRegionPresenter.RRListener, ReqRegionAdapter.OnIconClickListener {
    @BindView(R.id.p_Bar)
    ConstraintLayout pLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBar;
    @BindView(R.id.geo_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_content_layout)
    LinearLayout noContentLayout;
    @BindView(R.id.txtTitle)
    TextView title;
    @BindView(R.id.globe)
    ImageView globe;
    @BindView(R.id.add_geo_location)
    ImageView add_geo_location;
    @Inject
    RequestedRegionPresenter presenter;
    @Inject
    ReqRegionAdapter adapter;
    private Context context;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @Inject
    Utils utils;


    public static IFragment newInstance() {
        return new RequestedRegionFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_geo_location;
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
       presenter.getRequestedGeoList();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.img_close)
    public void onImageClose(){
        edtSearch.setText("");
    }
    private void setAdapter(List<ReqGeoRegion.Region> list) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.init(context, list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.add_geo_location)
    public void onAddGeoClick() {
        presenter.showAlertDialog("",-1);
    }

    @Override
    public void showMessage(String s) {
        utils.showToastMessage(getContext(),s);    }

    @Override
    public void updateRecyclerView(List<ReqGeoRegion.Region> locations) {
        final int size = locations.size();
        if (size> 0) {
            setAdapter(locations);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void resume() {
        showProgress();
        presenter.getRequestedGeoList();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("Requested Region List");
        title.setText("Requested Regions");
        globe.setVisibility(View.GONE);
        add_geo_location.setVisibility(View.GONE);
    }

    @Override
    public String getFragmentName() {
        return "RequestedRegionFragment";
    }

    @Override
    public void onAccept(Integer id) {
        presenter.showAlertDialog("accept",id);
    }

    @Override
    public void onReject(Integer id) {
presenter.showAlertDialog("reject",id);
    }

    @Override
    public void showProgress() {
        if (pLayout.getVisibility() == View.GONE) {
            pLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        pLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

}
