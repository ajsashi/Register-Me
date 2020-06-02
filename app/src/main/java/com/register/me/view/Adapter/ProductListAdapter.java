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

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener listener;
    private List<ClientProductList.Productdetail> datalist;

    public void init(Context context, List<ClientProductList.Productdetail> datalist, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
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
            viewIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onViewIconClick(getAdapterPosition());
                }
            });

        }



        public void bindData(ClientProductList.Productdetail product) {
            productName.setText(product.getName());
            productNumber.setText(product.getProductNumber());

           /* viewIcon.setVisibility(product.getIsView()? View.VISIBLE : View.GONE);
            auctionIcon.setVisibility(product.getIsinitiatebid()? View.VISIBLE : View.GONE);
         *//*   sendIcon.setVisibility(product.getIsdirectassign()? View.VISIBLE : View.GONE);*//*
            editIcon.setVisibility(product.getIsEdit()? View.VISIBLE : View.GONE);
//            List<GetProductModel.Project> project = product.getProject();

           countryIcon.setVisibility(product.getIsprojectavailable() ? View.VISIBLE : View.GONE);*/
        }
    }

    public interface OnItemClickListener {

        void onViewIconClick(int position);

    }
}
