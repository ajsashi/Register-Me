package com.register.me.view.fragments.Master;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.register.me.APIs.MasterNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.CertificateStatus;
import com.register.me.model.data.model.ClientProductList;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.UserInfo;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.MasterViewDetailPresenter;
import com.register.me.view.Adapter.ProductListAdapter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MasterViewDetailFragment extends BaseFragment implements IFragment, Utils.UtilNetworkInterface, ProductListAdapter.OnItemClickListener, Utils.UtilAlertInterface {

    private static ArrayList<KeyValue> processList;
    @BindView(R.id.profile_container)
    LinearLayout profileContainer;
    @BindView(R.id.profile_scroll)
    ScrollView profileScroll;
    @BindView(R.id.product_recycle)
    RecyclerView productRecycle;
    @BindView(R.id.layout_product)
    ConstraintLayout layoutProductdetail;
    @BindView(R.id.layout_certificate_process)
    ConstraintLayout layoutCertificateProcess;
    @BindView(R.id.certificate_data)
    ConstraintLayout certificateData;
    @BindView(R.id.userInfo_drop)
    ImageView userDrop;
    @BindView(R.id.product_drop)
    ImageView productDrop;
    @BindView(R.id.header_tv)
    TextView headerTv;
    @BindView(R.id.apl_stat_lay)
    CardView apl_stat_lay;
    @BindView(R.id.application_status)
    TextView application_status;
    @BindView(R.id.interview_stat_lay)
    CardView interview_stat_lay;
    @BindView(R.id.interview_status)
    TextView interview_status;
    @BindView(R.id.policy_stat_lay)
    CardView policy_stat_lay;
    @BindView(R.id.app_policy_status)
    TextView app_policy_status;
    @BindView(R.id.certificate_stat_lay)
    CardView certificate_stat_lay;
    @BindView(R.id.certificate_status)
    TextView certificate_status;
    private static Integer Screen;
    private static Integer Id;
    private static String app_Id;
    @Inject
    MasterNetworkCall masterNetworkCall;
    @Inject
    MasterViewDetailPresenter presenter;
    @Inject
    Constants constants;
    @Inject
    ProductListAdapter adapter;
    @Inject
    Utils utils;
    private Observer<String> message;
    private Observer<UserInfo> userObserver;
    private Observer<ClientProductList> listObserver;
    private Observer<CertificateStatus> statusObserver;
    private Observer<ResponseData> applicationObserver;


    public static IFragment newInstance(Integer screen, Integer id, String appID, ArrayList<KeyValue> list) {
        Screen = screen;
        Id = id;
        processList = list;
        app_Id = appID;
        return new MasterViewDetailFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_master_view_details;
    }

    @Override
    public String getFragmentName() {
        return "MasterViewDetailFragment";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext());
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
//                masterNetworkCall.clearDisposable();
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        userObserver = new Observer<UserInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserInfo userInfo) {

                ArrayList<KeyValue> userData = presenter.getUserDetails(userInfo);

                if (Screen == 1) {
                    masterNetworkCall.getClientProductList(String.valueOf(userInfo.getData().getUser().getUserId()), listObserver);
                }
                buildUserContainer(userData);
//                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        listObserver = new Observer<ClientProductList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ClientProductList clientProductList) {
                List<ClientProductList.Productdetail> list = clientProductList.getData().getProductdetails();
                setAdapter(list);
//                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        statusObserver = new Observer<CertificateStatus>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CertificateStatus certificateStatus) {
                CertificateStatus.Certificateprocess ctStatus = certificateStatus.getData().getCertificateprocess().get(0);
                updateCertificateView(ctStatus);
//                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        applicationObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                if (!responseData.getData().getMessage().isEmpty()) {
                    masterNetworkCall.getCertificateStatus(String.valueOf(Id), statusObserver);
                } else {
                    Toast.makeText(getContext(), responseData.getError(), Toast.LENGTH_SHORT).show();
                }
//                masterNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        masterNetworkCall.init(getContext(), message, this);

        JsonObject object = new JsonObject();
        object.addProperty("id", Id);
        switch (constants.getMasterScreen()) {
            case 1:
                object.addProperty("role", "client");
                break;
            case 2:
                object.addProperty("role", "rre");
                break;
            case 3:
                object.addProperty("role", "crre");
                break;
            case 4:
                object.addProperty("role", "mastercrre");
                break;
        }
        masterNetworkCall.getUserInformation(object, userObserver);

    }

    private void updateCertificateView(CertificateStatus.Certificateprocess ctStatus) {
        String applicationstatus = ctStatus.getApplicationstatus();

        if (applicationstatus.equals("Pending")) {
            return;
        } else {
            apl_stat_lay.setVisibility(View.VISIBLE);
            application_status.setText(applicationstatus);
            apl_stat_lay.setOnClickListener(v -> {
                if (applicationstatus.equals("Approve")) {
                    masterNetworkCall.approveApplication(app_Id, "1", applicationObserver, null);
                    Toast.makeText(getContext(), "Application Status", Toast.LENGTH_SHORT).show();
                }
            });
        }

        String interviewstatus = ctStatus.getInterviewstatus();

        if (interviewstatus.equals("Pending")) {
            return;
        } else {
            interview_stat_lay.setVisibility(View.VISIBLE);
            interview_status.setText(interviewstatus);
            interview_stat_lay.setOnClickListener(v -> {
                if (interviewstatus.equals("Approve")) {
                    utils.showAlert(getContext(), 17, this);
                    Toast.makeText(getContext(), "Interview Status", Toast.LENGTH_SHORT).show();
                }
            });
        }


        String policystatus = ctStatus.getTrainingstatus();

        if (policystatus.equals("Pending")) {
            return;
        } else {
            policy_stat_lay.setVisibility(View.VISIBLE);
            app_policy_status.setText(policystatus);
            policy_stat_lay.setOnClickListener(v -> {
                if (policystatus.equals("Approve")) {
//                    masterNetworkCall.approveApplication(app_Id, "2", applicationObserver);
                    Toast.makeText(getContext(), "Policy Status", Toast.LENGTH_SHORT).show();
                }
            });
        }

        String certificatestatus = ctStatus.getCertificatestatus();

        if (certificatestatus.equals("Pending")) {
            return;
        } else {
            certificate_stat_lay.setVisibility(View.VISIBLE);
            certificate_status.setText(certificatestatus);
            certificate_stat_lay.setOnClickListener(v -> {
                if (certificatestatus.equals("Approve")) {
                    Toast.makeText(getContext(), "Certificate Status", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (Screen == 2) {
            headerTv.setText("Submitted Documents");
            layoutCertificateProcess.setVisibility(View.VISIBLE);
            masterNetworkCall.getCertificateStatus(String.valueOf(Id), statusObserver);
        } else {
            layoutCertificateProcess.setVisibility(View.GONE);
        }

        if (processList != null) {

        }
    }

    private void setAdapter(List<ClientProductList.Productdetail> list) {
        if (list != null || list.size() > 0) {
            adapter.init(getContext(), list, this);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(RecyclerView.VERTICAL);
            productRecycle.setLayoutManager(layoutManager);
            productRecycle.setAdapter(adapter);

        }
    }

    private void buildUserContainer(ArrayList<KeyValue> userData) {
        profileContainer.removeAllViews();
        for (KeyValue val : userData) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.key_value_item, profileContainer, false);
            TextView key = view.findViewById(R.id.txtKey);
            TextView value = view.findViewById(R.id.txtValue);
            key.setText(val.getKey());
            value.setText(val.getValue());
            profileContainer.addView(view);
        }
    }

    @Override
    public void refreshNetwork() {

    }

    @OnClick(R.id.layout_view)
    public void onProfileClick() {
        if (profileContainer.getVisibility() == View.VISIBLE) {
            profileContainer.setVisibility(View.GONE);
            profileScroll.setVisibility(View.GONE);
            layoutProductdetail.setVisibility(View.VISIBLE);
            productRecycle.setVisibility(View.GONE);
            if (Screen == 2) {
                layoutCertificateProcess.setVisibility(View.VISIBLE);
                certificateData.setVisibility(View.GONE);
            }
            userDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        } else {
            profileContainer.setVisibility(View.VISIBLE);
            profileScroll.setVisibility(View.VISIBLE);
            layoutProductdetail.setVisibility(View.GONE);
            productRecycle.setVisibility(View.GONE);
            if (Screen == 2) {
                layoutCertificateProcess.setVisibility(View.GONE);
                certificateData.setVisibility(View.GONE);
            }
            userDrop.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }
    }

    @OnClick(R.id.layout_product)
    public void onProductClick() {
        if (productRecycle.getVisibility() == View.VISIBLE) {
            productRecycle.setVisibility(View.GONE);
            productDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        } else {
            productRecycle.setVisibility(View.VISIBLE);
            productDrop.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }
    }

    @OnClick(R.id.layout_certificate_process)
    public void onCertificateClick() {
        if (certificateData.getVisibility() == View.VISIBLE) {
            certificateData.setVisibility(View.GONE);
        } else {
            certificateData.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewIconClick(int position) {

    }

    @Override
    public void alertResponse(String comment) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("applicationId", app_Id);
        jsonObject.addProperty("comment", comment);
        masterNetworkCall.approveApplication("", "1", applicationObserver, jsonObject);

    }
}
