package com.register.me.view.fragments.RRE.Certification;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.register.me.APIs.RRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import javax.inject.Inject;

import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class CertificationFragment extends BaseFragment implements IFragment, Utils.UtilNetworkInterface {
    @Inject
    RRENetworkCall rreNetworkCall;
    private Observer<String> message;
    private Observer<ResponseData> certificateObserver;

    public static IFragment newInstance() {
        return new CertificationFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_certification;
    }

    @Override
    public String getFragmentName() {
        return "CertificationFragment";
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
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();

                rreNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        certificateObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                Toast.makeText(getContext(), responseData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                rreNetworkCall.sessionExpired(responseData.getData().getMessage());
                rreNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
    }

    @OnClick(R.id.card_proceed)
    public void onClickProceed() {
        rreNetworkCall.init(getContext(), message, this);
        rreNetworkCall.submitCertificate(certificateObserver);
    }

    @Override
    public void refreshNetwork() {
        rreNetworkCall.checkNetStatus();
    }
}
