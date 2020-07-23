package com.register.me.view.fragments.Master;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.ScheduleList;
import com.register.me.presenter.MasterAvailabilityPresenter;
import com.register.me.presenter.MasterAvailabilityPresenter.MasterAvailabilityListener;
import com.register.me.view.Adapter.ScheduleListAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MasterAvailabilityFragment extends BaseFragment implements IFragment, MasterAvailabilityListener, ScheduleListAdapter.OnIconClickListener {

    @BindView(R.id.p_Bar)
    ConstraintLayout pLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.no_content_layout)
    LinearLayout no_content_layout;
    @BindView(R.id.add_availability)
    ImageView addAvailability;


    @Inject
    MasterAvailabilityPresenter presenter;
    private Context context;
    @Inject
    ScheduleListAdapter adapter;


    public static IFragment newInstance() {
        return new MasterAvailabilityFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_master_availability;
    }

    @Override
    public String getFragmentName() {
        return "Master Availability";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        context = getContext();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getInterviewList();
        addAvailability.setOnClickListener(v -> {
            fragmentChannel.showAddAvailabilityFragment();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("Master Availability");
    }

    private void setAdapter(List<ScheduleList.Schedule> schedules) {
        recyclerView.setVisibility(View.VISIBLE);
        adapter.init(context, schedules, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void updateScheduleList(List<ScheduleList.Schedule> schedules) {
        if (schedules.size() > 0) {
            setAdapter(schedules);
            no_content_layout.setVisibility(View.GONE);
        } else {
            no_content_layout.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void showMessage(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
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
        presenter.approveSchedule(id);
    }

    @Override
    public void onCancel(Integer id) {
        presenter.cancelSchedule(id);
    }
}
