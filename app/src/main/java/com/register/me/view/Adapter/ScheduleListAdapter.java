package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.ScheduleList;

import java.util.List;

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private Context context;
    private List<ScheduleList.Schedule> dataList;

    private OnIconClickListener listener;


    public void init(Context context, List<ScheduleList.Schedule> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
        dataList = data;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ScheduleList.Schedule schedule = dataList.get(position);
        holder.name.setText(schedule.getFullName());
        String availableDate = schedule.getAvailableDate();
        String availableStartTime = schedule.getAvailableStartTime();
        String availableEndTime = schedule.getAvailableEndTime();
        holder.dateNTime.setText(availableDate);
        holder.st_time.setText(availableStartTime);
        holder.et_time.setText(availableEndTime);

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView dateNTime;
        private TextView st_time;
        private TextView et_time;
        private CardView approve;
        private CardView cancel;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            dateNTime = itemView.findViewById(R.id.date_n_time);
            st_time = itemView.findViewById(R.id.st_time);
            et_time = itemView.findViewById(R.id.et_time);
            approve = itemView.findViewById(R.id.approve);
            cancel = itemView.findViewById(R.id.cancel_icon);
            final ScheduleList.Schedule[] item = new ScheduleList.Schedule[1];
            approve.setOnClickListener(v -> {
                item[0] = dataList.get(getAdapterPosition());
                listener.onApprove(item[0].getId());
            });
            cancel.setOnClickListener(v -> {
                item[0] = dataList.get(getAdapterPosition());
                listener.onCancel(item[0].getId());
            });
        }

    }

    public interface OnIconClickListener {
        void onApprove(Integer id);

        void onCancel(Integer id);
    }


}
