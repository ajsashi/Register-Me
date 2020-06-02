package com.register.me.view.fragments.Client.portfolio;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.view.Adapter.ProductAdapter;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.presenter.PortFolioPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class PortFolioFragment extends BaseFragment implements IFragment, PortFolioPresenter.View, ProductAdapter.OnItemClickListener {

    @Inject
    PortFolioPresenter portFolioPresenter;
    @Inject
    Constants constants;
    @Inject
    ProductAdapter adapter;
    @BindView(R.id.tv_recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBarLayout;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.no_content_layout)

    LinearLayout noContentLayout;
    private Activity activity = getActivity();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        constants.setProjectID(null);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            fragmentChannel.setTitle(getResources().getString(R.string.awarded_prod_dash));
            portFolioPresenter.init(this, getContext());
            portFolioPresenter.getList();

        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

    private void setAdapter(List<GetProductModel.Product> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter.init(getContext(), list, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @OnClick(R.id.tv_add_new_proj)
    public void onNewProjClick() {
        constants.setSelectedList(null);
        if (fragmentChannel != null) {
            fragmentChannel.showAddProduct();
        }
    }


    @Override
    public String getFragmentName() {
        return "PortFolio";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_portfolio;
    }

    public static PortFolioFragment newInstance() {
        return new PortFolioFragment();
    }

    @Override
    public void onViewIconClick(int adapterPosition) {
        constants.setviewScreenFrom(1);
        setConstants(adapterPosition);
        fragmentChannel.showViewProductDetails();
    }

    @Override
    public void onAuctionIconClick(int adapterPosition) {
        setConstants(adapterPosition);
        fragmentChannel.showInitiateProductRegistration();
    }

   /* @Override
    public void onSendIconClick(int adapterPosition) {
        setConstants(adapterPosition);
        fragmentChannel.showDirectAssignment();
    }*/

    @Override
    public void onCountryIconClick(int adapterPosition) {
        setConstants(adapterPosition);
        fragmentChannel.showCountryScreen();
    }

    @Override
    public void onEditIconClick(int adapterPosition) {
        setConstants(adapterPosition);
        fragmentChannel.showAddProduct();
    }

    private void setConstants(int adapterPosition) {
        GetProductModel.Product selectedList = portFolioPresenter.checkData().get(adapterPosition);
        constants.setSelectedList(selectedList);
        constants.setProductID(String.valueOf(selectedList.getProductId()));
    }

    @Override
    public void showErroMessage(String message) {

        KToast.customColorToast((Activity) Objects.requireNonNull(getContext()), message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void updateList(List<GetProductModel.Product> list) {
        contentLayout.setVisibility(View.VISIBLE);
        setAdapter(list);
    }

    @Override
    public void showProgress() {
        if (progressBarLayout.getVisibility() == View.GONE) {
            progressBarLayout.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void switchView() {
        noContentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressBarLayout.getVisibility() == View.VISIBLE)
            progressBarLayout.setVisibility(View.GONE);

    }
}
