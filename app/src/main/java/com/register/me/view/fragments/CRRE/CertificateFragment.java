package com.register.me.view.fragments.CRRE;

import com.register.me.R;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

public class CertificateFragment extends BaseFragment implements IFragment {
    public static IFragment newInstance() {
        return new CertificateFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_certificate;
    }

    @Override
    public String getFragmentName(){
        return "CertificateFragment";
    }
}
