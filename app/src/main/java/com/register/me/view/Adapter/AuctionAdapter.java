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
import com.register.me.model.data.model.ActiveAuction;

import java.util.List;

public class AuctionAdapter extends RecyclerView.Adapter<AuctionAdapter.ViewHolder> {

    private Context context;
    private List<Object> dataList;

    private int ACTIVE_AUCTION =001;
    private int READY_TO_BID =002;
    private OnIconClickListener listener;


    public void init(Context context, List<Object> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
        dataList =data;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.auction_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder.getItemViewType() == ACTIVE_AUCTION){
            holder.bindAuction((ActiveAuction.Auctionsprogress)dataList.get(position));
        }else if(holder.getItemViewType() == READY_TO_BID){
            holder.bindBidToReady((ActiveAuction.Bidsreadytoevaluate_)dataList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (dataList.get(position) instanceof ActiveAuction.Auctionsprogress) {
            return ACTIVE_AUCTION;
        }else if(dataList.get(position) instanceof ActiveAuction.Bidsreadytoevaluate_) {
            return READY_TO_BID;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView country;
        ImageView viewIcon;
        ImageView directAssignIcon;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.pName);
            country = itemView.findViewById(R.id.pCountry);
            viewIcon = itemView.findViewById(R.id.view_icon);
            directAssignIcon = itemView.findViewById(R.id.expand_icon);
        }

        void bindAuction(ActiveAuction.Auctionsprogress auctionsprogress) {
            name.setText(auctionsprogress.getProductName());
            country.setText(auctionsprogress.getRegion());
            viewIcon.setOnClickListener(view -> listener.onViewClick(getAdapterPosition()));
            directAssignIcon.setOnClickListener(view -> listener.onDirectAssignmentClick(getAdapterPosition()));
        }

        void bindBidToReady(ActiveAuction.Bidsreadytoevaluate_ bidsReadyToEvaluate) {
            name.setText(bidsReadyToEvaluate.getProductName().trim());
            country.setText(bidsReadyToEvaluate.getRegion().trim());
            viewIcon.setOnClickListener(view -> listener.onViewClick(getAdapterPosition()));
            directAssignIcon.setOnClickListener(view -> listener.onDirectAssignmentClick(getAdapterPosition()));
        }
    }

    public interface OnIconClickListener{
        void onViewClick(int adapterPosition);
        void onDirectAssignmentClick(int adapterPosition);
    }


}
