package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.GeographicLocation;
import com.register.me.model.data.model.ReqGeoRegion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReqRegionAdapter extends RecyclerView.Adapter<ReqRegionAdapter.ViewHolder> {

    private Context context;
    private List<ReqGeoRegion.Region> dataList;

    private OnIconClickListener listener;
    private List<ReqGeoRegion.Region> initialList;


    public void init(Context context, List<ReqGeoRegion.Region> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
        initialList=new ArrayList<>();
        initialList.addAll(data);
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

        holder.region.setText(region.getRegion()!=null||!region.getRegion().isEmpty()? region.getRegion():"-");
        holder.description.setText(region.getDescription()!=null||!region.getDescription().isEmpty()? region.getDescription():"-");
        holder.reqBy.setText(region.getRequestedby()!=null||!region.getRequestedby().isEmpty()? region.getRequestedby():"-");
        holder.status.setText(region.getIsAccepted()!=null||!region.getRegion().isEmpty()? region.getRegion():"-");
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

    public Filter getFilter() {
        if(dataList.size()!=initialList.size()){
            dataList.clear();
            dataList.addAll(initialList);
        }
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ReqGeoRegion.Region> list = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    list.addAll(dataList);
                }else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ReqGeoRegion.Region item : dataList) {
                        if (item.getRegion().toLowerCase().contains(filterPattern)) {
                            list.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = list;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataList.clear();
                dataList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
        return filter;
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
