package com.register.me.view.fragments.Master;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.register.me.R;
import com.register.me.model.data.model.GeographicLocation;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.GeoLocationPresenter;
import com.register.me.view.Adapter.GeoListAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class GeoLocationFragment extends BaseFragment implements IFragment, GeoLocationPresenter.GeoListener, GeoListAdapter.OnIconClickListener {

    @BindView(R.id.p_Bar)
    ConstraintLayout pLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBar;
    @BindView(R.id.geo_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.no_content_layout)
    LinearLayout noContentLayout;
    @BindView(R.id.edt_search)
    EditText edtSearch;
    @Inject
    GeoLocationPresenter presenter;
    @Inject
    GeoListAdapter adapter;
    private Context context;
    @Inject
    Utils utils;


    public static IFragment newInstance() {
        return new GeoLocationFragment();
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
        presenter.getGeoList();
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

    private void setAdapter(List<GeographicLocation.Location> list) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.init(context, list, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.add_geo_location)
    public void onAddGeoClick() {
        presenter.showAlertDialog("Add");
    }

    @OnClick(R.id.globe)
    public void onReqRegionClick() {
        fragmentChannel.showReqRegionList();
    }

    @Override
    public void showMessage(String s) {
        utils.showToastMessage(getContext(),s);    }

    @Override
    public void updateRecyclerView(List<GeographicLocation.Location> locations) {
        if (locations.size() > 0) {
            setAdapter(locations);
        } else {
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void resume() {
        presenter.getGeoList();
    }

    @Override
    public String getFragmentName() {
        return "GeoLocationFragment";
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

    @Override
    public void onEdit(Integer id, String geographiclocation) {
        presenter.setId(id);
        presenter.setRegion(geographiclocation);
        presenter.showAlertDialog("Edit");
    }

    @Override
    public void onCancel(Integer id) {
        presenter.setId(id);
        presenter.showAlertDialog("Cancel");
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("Geographic Location List");
    }
}
