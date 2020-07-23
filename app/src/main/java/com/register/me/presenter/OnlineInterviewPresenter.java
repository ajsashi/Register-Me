package com.register.me.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.google.gson.JsonObject;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.TimeSchedule;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

public class OnlineInterviewPresenter {
    private Context context;
    @Inject
    Constants constants;
    @Inject
    CacheRepo repo;
    private IOnlineInterview listener;

    public void init(Context context, IOnlineInterview listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
    }


    public View getBookedView(TimeSchedule.Schedule item, LinearLayout container) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_item, container, false);
        TextView nameCRRE = view.findViewById(R.id.crre_name);
        TextView dnt = view.findViewById(R.id.date_n_time);
        TextView status = view.findViewById(R.id.status);
        CardView clickView = view.findViewById(R.id.clickView);

        nameCRRE.setText(item.getCrre());
        dnt.setText(item.getDate());
        String state = item.getStatus();
        if (state.equals("Pending")) {
            state = "Cancel slot";
        }else if (state.equals("Cancelled")) {
            clickView.setCardBackgroundColor(context.getResources().getColor(R.color.darker_gray_color));}


        String finalState = state;
        clickView.setOnClickListener(V -> {
            if (finalState.equals("Cancelled")) {
                clickView.setClickable(false);
            } else {
                clickView.setClickable(false);
                clickView.setEnabled(false);
                JsonObject object = new JsonObject();
                object.addProperty("id", item.getTimeid());
                object.addProperty("action", "cancel");

                listener.triggerApi(object,clickView);

            }
        });
        status.setText(state);
        return view;
    }

    public View getAvailableView(TimeSchedule.Time item, LinearLayout container) {
        View view = LayoutInflater.from(context).inflate(R.layout.schedule_item, container, false);
        TextView nameCRRE = view.findViewById(R.id.crre_name);
        TextView dnt = view.findViewById(R.id.date_n_time);
        CardView clickView = view.findViewById(R.id.clickView);
        nameCRRE.setText(item.getName());
        dnt.setText(item.getDate());
        clickView.setOnClickListener(v -> {
            clickView.setClickable(false);
            clickView.setEnabled(false);
            JsonObject object = new JsonObject();
            object.addProperty("id", item.getAvailableid());
            object.addProperty("action", "submit");
            listener.triggerApi(object, clickView);
        });
        return view;
    }

    public interface IOnlineInterview {
        void triggerApi(JsonObject object, CardView clickView);
    }
}
