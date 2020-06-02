package com.register.me.view.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.MyActiveAuction;
import com.register.me.model.data.model.MyAuctionInProgress;
import com.register.me.view.fragments.CRRE.MyActiveAuctionsFragment;

import java.util.List;

public class CrreAuctionAdapter extends RecyclerView.Adapter<CrreAuctionAdapter.ViewHolder> {

    private static final int TYPE_ACTIVE = 1;
    private static final int TYPE_IN_PROGRESS = 2;
    private List<MyActiveAuction.ActiveBiddingdetail> dataList = null;
    private OnItemClickListener listener;
    private List<MyAuctionInProgress.BiddingProgressDetail> inProgressList = null;

    public void init(List<MyActiveAuction.ActiveBiddingdetail> activeBiddingdetails, OnItemClickListener listener) {
        dataList = activeBiddingdetails;
        this.listener = listener;
    }

    public void subInit(List<MyAuctionInProgress.BiddingProgressDetail> biddingProgressDetails, OnItemClickListener listener) {
        inProgressList = biddingProgressDetails;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View infView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_auction_item, parent, false);
        return new ViewHolder(infView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
        if(itemViewType == TYPE_ACTIVE) {
            MyActiveAuction.ActiveBiddingdetail activeBiddingdetail = dataList.get(position);
            holder.txt_product.setText(activeBiddingdetail.getProductName());
            holder.txt_country.setText(activeBiddingdetail.getRegion());
            holder.view_icon.setOnClickListener(v -> {
                listener.onViewClick(activeBiddingdetail.getProductId(), activeBiddingdetail.getProjectId());
            });
        }else if(holder.getItemViewType() == TYPE_IN_PROGRESS){
            MyAuctionInProgress.BiddingProgressDetail biddingProgressDetail = inProgressList.get(position);
            holder.txt_product.setText(biddingProgressDetail.getProductName());
            holder.txt_country.setText(biddingProgressDetail.getRegion());
            holder.view_icon.setOnClickListener(v -> {
                listener.onViewClick(biddingProgressDetail.getProductId(), biddingProgressDetail.getProjectId());
            });
        }
    }

    @Override
    public int getItemCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return inProgressList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList!=null){
            return TYPE_ACTIVE;
        }
        return TYPE_IN_PROGRESS;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_product;
        TextView txt_country;
        ImageView view_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_product = itemView.findViewById(R.id.txt_product);
            txt_country = itemView.findViewById(R.id.txt_country);
            view_icon = itemView.findViewById(R.id.view_icon);


        }
    }

    public interface OnItemClickListener {
        void onViewClick(Integer productId, Integer projectId);
    }
}
