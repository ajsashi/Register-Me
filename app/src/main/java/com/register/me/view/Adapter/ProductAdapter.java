package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.ClientProductList;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.view.fragments.Master.MasterViewDetailFragment;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    private List<GetProductModel.Product> datalist;

    public void init(Context context, List<GetProductModel.Product> datalist, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
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



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout base;
        TextView productNumber;
        TextView productName;
        ImageView viewIcon;
        ImageView auctionIcon;
        ImageView sendIcon;
        ImageView editIcon;
        ImageView countryIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            base = itemView.findViewById(R.id.productHeader);
            productNumber = itemView.findViewById(R.id.pNumber);
            productName = itemView.findViewById(R.id.pName);
            viewIcon = itemView.findViewById(R.id.view_icon);
            auctionIcon = itemView.findViewById(R.id.auction_icon);
            /*sendIcon = itemView.findViewById(R.id.send_icon);*/
            editIcon = itemView.findViewById(R.id.edit_icon);
            countryIcon = itemView.findViewById(R.id.country_icon);

            viewIcon.setOnClickListener(this);
            auctionIcon.setOnClickListener(this);
            /*sendIcon.setOnClickListener(this);*/
            editIcon.setOnClickListener(this);

            countryIcon.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.view_icon:
                    listener.onViewIconClick(getAdapterPosition());
                    break;
                case R.id.auction_icon:
                    listener.onAuctionIconClick(getAdapterPosition());
                    break;
                /*case R.id.send_icon:
                    listener.onSendIconClick(getAdapterPosition());
                    break;*/
                case R.id.edit_icon:
                    listener.onEditIconClick(getAdapterPosition());
                    Toast.makeText(context, "Edit Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.country_icon:
                    listener.onCountryIconClick(getAdapterPosition());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + id);
            }
        }

        public void bindData(GetProductModel.Product product) {
            productName.setText(product.getProduct().getProductName());
            productNumber.setText(product.getProduct().getProductNumber());

            viewIcon.setVisibility(product.getIsView()? View.VISIBLE : View.GONE);
            auctionIcon.setVisibility(product.getIsinitiatebid()? View.VISIBLE : View.GONE);
         /*   sendIcon.setVisibility(product.getIsdirectassign()? View.VISIBLE : View.GONE);*/
            editIcon.setVisibility(product.getIsEdit()? View.VISIBLE : View.GONE);
//            List<GetProductModel.Project> project = product.getProject();

           countryIcon.setVisibility(product.getIsprojectavailable() ? View.VISIBLE : View.GONE);
        }
    }

    public interface OnItemClickListener {

        void onViewIconClick(int position);

        void onAuctionIconClick(int position);

    /*    void onSendIconClick(int adapterPosition);*/

        void onCountryIconClick(int adapterPosition);

        void onEditIconClick(int adapterPosition);
    }
}
