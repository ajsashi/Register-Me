package com.register.me.view.Adapter;

import android.content.Context;
import android.text.InputFilter;
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

import java.util.ArrayList;
import java.util.List;

public class GeoListAdapter extends RecyclerView.Adapter<GeoListAdapter.ViewHolder> {

    private Context context;
    private List<GeographicLocation.Location> dataList;
    private List<GeographicLocation.Location> initialList;

    private OnIconClickListener listener;


    public void init(Context context, List<GeographicLocation.Location> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
        initialList=new ArrayList<>();
        initialList.addAll(data);
        dataList = data;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.geo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GeographicLocation.Location location = dataList.get(position);
        holder.location.setText(location.getGeographiclocation());
        holder.status.setText(location.getStatus());

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
                List<GeographicLocation.Location> list = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    list.addAll(dataList);
                }else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (GeographicLocation.Location item : dataList) {
                        if (item.getGeographiclocation().toLowerCase().contains(filterPattern)) {
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

        private TextView location;
        private TextView status;
        private ImageView edit;
        private ImageView delete;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.region);
            status = itemView.findViewById(R.id.status);
            edit = itemView.findViewById(R.id.accpet_icon);
            delete = itemView.findViewById(R.id.cancel_icon);
            edit.setOnClickListener(v->{
                final GeographicLocation.Location location = dataList.get(getAdapterPosition());
                listener.onEdit(location.getId(),location.getGeographiclocation());
            });
            delete.setOnClickListener(v->{
                listener.onCancel(dataList.get(getAdapterPosition()).getId());
            });
        }

    }

    public interface OnIconClickListener {
        void onEdit(Integer id, String geographiclocation);

        void onCancel(Integer id);
    }


}
