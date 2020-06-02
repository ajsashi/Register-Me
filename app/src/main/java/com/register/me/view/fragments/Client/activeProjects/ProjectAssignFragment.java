package com.register.me.view.fragments.Client.activeProjects;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.view.Adapter.SpinAdapter;
import com.register.me.R;
import com.register.me.model.data.model.CrreList;
import com.register.me.presenter.ProjectAssignPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 10-03-2020PM 04:32.
 */
public class ProjectAssignFragment extends BaseFragment implements IFragment, ProjectAssignPresenter.IProjectAssign {

    private static String locationName;
    @BindView(R.id.txt_pName)
    TextView proName;
    @BindView(R.id.txt_pCountry)
    TextView proCountry;
    @BindView(R.id.spinner_expert)
    Spinner spinnerExpert;
    @Inject
    ProjectAssignPresenter presenter;

    static String pName;
    static int locationId;
    private SpinAdapter<CrreList.Expertlist> adapter;

    public static IFragment newInstance(String name, int locationid,String locName) {
        pName = name;
        locationId = locationid;
        locationName = locName;
        return new ProjectAssignFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project_assign;
    }

    @Override
    public String getFragmentName() {
        return "ProjectAssignFragment";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        proName.setText(pName);
        proCountry.setText(locationName);
        presenter.init(getContext(),this);
        presenter.getCrreList(locationId);
    }

    @Override
    public void showMessage(String message) {
        KToast.customColorToast(getActivity(), message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);

    }

    @Override
    public void updateSPinner(List<CrreList.Expertlist> data) {
        adapter = new SpinAdapter<>(getContext(),android.R.layout.simple_spinner_item,data, new CrreList());
        spinnerExpert.setAdapter(adapter);
    }
}
