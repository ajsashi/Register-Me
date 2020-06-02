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
import com.register.me.model.data.model.GeographicLocation;
import com.register.me.presenter.GeoLocationPresenter;
import com.register.me.view.Adapter.GeoListAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import org.jetbrains.annotations.Contract;

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
    @Inject
    GeoLocationPresenter presenter;
    @Inject
    GeoListAdapter adapter;
    private Context context;


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
    public void onAddGeoClick(){
        presenter.showAlertDialog();
    }

    @OnClick(R.id.globe)
    public void onReqRegionClick(){
        fragmentChannel.showReqRegionList();
    }
    @Override
    public void showMessage(String s) {

    }

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
    public void onApprove(Integer id) {

    }

    @Override
    public void onCancel(Integer id) {

    }
}
