package com.register.me.view.fragments.Master;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.model.TimeSlotKV;
import com.register.me.presenter.AddAvailabilityPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddAvailabilityFragment extends BaseFragment implements IFragment, AddAvailabilityPresenter.AvailabilityListener {

    @BindView(R.id.p_Bar)
    ConstraintLayout pLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBar;
    @BindView(R.id.dateView)
    TextView dateView;
    @BindView(R.id.timeslot_container)
    LinearLayout containerTS;
    /*  @BindView(R.id.submit_btn)
      CardView submitBtn;*/
    ArrayList<TimeSlotKV> FromToTime;
    String selectedDate;


    @Inject
    AddAvailabilityPresenter presenter;
    private int mYear = -1;
    private int mMonth = -1;
    private int mDay = -1;
    private int mHour = -1;
    private int mMinute = -1;
    private int removedIndexCount = -1;

    public static IFragment newInstance() {
        return new AddAvailabilityFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_availablity;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FromToTime = new ArrayList<>();

        inflateLayout();
    }

    private void inflateLayout() {
        containerTS.removeAllViews();
        containerTS.addView(addItem(true));
    }

    /*
     * inflates new item to the time slot*/
    private View addItem(boolean isFirst) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.time_slot_item, containerTS, false);
        TextView from_time = view.findViewById(R.id.from_time);
        TextView to_time = view.findViewById(R.id.to_time);
        TextView cardText = view.findViewById(R.id.card_text);
        CardView card_add = view.findViewById(R.id.card_add);
        /*
         * Check condition to display button text for first button and the other buttons*/
        if (isFirst) {
            cardText.setText("Add");
            card_add.setCardBackgroundColor(getContext().getColor(R.color.green));
        } else {
            cardText.setText("Remove");
            card_add.setCardBackgroundColor(getContext().getColor(R.color.red));
        }
        int ct = containerTS.getChildCount();

        FromToTime.add(new TimeSlotKV(ct, "", ""));

        from_time.setTag(R.id.index, ct);
        from_time.setOnClickListener(v -> {
            from_time.setTag(R.id.usecase, "from");
            callTimePicker(from_time, "");
        });
        to_time.setTag(R.id.index, ct);
        to_time.setOnClickListener(v -> {
            to_time.setTag(R.id.usecase, "to");
            callTimePicker(to_time, "");
        });

        card_add.setTag(view);
        card_add.setOnClickListener(v -> {
            if (cardText.getText().equals("Add")) {
                containerTS.addView(addItem(false));
            } else {
                containerTS.removeView((View) card_add.getTag());
                removedIndexCount = removedIndexCount + 1;
                final int index = ct - removedIndexCount;
                FromToTime.remove(index);
                if (FromToTime.size() == 1) {
                    removedIndexCount = -1;
                }

                Toast.makeText(getContext(), "Clicked Remove", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /*Opens Time Picker dialog*/
    private void callTimePicker(TextView v, String st) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        Time time = new Time(hourOfDay, minute, 0);

                        //little h uses 12 hour format and big H uses 24 hour format
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("a : h : mm");

                        //format takes in a Date, and Time is a sublcass of Date
                        String s = simpleDateFormat.format(time);
//                        v.setText(s+hourOfDay + ":" + minute);
                        SimpleDateFormat arrayFormat = new SimpleDateFormat("HH:mm:ss");
                        String arrayFormatDate = arrayFormat.format(time);
                        int index = (int) v.getTag(R.id.index);
                        String callCase = (String) v.getTag(R.id.usecase);
                        final int index1 = index - removedIndexCount;
                        if (callCase.equals("from")) {

                            TimeSlotKV ft = null;
                            for (TimeSlotKV it : FromToTime) {
                                if (it.getIndex() == index) {
                                    ft = it;
                                }
                            }
                            //int ft = FromTime.indexOf(index);
                            // FromTime.set(ft.getIndex(),new TimeSlotKV(ft,arrayFormatDate));
                            FromToTime.get(ft.getIndex()).setFromTime(arrayFormatDate);
                        } else {
                            TimeSlotKV tt = null;
                            for (TimeSlotKV it : FromToTime) {
                                if (it.getIndex() == index) {
                                    tt = it;
                                }
                            }
                            //int ft = FromTime.indexOf(index);
                            // FromTime.set(ft.getIndex(),new TimeSlotKV(ft,arrayFormatDate));
                            FromToTime.get(tt.getIndex()).setToTime(arrayFormatDate);
                        }
                        v.setText(st + s);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    @OnClick(R.id.dateView)
    public void onDateViewClick() {

        final Calendar c = Calendar.getInstance();
        if (mYear == -1) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }

        /*Opens Date Picker dialog*/
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        dateView.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" + ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" + year);
                        mDay = view.getDayOfMonth();
                        mMonth = view.getMonth();
                        mYear = view.getYear();
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(mYear, mMonth, mDay);

                        SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yyyy");
                        selectedDate = sdf.format(calendar.getTime());

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @OnClick(R.id.submit_btn)
    public void onSubmitClick() {
        presenter.submitApi(selectedDate, FromToTime);
    }

    @Override
    public String getFragmentName() {
        return "AddAvailabilityFragment";
    }

    @Override
    public void showMessage(String s) {
        KToast.customColorToast((Activity) getContext(), s, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
        fragmentChannel.popUp();
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

}
