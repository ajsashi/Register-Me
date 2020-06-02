package com.register.me.view.fragments.RRE.applicationSubmission;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.presenter.ActivityPresenter;
import com.register.me.view.BaseFragment;

import javax.inject.Inject;

/**
 * Created by Jennifer - AIT on 13-02-2020PM 03:05.
 */
public class ActivityFragment extends BaseFragment {

    @Inject
    ActivityPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(getContext());
        if (presenter.getComment().size() > 0) {
//            LayoutInflater.from(getContext()).inflate(R.layout.)
        }

    }
}
