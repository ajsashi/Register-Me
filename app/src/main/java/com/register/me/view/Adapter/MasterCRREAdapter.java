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
import com.register.me.model.data.model.CertifiedRREList;
import com.register.me.model.data.model.Client;
import com.register.me.model.data.model.McrreList;
import com.register.me.model.data.model.RRE;

import java.util.List;

public class MasterCRREAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CASE_CLIENT = 1;
    private static final int CASE_RRE = 2;
    private static final int CASE_CRRE = 3;
    private static final int CASE_MCRRE = 4;
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
        if (viewType == CASE_CLIENT||viewType==CASE_MCRRE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
            return new ClientViewHolder(view);
        } else if (viewType == CASE_RRE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rre_item, parent, false);
            return new RREViewHolder(view);
        }else if(viewType == CASE_CRRE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crre_item, parent, false);
            return new CRREViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int itemViewType = holder.getItemViewType();
        switch (itemViewType){
            case CASE_CLIENT:
                Client.Clientdetail data = (Client.Clientdetail) dataList.get(position);
                ((ClientViewHolder)holder).bindClientData(data);
                break;
            case CASE_RRE:
                RRE.RREdetail rreData = (RRE.RREdetail) dataList.get(position);
                ((RREViewHolder)holder).bindRREData(rreData);
                break;
            case CASE_CRRE:
                CertifiedRREList.Certifiedrredetail crreData = (CertifiedRREList.Certifiedrredetail) dataList.get(position);
                ((CRREViewHolder)holder).bindCRREData(crreData);
                break;
            case CASE_MCRRE:
                McrreList.Mastercrre mcrreData = (McrreList.Mastercrre)dataList.get(position);
                ((ClientViewHolder)holder).bindMcrreData(mcrreData);
                break;

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
        }else if(o instanceof CertifiedRREList.Certifiedrredetail){
            return CASE_CRRE;
        }else if(o instanceof McrreList.Mastercrre){
            return CASE_MCRRE;
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

            txtClientName = itemView.findViewById(R.id.txt_crre_name);
            txtClientMail = itemView.findViewById(R.id.txt_crre_mail);
            txtClientStatus = itemView.findViewById(R.id.txt_crre_regions);
            txtClientMobile = itemView.findViewById(R.id.txt_crre_mobile);
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

        public void bindMcrreData(McrreList.Mastercrre mcrreData) {
            txtClientName.setText(mcrreData.getFullName());
            txtClientMail.setText(mcrreData.getEmail());
            txtClientMobile.setText(mcrreData.getCellphone());
            txtClientStatus.setText("IsAdmin : "+mcrreData.getIsAdmin());
            viewIcon.setVisibility(View.GONE);
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


    class CRREViewHolder extends RecyclerView.ViewHolder {

        TextView txt_crre_name;
        TextView txt_crre_mail;
        TextView txt_crre_regions;
        TextView txt_crre_mobile;
        ImageView viewIcon;


        CRREViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_crre_name = itemView.findViewById(R.id.txt_crre_name);
            txt_crre_mail = itemView.findViewById(R.id.txt_crre_mail);
            txt_crre_regions = itemView.findViewById(R.id.txt_crre_regions);
            txt_crre_mobile = itemView.findViewById(R.id.txt_crre_mobile);
            viewIcon = itemView.findViewById(R.id.icon_view);
        }

        public void bindCRREData(CertifiedRREList.Certifiedrredetail data) {
            txt_crre_name.setText(data.getFullName()==null? " - ": data.getFullName());
            txt_crre_mail.setText(data.getEmail()==null? " - " : data.getEmail());
            txt_crre_regions.setText(data.getTelephone() == null? " - ":data.getTelephone());
            txt_crre_mobile.setText(data.getCertifiedCountry() == null? " - ": data.getCertifiedCountry());
            viewIcon.setOnClickListener(v ->{
                listener.onViewClick(data.getId());
            });
        }
    }
    public interface OnIconClickListener {
        void onViewClick(Integer id);

        void onViewRREClick(RRE.RREdetail data);
    }


}
