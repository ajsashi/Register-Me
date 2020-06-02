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
import com.register.me.model.data.model.KeyValue;
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

public class RREDetailFragment extends BaseFragment implements IFragment, Utils.UtilNetworkInterface, OnIconClickListener {
    @BindView(R.id.client_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.sub_header)
    TextView subheader;
    @Inject
    MasterNetworkCall masterNetworkCall;
    @Inject
    MasterCRREAdapter adapter;
    private Observer<String> message;
    private Observer<RRE> RREObserver;

    public static IFragment newInstance() {
        return new RREDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_client_rre_detail;
    }

    @Override
    public String getFragmentName() {
        return "RREDetailFragment";
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
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        RREObserver = new Observer<RRE>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(RRE rre) {
                dismissProgress();
                setAdapter(rre.getData().getRredetails());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

    }

    private void setAdapter(List<RRE.RREdetail> clientdetail) {
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
        subheader.setText("RRE Details");
    }

    @Override
    public void refreshNetwork() {
        onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("RRE Dashboard");
        masterNetworkCall.init(getContext(), message, this);
        showProgress();
        masterNetworkCall.getRREList(RREObserver);
    }

    @Override
    public void onViewClick(Integer id) {
        //Nil
    }

    @Override
    public void onViewRREClick(RRE.RREdetail data) {
        ArrayList<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("Application Submission", data.getCompleteApplication(), null));
        list.add(new KeyValue("Online Interview", data.getInterview(), null));
        list.add(new KeyValue("Application & Policy Training", data.getAppTraining(), null));
        list.add(new KeyValue("Certificate", data.getCertificateIssue(), null));

        String applicationId = data.getApplicationid();
        fragmentChannel.showMasterViewDetails(2,data.getId(),applicationId,list);
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
