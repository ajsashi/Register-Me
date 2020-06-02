package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.SuccessCertificate;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    private List<SuccessCertificate.Certifiedregion> datalist;

    public void init(Context context, List<SuccessCertificate.Certifiedregion> datalist, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindData(datalist.get(position));
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView location;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location = itemView.findViewById(R.id.region);
            itemView.setOnClickListener(v -> {
                listener.onViewItemClick(datalist.get(getAdapterPosition()).getLocationid());
            });
        }


        public void bindData(SuccessCertificate.Certifiedregion certifiedregion) {
        location.setText(certifiedregion.getCertifiedregion());
        }
    }

    public interface OnItemClickListener {
        void onViewItemClick(int locationId);
            }
}
