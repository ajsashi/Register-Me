package com.register.me.view.fragments.RRE.onlineInterview;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.TimeSchedule;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.OnlineInterviewPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jennifer - AIT on 14-02-2020PM 01:51.
 */
public class OnlineInterviewFragment extends BaseFragment implements IFragment, OnlineInterviewPresenter.IOnlineInterview, Utils.UtilNetworkInterface {

    @BindView(R.id.booked_container)
    LinearLayout bookedContainer;
    @BindView(R.id.available_container)
    LinearLayout availableContainer;
    @BindView(R.id.noContent)
    LinearLayout noContent;
    @BindView(R.id.avl_noContent)
    LinearLayout avlNocontent;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @Inject
    RRENetworkCall rreNetworkCall;
    @Inject
    OnlineInterviewPresenter presenter;

    @Inject
    Utils utils;
    private Observer<String> message = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            dismissProgress();
            if (utils.isOnline(getContext())) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();}
            rreNetworkCall.clearDisposable();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    private TimeSchedule timeSchedule;
    private Observer<ResponseData> submitCancelObserver;
    private Observer<TimeSchedule> scheduleObserver;
    public static IFragment newInstance() {
        return new OnlineInterviewFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online_interview;
    }

    @Override
    public String getFragmentName() {
        return "OnlineInterview";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        rreNetworkCall.init(getContext(), message, this);
        scheduleObserver = new Observer<TimeSchedule>() {


            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TimeSchedule time_Schedule) {
                dismissProgress();
                timeSchedule = time_Schedule;
                buildUI();
                rreNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        submitCancelObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                showProgress();
                rreNetworkCall.getAvailableTimeSchedule(scheduleObserver);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };


    }

    private void buildUI() {
        displayCheck();
        buildBookedUI();
        buildAvailableUI();
    }

    private void buildBookedUI() {

        List<TimeSchedule.Schedule> schedules = timeSchedule.getData().getSchedules();
        if (schedules.size() > 0) {
            bookedContainer.removeAllViews();
            for (TimeSchedule.Schedule item : schedules) {
                View view = presenter.getBookedView(item, bookedContainer);
                bookedContainer.addView(view);
            }
        }
    }


    private void buildAvailableUI() {
        List<TimeSchedule.Time> time = timeSchedule.getData().getTime();
        if (time.size() > 0) {
            availableContainer.removeAllViews();
            for (TimeSchedule.Time item : time) {
                View view = presenter.getAvailableView(item, availableContainer);
                availableContainer.addView(view);
            }
        }
    }


    private void displayCheck() {
        if (timeSchedule.getData().getSchedules().size() == 0) {
            noContent.setVisibility(View.VISIBLE);
            bookedContainer.setVisibility(View.GONE);
        } else {
            noContent.setVisibility(View.GONE);
            bookedContainer.setVisibility(View.VISIBLE);
        }

        if (timeSchedule.getData().getTime().size() == 0) {
            avlNocontent.setVisibility(View.VISIBLE);
            availableContainer.setVisibility(View.GONE);
        } else {
            avlNocontent.setVisibility(View.GONE);
            availableContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void triggerApi(JsonObject object, CardView clickView) {
        showProgress();
        rreNetworkCall.submitCancelSlot(object, submitCancelObserver);
    }


    @Override
    public void onResume() {
        super.onResume();
        showProgress();
        fragmentChannel.setTitle("Online Interview");
        rreNetworkCall.getAvailableTimeSchedule(scheduleObserver);
    }

    @Override
    public void refreshNetwork() {
        if (timeSchedule == null) {
            onResume();
        } else {

            rreNetworkCall.checkNetStatus();
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
    /*    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.layout_date)
    public void onClickDate() {
        utils.showCalendar(getContext(), this, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.layout_time)
    public void onClickTime() {
        utils.showCalendar(getContext(), this, 1);
    }*/

   /* @Override
    public void onDateSet(long timeInMillis) {
        txtDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(timeInMillis));
    }

    @Override
    public void onTimeSet(Integer currentHour, Integer currentMinute) {
        if (currentHour > 12) {
            txtTime.setText("" + (currentHour-12) + " : " + currentMinute+" "+"P.M");
        } else {
            txtTime.setText("" + currentHour + " : " + currentMinute+" "+"A.M");
        }
    }*/


}
