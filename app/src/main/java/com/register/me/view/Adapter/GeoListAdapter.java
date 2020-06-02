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
import com.register.me.model.data.model.GeographicLocation;

import java.util.List;

public class GeoListAdapter extends RecyclerView.Adapter<GeoListAdapter.ViewHolder> {

    private Context context;
    private List<GeographicLocation.Location> dataList;

    private OnIconClickListener listener;


    public void init(Context context, List<GeographicLocation.Location> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
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
            delete = itemView.findViewById(R.id.delete);
        }

    }

    public interface OnIconClickListener {
        void onApprove(Integer id);

        void onCancel(Integer id);
    }


}
