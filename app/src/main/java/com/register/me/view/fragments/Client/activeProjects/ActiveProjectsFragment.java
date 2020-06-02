package com.register.me.view.fragments.Client.activeProjects;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.presenter.ActiveProjectPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import javax.inject.Inject;

import butterknife.OnClick;


public class ActiveProjectsFragment extends BaseFragment implements IFragment, ActiveProjectPresenter.View {

    private static final String FRAGMENT_NAME = "ActiveProjects";

    @Inject
    ActiveProjectPresenter activeProjectPresenter;
    @Inject
    Constants constants;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        activeProjectPresenter.setView(this);
        fragmentChannel.setTitle(getResources().getString(R.string.client_dashboard));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }


    @Override
    public void dispose() {
        activeProjectPresenter.dispose();
    }

    @Override
    public void restore() {

    }

    @Override
    public void setTitle() {

    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active_project;
    }

    public static ActiveProjectsFragment newInstance() {
        return new ActiveProjectsFragment();
    }

    @OnClick(R.id.img_active_project)
    public void activeProject() {
        constants.setAcitiveProject(true);
        fragmentChannel.showActiveProjectsSub();
    }

    @OnClick(R.id.img_completed_project)
    public void completedProject() {
        constants.setAcitiveProject(false);
        fragmentChannel.showActiveProjectsSub();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("Project Details");
    }
}
