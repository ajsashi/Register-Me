package com.register.me.view.fragments.Client.portfolio.directAssignment;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.register.me.R;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.InitiateRegistrationPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */
public class CRREDirectFragment extends BaseFragment implements IFragment, Utils.UtilDateTimeInterface {
    private static final String FRAGMENT_NAME = "InitiateProductRegistration";

    @BindView(R.id.txtDate)
    TextView txtDate;
    @Inject
    InitiateRegistrationPresenter addProductPresenter;
    @Inject
    Utils util;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        fragmentChannel.setTitle(getResources().getString(R.string.crre_direct));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }



    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_direct_assignment;
    }

    public static CRREDirectFragment newInstance() {
        return new CRREDirectFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.txtDate)
    public  void onDateClicked() {
        util.showCalendar(getContext(),this,0);
        Toast.makeText(getContext(), "Date Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(long timeInMillis) {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        txtDate.setText(formatter.format(timeInMillis));
    }

    @Override
    public void onTimeSet(Integer currentHour, Integer currentMinute) {

    }
}
