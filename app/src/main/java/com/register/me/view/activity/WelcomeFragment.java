package com.register.me.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.register.me.APIs.MasterNetworkCall;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.MasterDash;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.WelcomePresenter;
import com.register.me.view.BaseActivity;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WelcomeFragment extends BaseFragment implements IFragment, Utils.UtilNetworkInterface, WelcomePresenter.IWelcome {

    @BindView(R.id.img_tab_one)
    ImageView tabOne;
    @BindView(R.id.img_tab_two)
    ImageView tabTwo;
    @BindView(R.id.img_tab_three)
    ImageView tabThree;
    @BindView(R.id.img_tab_four)
    ImageView tabFour;
    @BindView(R.id.client_dash)
    LinearLayout clientDash;
    @BindView(R.id.rre_dash)
    LinearLayout mRREDash;
    @BindView(R.id.crre_container)
    LinearLayout mCRREDash;
    @BindView(R.id.crre_dash)
    ScrollView dashCRRE;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;

    @Inject
    WelcomePresenter welcomePresenter;
    @Inject
    RRENetworkCall rreNetworkCall;
    @Inject
    MasterNetworkCall masterNetworkCall;
    @Inject
    Constants constants;
    private Integer stepFromResponse;
    private int userRole;
    private Observer<String> message = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(String s) {
            dismissProgress();
            if (s.contains("Failed to connect")) {
                welcomePresenter.isOnLine();
            } else {
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
    private Observer<RREApplication> applicationObserver = new Observer<RREApplication>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(RREApplication rreApplication) {
            dismissProgress();
            welcomePresenter.setApplicationData(rreApplication);
//            Toast.makeText(WelcomeActivity.this, rreApplication.getData().getComments().toString(), Toast.LENGTH_SHORT).show();
            /*questList = addProductPresenter.getRREApplication(rreApplication);
            buildUI();*/
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    private Observer<Integer> getStepObserver = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(Integer stepsResponse) {
            dismissProgress();
            stepFromResponse = stepsResponse;
            setUpRREHome();

        }

        @Override
        public void onError(Throwable e) {
            Log.d("Object", String.valueOf(e));

        }

        @Override
        public void onComplete() {

        }
    };
    private Observer<MasterDash> dashObserver = new Observer<MasterDash>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(MasterDash masterDash) {
            List<MasterDash.Dashboard> list = masterDash.getData().getDashboard();
            dismissProgress();
            setUpMasterCRREHome(list);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };

    public static IFragment newInstance() {
        return new WelcomeFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        /*byte[] test ={11,11,11,11,11};
        String t = new String(test);
        Log.d("trail data", t.toString());*/
        userRole = constants.getuserRole();

    }

    private void setUpRREHome() {
        mRREDash.setVisibility(View.VISIBLE);
        ArrayList<String> title = new ArrayList<>();
        title.add(" Application Submission ");
        title.add(" On - Line Interview ");
        title.add(" Application & Policy Training ");
        title.add(" Certificate ");
        mRREDash.removeAllViews();
        for (int i = 0; i < title.size(); i++) {

            View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.rre_welcome_item, mRREDash, false);

            ConstraintLayout item = inflatedView.findViewById(R.id.item);
            View topLine = inflatedView.findViewById(R.id.top_line);
            View bottomLine = inflatedView.findViewById(R.id.bottom_line);
            ImageView imageBtn = inflatedView.findViewById(R.id.image_btn);
            TextView txtTitle = inflatedView.findViewById(R.id.title);
            txtTitle.setText(title.get(i));
            int finalI = i;
            item.setOnClickListener(view -> {
                if (finalI + 1 <= stepFromResponse) {

                    if (title.get(finalI).equals(" Application Submission ")) {
                        triggerAPI();
                    }
                    final int tab = finalI + 1;
                    constants.setTAB(tab);
                    welcomePresenter.setTab(tab);
                    fragmentChannel.welcomeRedirect();
                } else {
                    Toast.makeText(getContext(), "Please complete your steps one by one", Toast.LENGTH_SHORT).show();
                }
            });
            if (i == 0) {
                topLine.setVisibility(View.GONE);
            } else if (i == title.size() - 1) {
                bottomLine.setVisibility(View.GONE);
            }
            if (i + 1 < stepFromResponse) {
                imageBtn.setImageResource(R.drawable.btn_green);
            } else if (i + 1 == stepFromResponse) {
                imageBtn.setImageResource(R.drawable.btn_blue);
            }

            mRREDash.addView(inflatedView);
        }
    }

    private void setUpCRREHome() {
        ArrayList<Integer> tile = new ArrayList<>();
        tile.add(R.drawable.my_active_auction);
        tile.add(R.drawable.auction_in_progress);
        tile.add(R.drawable.my_active_project);
        tile.add(R.drawable.my_complete_project);
        tile.add(R.drawable.success_story);
        tile.add(R.drawable.certificate_status);
        tile.add(R.drawable.personal);
        mCRREDash.removeAllViews();
        for (int i = 0; i < tile.size(); i++) {
            View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.image_item, mRREDash, false);
            ImageView tileImg = inflatedView.findViewById(R.id.tile);
            tileImg.setImageResource(tile.get(i));
            int finalI = i;
            tileImg.setOnClickListener(v -> {
                final int tab = finalI + 1;
                constants.setTAB(tab);
                welcomePresenter.setTab(tab);
                fragmentChannel.welcomeRedirect();
            });
            mCRREDash.addView(inflatedView);
        }
    }

    private void setUpMasterCRREHome(List<MasterDash.Dashboard> list) {
        /*ArrayList<String> mcList = new ArrayList<>();
        mcList.add("Client Details");
        mcList.add("RRE Details");
        mcList.add("Cerified RRE Details");
        mcList.add("Master RRE Details");
        mcList.add("Master Availability");
        mcList.add("Geographics Locations");
        mcList.add("My Active Projects");*/
        mCRREDash.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.master_crre_item, mRREDash, false);
            TextView textView = inflatedView.findViewById(R.id.txtTitle);
            textView.setText(list.get(i).getName());
            int finalI = i;
            textView.setOnClickListener(v -> {
                final int tab = finalI + 1;
                constants.setTAB(tab);
                welcomePresenter.setTab(tab);
                fragmentChannel.welcomeRedirect();
            });
            mCRREDash.addView(inflatedView);
        }
    }

    private void triggerAPI() {
        showProgress();
        rreNetworkCall.viewRREApplication(applicationObserver);
    }

    @OnClick(R.id.img_tab_one)
    public void onTabOneClick() {
        constants.setTAB(1);
        welcomePresenter.setTab(1);
        fragmentChannel.showAddProduct();

    }

    @OnClick(R.id.img_tab_two)
    public void onTabTwoClick() {
        constants.setTAB(2);
        welcomePresenter.setTab(2);
    }

    @OnClick(R.id.img_tab_three)
    public void onTabThreeClick() {
        constants.setTAB(3);
        welcomePresenter.setTab(3);
    }

    @OnClick(R.id.img_tab_four)
    public void onTabFourClick() {
        constants.setTAB(4);
        welcomePresenter.setTab(4);
        if (constants.getuserRole() == 0) {
            fragmentChannel.welcomeRedirect();
        }
    }

    @Override
    public void refreshNetwork() {
//        Toast.makeText(this, "Refresh clicked", Toast.LENGTH_SHORT).show();
        welcomePresenter.isOnLine();
        buildUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            fragmentChannel.setTitle("");
            welcomePresenter.init(getContext(), this);
            welcomePresenter.getUserProfile();
            userRole = constants.getuserRole();
            if (userRole != -1) {
                checkRole();
            }
        } catch (Exception e) {
           Log.e("Welcome Exception : ", e.getMessage());
        }
        //welcomePresenter.isOnLine();
    }

    @Override
    public void buildUI() {
        fragmentChannel.updateNavigation();
        checkRole();
    }

    private void checkRole() {
        switch (userRole) {
            case 0:
                clientDash.setVisibility(View.VISIBLE);
                break;
            case 1:
                rreNetworkCall.init(getContext(), message, this);
                showProgress();
                rreNetworkCall.getStepStatus(getStepObserver);
                break;
            case 2:
                dashCRRE.setVisibility(View.VISIBLE);
                setUpCRREHome();
                break;
            case 3:
                dashCRRE.setVisibility(View.VISIBLE);
                masterNetworkCall.init(getContext(), message, this);
                showProgress();
                masterNetworkCall.getDashBoardData(dashObserver);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + userRole);
        }
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public String getFragmentName() {
        return "WelcomeActivity";
    }
}
