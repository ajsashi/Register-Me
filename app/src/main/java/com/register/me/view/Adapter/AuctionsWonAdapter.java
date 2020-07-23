package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.AuctionWon;

import java.util.List;

public class AuctionsWonAdapter extends RecyclerView.Adapter<AuctionsWonAdapter.ViewHolder> {

    private Context context;
    private List<AuctionWon.AuctionsWonData> dataList;

    private OnIconClickListener listener;


    public void init(Context context, List<AuctionWon.AuctionsWonData> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
        dataList = data;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auction_won_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AuctionWon.AuctionsWonData data = dataList.get(position);
        holder.txtProNo.setText(data.getProductNumber());
        holder.txtCountry.setText(data.getRegion());
        holder.txtCompDate.setText(data.getProjectCompletion());
        holder.txtClientName.setText(data.getClient());
        holder.txtRemainDays.setText(data.getRemaining());
        holder.progressPercent.setText(data.getStatus());
        String status = data.getStatus().replace("%","");
        holder.progressBar.setProgress(Integer.parseInt(status));

    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtProNo;
        private TextView txtCountry;
        private TextView txtCompDate;
        private TextView txtClientName;
        private TextView txtRemainDays;
        private TextView progressPercent;
        private ProgressBar progressBar;
        private ImageView viewIcon;



        ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProNo = itemView.findViewById(R.id.txt_pro_no);
            txtCountry = itemView.findViewById(R.id.txt_country);
            txtCompDate = itemView.findViewById(R.id.txt_comp_date);
            txtClientName = itemView.findViewById(R.id.txt_client_name);
            txtRemainDays = itemView.findViewById(R.id.txt_remain_days);
            progressPercent = itemView.findViewById(R.id.progress_percent);
            progressBar = itemView.findViewById(R.id.progressBar);
            viewIcon = itemView.findViewById(R.id.icon_view);
            viewIcon.setOnClickListener(v -> listener.onViewIconClick(dataList.get(getAdapterPosition()).getProductId()));

        }

    }

    public interface OnIconClickListener {
     void onViewIconClick(Integer productId);
    }


}
