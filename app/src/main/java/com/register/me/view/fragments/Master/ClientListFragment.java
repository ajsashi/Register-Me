package com.register.me.view.fragments.Master;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.R;
import com.register.me.model.data.model.Client;
import com.register.me.model.data.model.RRE;
import com.register.me.model.data.util.Utils;
import com.register.me.view.Adapter.MasterCRREAdapter;
import com.register.me.view.Adapter.MasterCRREAdapter.OnIconClickListener;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ClientListFragment extends BaseFragment implements IFragment, Utils.UtilNetworkInterface, OnIconClickListener {
    @BindView(R.id.client_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.no_content_layout)
    TextView no_content_layout;
    @Inject
    MasterNetworkCall masterNetworkCall;
    @Inject
    MasterCRREAdapter adapter;
    @Inject
    Utils utils;
    private Observer<String> message;
    private Observer<Client> clientObserver;

    public static IFragment newInstance() {
        return new ClientListFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_cl_rr_ma_detail;
    }

    @Override
    public String getFragmentName() {
        return "ClientDetailFragment";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                dismissProgress();
                masterNetworkCall.clearDisposable();
                if (utils.isOnline(getContext())) {
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        clientObserver = new Observer<Client>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Client client) {
                dismissProgress();
                masterNetworkCall.clearDisposable();
                final List<Client.Clientdetail> clientdetailList = client.getData().getClientdetail();
                if (clientdetailList != null && clientdetailList.size() > 0) {
                    setAdapter(clientdetailList);
                } else {
                    no_content_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    private void setAdapter(List<Client.Clientdetail> clientdetail) {
        List<Object> objects = new ArrayList<>(clientdetail);
        adapter.init(getContext(), objects, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void refreshNetwork() {
        onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("CLIENT DETAILS");
        masterNetworkCall.init(getContext(), message, this);
        showProgress();
        masterNetworkCall.getClientList(clientObserver);
    }

    @Override
    public void onViewClick(Integer id) {
        fragmentChannel.showMasterViewDetails(1, id, "", null);
    }

    @Override
    public void onViewRREClick(RRE.RREdetail data) {
        //NIL
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }
}
