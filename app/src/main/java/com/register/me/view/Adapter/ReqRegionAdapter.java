package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.ReqGeoRegion;

import java.util.List;

public class ReqRegionAdapter extends RecyclerView.Adapter<ReqRegionAdapter.ViewHolder> {

    private Context context;
    private List<ReqGeoRegion.Region> dataList;

    private OnIconClickListener listener;


    public void init(Context context, List<ReqGeoRegion.Region> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
        dataList = data;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.req_region_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReqGeoRegion.Region region = dataList.get(position);

        holder.region.setText(region.getRegion());
        holder.description.setText(region.getDescription());
        holder.reqBy.setText(region.getRequestedby());
        holder.status.setText(region.getIsAccepted());
        if(region.getIsAccepted().equals("Requested")){
            holder.accept.setVisibility(View.VISIBLE);
            holder.reject.setVisibility(View.VISIBLE);
        }else {
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView region;
        private TextView description;
        private TextView reqBy;
        private TextView status;
        private ImageView accept;
        private ImageView reject;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            region = itemView.findViewById(R.id.region);
            description = itemView.findViewById(R.id.description);
            reqBy = itemView.findViewById(R.id.req_by);
            status = itemView.findViewById(R.id.status);
            accept = itemView.findViewById(R.id.accpet_icon);
            reject = itemView.findViewById(R.id.cancel_icon);

            accept.setOnClickListener(v -> {
                listener.onAccept(dataList.get(getAdapterPosition()).getId());
            });
            reject.setOnClickListener(v -> {
                listener.onReject(dataList.get(getAdapterPosition()).getId());
            });
        }

    }

    public interface OnIconClickListener {
        void onAccept(Integer id);

        void onReject(Integer id);
    }


}
