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
import com.register.me.model.data.model.Client;
import com.register.me.model.data.model.RRE;
import com.register.me.view.Adapter.MasterCRREAdapter.RREViewHolder;

import java.util.List;

public class MasterCRREAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CASE_CLIENT = 1;
    private static final int CASE_RRE = 2;
    private Context context;
    private List<Object> dataList;

    private OnIconClickListener listener;


    public void init(Context context, List<Object> data, OnIconClickListener listener) {
        this.context = context;
        this.listener = listener;
        dataList = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == CASE_CLIENT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
            return new ClientViewHolder(view);
        } else if (viewType == CASE_RRE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rre_item, parent, false);
            return new RREViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == CASE_CLIENT) {
            Client.Clientdetail data = (Client.Clientdetail) dataList.get(position);
            ((ClientViewHolder)holder).bindClientData(data);

        } else {
            RRE.RREdetail data = (RRE.RREdetail) dataList.get(position);
            ((RREViewHolder)holder).bindRREData(data);
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object o = dataList.get(position);
        if (o instanceof Client.Clientdetail) {
            return CASE_CLIENT;
        } else if (o instanceof RRE.RREdetail) {
            return CASE_RRE;
        }
        return -1;
    }

    class ClientViewHolder extends RecyclerView.ViewHolder {

        TextView txtClientName;
        TextView txtClientMail;
        TextView txtClientStatus;
        TextView txtClientMobile;
        ImageView viewIcon;


        ClientViewHolder(@NonNull View itemView) {
            super(itemView);

            txtClientName = itemView.findViewById(R.id.txt_client_name);
            txtClientMail = itemView.findViewById(R.id.txt_client_mail);
            txtClientStatus = itemView.findViewById(R.id.txt_client_status);
            txtClientMobile = itemView.findViewById(R.id.txt_client_mobile);
            viewIcon = itemView.findViewById(R.id.icon_view);
        }

        public void bindClientData(Client.Clientdetail data) {
           txtClientName.setText(data.getUsername());
           txtClientMail.setText(data.getEmail());
           txtClientMobile.setText(data.getCellPhone());
           txtClientStatus.setText(data.getActive());
           viewIcon.setOnClickListener(v ->{
               listener.onViewClick(data.getId());
           });
        }
    }

    class RREViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_rre_name;
        private ImageView img_comp_application;
        private ImageView img_interview;
        private ImageView img_app_train;
        private ImageView img_certificate;
        private ImageView icon_view;



        RREViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_rre_name = itemView.findViewById(R.id.txt_rre_name);
            img_comp_application = itemView.findViewById(R.id.img_comp_application);
            img_interview = itemView.findViewById(R.id.img_interview);
            img_app_train = itemView.findViewById(R.id.img_app_train);
            img_certificate = itemView.findViewById(R.id.img_certificate);
            icon_view = itemView.findViewById(R.id.icon_view);

        }

        public void bindRREData(RRE.RREdetail data) {
           txt_rre_name.setText(data.getRegistrationExpert());
            if(data.getCompleteApplication().equals("Yes")){
                img_comp_application.setImageResource(R.drawable.checked);
            }else {
                img_comp_application.setImageResource(R.drawable.unchecked);
            }
            if(data.getInterview().equals("Yes")){
               img_interview.setImageResource(R.drawable.checked);
            }else {
              img_interview.setImageResource(R.drawable.unchecked);
            }
            if(data.getAppTraining().equals("Yes")){
               img_app_train.setImageResource(R.drawable.checked);
            }else {
                img_app_train.setImageResource(R.drawable.unchecked);
            }
            if(data.getCertificateIssue().equals("Yes")){
                img_certificate.setImageResource(R.drawable.checked);
            }else {
                img_certificate.setImageResource(R.drawable.unchecked);
            }
            icon_view.setOnClickListener(v ->{
                listener.onViewRREClick(data);
            });
        }
    }

    public interface OnIconClickListener {
        void onViewClick(Integer id);

        void onViewRREClick(RRE.RREdetail data);
    }


}
