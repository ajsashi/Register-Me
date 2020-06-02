package com.register.me.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.register.me.model.di.activity.ActivityComponent;
import com.register.me.view.fragmentmanager.FragmentChannel;

import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseFragment extends Fragment {

    protected FragmentChannel fragmentChannel;

    protected abstract int getLayoutId();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentChannel) {
            fragmentChannel = ((FragmentChannel) context);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (fragmentChannel == null && getParentFragment() != null && getParentFragment() instanceof FragmentChannel) {
            fragmentChannel = (FragmentChannel) getParentFragment();
        }

        if (fragmentChannel == null) {
            Timber.d("Parent does not implement FragmentChannel.");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public ActivityComponent injector() {
        return ((BaseActivity) getContext()).injector();
    }
}
