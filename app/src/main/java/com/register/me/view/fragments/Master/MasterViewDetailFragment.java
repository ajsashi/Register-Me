package com.register.me.view.fragments.Master;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.register.me.model.data.model.LocationModel;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.UserInfo;
import com.register.me.model.data.repository.CacheRepo;
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

public class MasterViewDetailFragment extends BaseFragment implements IFragment, Utils.UtilNetworkInterface,
        ProductListAdapter.OnItemClickListener, Utils.UtilAlertInterface, MasterViewDetailPresenter.MVDListener {

    private static ArrayList<KeyValue> processList;
    @BindView(R.id.profile_container)
    LinearLayout profileContainer;
    @BindView(R.id.doc_layout)
    ScrollView docLayout;
    @BindView(R.id.no_pro_doc_layout)
    TextView noProDocLayout;
    @BindView(R.id.view_Comments)
    ImageView view_Comments;
    @BindView(R.id.sub_header)
    TextView subHeader;
    @BindView(R.id.doc_container)
    LinearLayout docContainer;
    @BindView(R.id.profile_scroll)
    ScrollView profileScroll;
    @BindView(R.id.product_recycle)
    RecyclerView productRecycle;
    @BindView(R.id.layout_product)
    ConstraintLayout product_doc_header;
    @BindView(R.id.layout_certificate_process)
    ConstraintLayout certificate_header;
    @BindView(R.id.certificate_data)
    ConstraintLayout certificateData;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.userInfo_drop)
    ImageView userDrop;
    @BindView(R.id.product_drop)
    ImageView productDrop;
    @BindView(R.id.process_drop)
    ImageView processDrop;
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
    @Inject
    CacheRepo repo;
    private Observer<String> message;
    private Observer<UserInfo> userObserver;
    private Observer<ClientProductList> listObserver;
    private Observer<CertificateStatus> statusObserver;
    private Observer<ResponseData> applicationObserver;
    private Observer<LocationModel> locationObserver;
    private List<LocationModel.Location> locationList;
    private boolean isProductEmpty;
    private List<ClientProductList.Productdetail> list;
    private boolean isDocEmpty;
    private int masterScreen;
    private List<RREApplication.Comment> comments;
    private JsonObject object;
    private Context context;


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
        this.context = getContext();
    }

    private void screenSetup() {
        //Observer to get error message from retrofit call.
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(String s) {
                dismissProgress();
                if (utils.isOnline(context)) {
                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                }
                masterNetworkCall.clearDisposable();
            }


            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //Observer to receive the response of user information
        userObserver = new Observer<UserInfo>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(UserInfo userInfo) {

                ArrayList<KeyValue> userData = presenter.getUserDetails(userInfo);

                if (Screen == 1) {
                   /* noProDocLayout.setVisibility(View.VISIBLE);
                    noProDocLayout.setText("Please Wait . . . ");*/
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

        //Observer to receive the response of client products
        listObserver = new Observer<ClientProductList>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ClientProductList clientProductList) {
                masterNetworkCall.clearDisposable();
                list = clientProductList.getData().getProductdetails();
                if (list.size() > 0) {
                    setAdapter(list);
                } else {
                    isProductEmpty = true;
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //Observer to receive the response of RRE certificate status
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

        //Observer to receive the response of RRE certificate approval API of each stage(applicationstatus, interviewstatus, trainingstatus and certificatestatus )
        applicationObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                masterNetworkCall.clearDisposable();
                dismissProgress();
                if (!responseData.getData().getMessage().isEmpty()) {
                    masterNetworkCall.getCertificateStatus(String.valueOf(Id), statusObserver);
                } else {
                    Toast.makeText(getContext(), responseData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //Observer to receive the response of list of location to pass to alert dialog for RRE interview approval
        locationObserver = new Observer<LocationModel>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LocationModel locationModel) {
                locationList = locationModel.getData().getLocations();
                if (locationList != null) {
                    utils.setLocationList(locationList);
                    utils.showAlert(getContext(), 17, MasterViewDetailFragment.this::alertResponse);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        masterNetworkCall.init(context, message, this);

        object = new JsonObject();
        object.addProperty("id", Id);
        masterScreen = constants.getMasterScreen();
        switch (masterScreen) {
            case 1:
                subHeader.setText("Client Information");
                view_Comments.setVisibility(View.GONE);
                object.addProperty("role", "client");
                break;
            case 2:
                subHeader.setText("RRE Information");
                view_Comments.setVisibility(View.VISIBLE);
                object.addProperty("role", "rre");
                break;
            case 3:
                subHeader.setText("CRRE Information");
                view_Comments.setVisibility(View.VISIBLE);
                object.addProperty("role", "crre");

                break;
            case 4:
                object.addProperty("role", "mastercrre");
                break;
        }

        if (Screen == 2) {
            headerTv.setText("Submitted Documents");
            certificate_header.setVisibility(View.VISIBLE);
            masterNetworkCall.getCertificateStatus(String.valueOf(Id), statusObserver);
        } else if (Screen == 3) {
            headerTv.setText("Submitted Documents");
            certificate_header.setVisibility(View.VISIBLE);
        } else {
            certificate_header.setVisibility(View.GONE);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.init(context, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        screenSetup();
        //API call to get user information
        masterNetworkCall.getUserInformation(object, userObserver);
        switch (masterScreen) {
            case 2:
                presenter.getdocCommentsData(Id);
                break;
            case 3:
                presenter.getdocCommentsData(Id);
                certificate_header.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void updateDocs(List<RREApplication.Document> viewList) {
        if (viewList.size() > 0) {
            isDocEmpty = false;
            docContainer.removeAllViews();
            for (RREApplication.Document doc : viewList) {
                View inflateView;
                inflateView = LayoutInflater.from(getContext()).inflate(R.layout.document_item, null, false);
                TextView docName = inflateView.findViewById(R.id.docName);
                ImageView extImage = inflateView.findViewById(R.id.ext_image);
                ImageView view = inflateView.findViewById(R.id.view);
                ImageView delete = inflateView.findViewById(R.id.delete);

                String docUrl = doc.getDocument();
                String fileName = presenter.getFileName(docUrl);
                String extension = presenter.getExtension();
                Drawable res = null;
                try {
                    String uri = "@drawable/" + extension;
                    int imageResource = getContext().getResources().getIdentifier(uri, "drawable", getContext().getPackageName());
                    if (imageResource == 0) {
                        res = getContext().getResources().getDrawable(R.drawable.blank, null);
                    } else {
                        res = getContext().getResources().getDrawable(imageResource, null);
                    }
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                extImage.setImageDrawable(res);
                docName.setText(fileName);
                view.setOnClickListener(v -> {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(docUrl));
                    startActivity(i);
                });
                delete.setVisibility(View.GONE);
                docContainer.addView(inflateView);
            }
        } else {
            isDocEmpty = true;
        }

    }

    @Override
    public void updateComments(List<RREApplication.Comment> comment) {
        this.comments = comment;
    }

    @Override
    public void hideProgress() {

    }

    /* public View setUpDescription(RREApplication.Comment commentItem) {
             View inflater = LayoutInflater.from(getContext()).inflate(R.layout.comment_item, null, false);
             TextView commentTopic = inflater.findViewById(R.id.commentTopic);
             TextView commentDate = inflater.findViewById(R.id.commentDate);
             TextView txtCreatedby = inflater.findViewById(R.id.txt_createdBy);
             TextView description = inflater.findViewById(R.id.description);
             View containerView = inflater.findViewById(R.id.headerView);
             LinearLayout subContainer = inflater.findViewById(R.id.subDescription_container);
             CardView addComment = inflater.findViewById(R.id.addComment);


             commentTopic.setText(commentItem.getComment());
             commentDate.setText(commentItem.getCreateddate());
             txtCreatedby.setText(commentItem.getName());
             description.setText(commentItem.getDescription());

             addComment.setOnClickListener(view1 -> {
                 setCommentId(commentItem.getCommentID());
                 showAlert(commentItem.getCommentTopic());
     //            Toast.makeText(context, getCommentId()+"", Toast.LENGTH_SHORT).show();
             });

             if (commentItem.getSubDescriptions().size() != 0) {
                 subContainer.removeAllViews();
                 for (ViewActCompProject.SubDescription subItem : commentItem.getSubDescriptions()) {
                     View subInflater = setUpSubDescription(subItem);
                     subContainer.addView(subInflater);
                 }
                 containerView.setOnClickListener(view1 -> {
                     if (subContainer.getVisibility() == View.VISIBLE) {
                         subContainer.setVisibility(View.GONE);
                     } else {
                         subContainer.setVisibility(View.VISIBLE);
                     }
                 });
             }
             return inflater;
         }*/
    //Update the view of Certificate Process section based on the API response
    private void updateCertificateView(CertificateStatus.Certificateprocess ctStatus) {
        String applicationstatus = ctStatus.getApplicationstatus();

        if (applicationstatus.equals("Pending")) {
            return;
        } else {
            apl_stat_lay.setVisibility(View.VISIBLE);
            application_status.setText(applicationstatus);
            apl_stat_lay.setOnClickListener(v -> {
                if (applicationstatus.equals("Approve")) {
                    showProgress();
                    masterNetworkCall.approveApplication(app_Id, "1", applicationObserver, null);
//                    Toast.makeText(getContext(), "Application Status", Toast.LENGTH_SHORT).show();
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
                    showProgress();
                    masterNetworkCall.getLocation(locationObserver);
//                    Toast.makeText(getContext(), "Interview Status", Toast.LENGTH_SHORT).show();
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
                    showProgress();
                    masterNetworkCall.approveApplication(app_Id, "3", applicationObserver, null);
//                    Toast.makeText(getContext(), "Policy Status", Toast.LENGTH_SHORT).show();
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
                    showProgress();
                    masterNetworkCall.approveApplication(app_Id, "4", applicationObserver, null);
//                    Toast.makeText(getContext(), "Certificate Status", Toast.LENGTH_SHORT).show();
                }
            });
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
        onResume();
    }

    @OnClick(R.id.layout_view)
    public void onProfileClick() {
        if (profileContainer.getVisibility() == View.VISIBLE) {
            profileContainer.setVisibility(View.GONE);
            profileScroll.setVisibility(View.GONE);
            product_doc_header.setVisibility(View.VISIBLE);
            productRecycle.setVisibility(View.GONE);
            noProDocLayout.setVisibility(View.GONE);
            if (Screen == 2) {
                //hide product(Submitted Doc) header and data
                docLayout.setVisibility(View.GONE);
                noProDocLayout.setVisibility(View.GONE);

                // hide certificate header and data
                certificate_header.setVisibility(View.VISIBLE);
                certificateData.setVisibility(View.GONE);
            } else if (Screen == 3) {
                certificate_header.setVisibility(View.GONE);
                certificateData.setVisibility(View.GONE);
                docLayout.setVisibility(View.GONE);
                product_doc_header.setVisibility(View.VISIBLE);
            }
            userDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            productDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
            processDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        } else {
            profileContainer.setVisibility(View.VISIBLE);
            profileScroll.setVisibility(View.VISIBLE);
            product_doc_header.setVisibility(View.GONE);
            productRecycle.setVisibility(View.GONE);
            noProDocLayout.setVisibility(View.GONE);
            if (Screen == 2) {
                certificate_header.setVisibility(View.GONE);
                certificateData.setVisibility(View.GONE);
                docLayout.setVisibility(View.GONE);
            } else if (Screen == 3) {
                certificate_header.setVisibility(View.GONE);
                certificateData.setVisibility(View.GONE);
                docLayout.setVisibility(View.GONE);
                product_doc_header.setVisibility(View.GONE);
            }
            userDrop.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }
    }

    @OnClick(R.id.layout_product)
    public void onProductClick() {
        final String text = headerTv.getText().toString();
        switch (text) {
            case "Submitted Documents":
                if (docLayout.getVisibility() == View.VISIBLE) {
                    docLayout.setVisibility(View.GONE);
                    if (masterScreen == 2) {
                        certificate_header.setVisibility(View.VISIBLE);
                    } else {
                        certificate_header.setVisibility(View.GONE);
                    }
                    noProDocLayout.setVisibility(View.GONE);
                    productDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                } else {
                    docLayout.setVisibility(View.VISIBLE);
                    if (isDocEmpty) {
                        noProDocLayout.setText("No Documents Submitted !");
                        noProDocLayout.setVisibility(View.VISIBLE);
                    } else {
                        docContainer.setVisibility(View.VISIBLE);
                    }

                    certificate_header.setVisibility(View.GONE);
                    certificateData.setVisibility(View.GONE);
                    productDrop.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                }
                break;
            case "Product Details":
                if (productRecycle.getVisibility() == View.VISIBLE || noProDocLayout.getVisibility() == View.VISIBLE) {
                    if (isProductEmpty) {
                        noProDocLayout.setVisibility(View.GONE);
                        productDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    } else {
                        productRecycle.setVisibility(View.GONE);
                        productDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                    }

                } else {
                    if (isProductEmpty) {
                        noProDocLayout.setVisibility(View.VISIBLE);
                        productDrop.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    } else {
                        productRecycle.setVisibility(View.VISIBLE);
                        productDrop.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                    }

                }
                break;
        }

    }

    @OnClick(R.id.layout_certificate_process)
    public void onCertificateClick() {
        if (certificateData.getVisibility() == View.VISIBLE) {
            certificateData.setVisibility(View.GONE);
            processDrop.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
        } else {
            certificateData.setVisibility(View.VISIBLE);
            processDrop.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
        }
    }

    @OnClick(R.id.view_Comments)
    public void onCommentsClick() {
        if (comments != null && comments.size() > 0) {
            fragmentChannel.showMCommentScreen(comments, Id);
        } else {
            Toast.makeText(getContext(), "No Comments Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewIconClick(int position) {
        constants.setviewScreenFrom(5);
        constants.setProductID(String.valueOf(list.get(position).getProductid()));
        fragmentChannel.showViewProductDetails();
    }

    @Override
    public void alertResponse(String comment) {
        /*{"applicationId":"{applicationId}","crreId":"{crreId}","locationId":"{locationId1,locationId2,locationId3}","comment":"{yes}"}*/
        /*{"applicationId":"7","crreId":9,"comment":"res","locationId":"5,6"}*/
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("applicationId", app_Id);
        jsonObject.addProperty("crreId", Id);
        jsonObject.addProperty("comment", comment);
        ArrayList<LocationModel.Location> list = utils.getSelectedLocationList();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            builder.append(list.get(i).getId());
            if (i != list.size() - 1) {
                builder.append(",");
            }

        }
        jsonObject.addProperty("locationId", builder.toString());
        showProgress();
        masterNetworkCall.approveApplication("", "2", applicationObserver, jsonObject);

    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh() {
        onResume();
    }

    @Override
    public void showMessage(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
