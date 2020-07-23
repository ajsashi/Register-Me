package com.register.me.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.APIs.MasterNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.PostReply;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 11-03-2020PM 12:50.
 */
public class CommentPresenter implements Utils.UtilAlertInterface, ClientNetworkCall.NetworkCallInterface {
    Context context;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;
    @Inject
    MasterNetworkCall masterNetworkCall;
    private ICommentListener listener;
    private int commentId = 0;
    private int proAssignId;
    private String token;
    private int rreOrCrreID = -1;

    public void init(Context context, ICommentListener listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        token = repo.getData(constants.getcacheTokenKey());
    }

    public void setData(int id) {

        proAssignId = id;
    }

    public void showAlert(String commentTopic) {
        utils.setCommentTopic(commentTopic);
        if (getRole() == 0 || getRole() == 2) {
            utils.showAlert(context, 10, this);
        } else {
            utils.showAlert(context, 11, this);
        }
    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("$ALERT$")) {
            listener.showMessage("Please fill all the fields");
        } else {
            final int role = getRole();
            if (role == 0 || role == 2) {
                String[] data = success.split(":");
                JsonObject object = getJsonObject(data);
                apiCall(object);
            } else if (role == 3) {
                JsonObject object = getJsonObjectMcrre(success);
                apiCall(object);
            } else {
                Toast.makeText(context, success, Toast.LENGTH_SHORT).show();
                listener.triggerApi(getCommentId(), success);
            }

        }
    }

    private JsonObject getJsonObjectMcrre(String data) {
        JsonObject object = new JsonObject();
        object.addProperty("id", rreOrCrreID);
        object.addProperty("commentid", commentId);
        object.addProperty("content", data);
        return object;
    }

    private void apiCall(JsonObject object) {
        if (utils.isOnline(context)) {
            listener.showProgress();

            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            if (object != null) {
                if (rreOrCrreID != -1) {
                    Observer<RREApplication> detailsOserver = new Observer<RREApplication>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(RREApplication rreApplication) {
                            listener.dismissProgress();
                            masterNetworkCall.clearDisposable();
                            final RREApplication.Data data = rreApplication.getData();
                            if (data != null) {
                                final List<RREApplication.Comment> comments = data.getComments();
                                if (comments != null) {
                                    listener.updateMCRRE(comments);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    };
                    Observer<ResponseData> commentObserver = new Observer<ResponseData>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseData responseData) {
                            masterNetworkCall.clearDisposable();
                            String data = responseData.getData().getMessage();
                            if (data != null && !data.isEmpty()) {
                                utils.showToastMessage(context, data);
                            }
                            listener.showProgress();
                            masterNetworkCall.getDocCommentDetails(String.valueOf(rreOrCrreID), detailsOserver);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    };
                    masterNetworkCall.postReply(object, commentObserver);
                    return;
                }
                networkCall.postReply(apiInterface, token, object, this);
            } else {
                String id = constants.getProjectID();
                networkCall.getACDetails(apiInterface, token, id, this);
            }
        } else {
            listener.showMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @NotNull
    private JsonObject getJsonObject(String[] data) {
        JsonObject object = new JsonObject();
        object.addProperty("projectassignid", proAssignId);
        object.addProperty("topic", data[0]);
        object.addProperty("content", data[1]);
        object.addProperty("commentid", getCommentId());
        return object;
    }

    @Override
    public void onCallSuccess(Object response) {
        listener.dismissProgress();
        if (response instanceof PostReply) {
            if (((PostReply) response).getData().getMessage().equals("Posted Successfully")) {
                apiCall(null);
            }
        } else if (response instanceof ViewActCompProject) {
            List<ViewActCompProject.Comment> commentList = ((ViewActCompProject) response).getData().getComments();
            listener.updateUI(commentList);
        }
    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();
        listener.showMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.dismissProgress();
        listener.showMessage("Session Expired");

        utils.sessionExpired(context, repo);

    }


    public View setUpDescription(ViewActCompProject.Comment commentItem) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.comment_item, null, false);
        TextView commentTopic = inflater.findViewById(R.id.commentTopic);
        TextView commentDate = inflater.findViewById(R.id.commentDate);
        TextView txtCreatedby = inflater.findViewById(R.id.txt_createdBy);
        TextView description = inflater.findViewById(R.id.description);
        View containerView = inflater.findViewById(R.id.headerView);
        LinearLayout subContainer = inflater.findViewById(R.id.subDescription_container);
        CardView addComment = inflater.findViewById(R.id.addComment);


        commentTopic.setText(commentItem.getCommentTopic());
        commentDate.setText(commentItem.getCreatedDate());
        txtCreatedby.setText(commentItem.getCreatedby());
        description.setText(commentItem.getDescription());

        addComment.setOnClickListener(view1 -> {
            setCommentId(commentItem.getCommentID());
            showAlert(commentItem.getCommentTopic());
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
    }


    public View setUpSubDescription(ViewActCompProject.SubDescription subItem) {
        View subInflater = LayoutInflater.from(context).inflate(R.layout.comment_sub_item, null, false);

        TextView subCommentDate = subInflater.findViewById(R.id.subCommentDate);
        TextView subCommentBy = subInflater.findViewById(R.id.subCommentBy);
        TextView subDescription = subInflater.findViewById(R.id.subDescription);

        subCommentDate.setText(subItem.getCreatedDate());
        subCommentBy.setText(subItem.getCreatedBy());
        subDescription.setText(subItem.getSubDescription());
        return subInflater;
    }

    public View setUpRREDescription(RREApplication.Comment rreComment) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.rrecomment_item, null, false);
        TextView commentDate = inflater.findViewById(R.id.rre_commentDate);
        TextView txtCreatedby = inflater.findViewById(R.id.rre_txt_createdBy);
        TextView description = inflater.findViewById(R.id.rre_description);
        View containerView = inflater.findViewById(R.id.rre_headerView);
        LinearLayout subContainer = inflater.findViewById(R.id.rre_subDescription_container);
        CardView addComment = inflater.findViewById(R.id.rre_addComment);

        commentDate.setText(rreComment.getCreateddate());
        txtCreatedby.setText(rreComment.getName());
        description.setText(rreComment.getComment());
        addComment.setOnClickListener(view1 -> {

            setCommentId(rreComment.getId());
            showAlert("");
//            Toast.makeText(context, getCommentId()+"", Toast.LENGTH_SHORT).show();
        });

        if (rreComment.getSubcomments().size() != 0) {
            subContainer.removeAllViews();
            for (RREApplication.Subcomment subItem : rreComment.getSubcomments()) {
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
    }

    private View setUpSubDescription(RREApplication.Subcomment subItem) {
        View subInflater = LayoutInflater.from(context).inflate(R.layout.rre_comment_sub_item, null, false);

        TextView subCommentDate = subInflater.findViewById(R.id.rre_subCommentDate);
        TextView subCommentBy = subInflater.findViewById(R.id.rre_subCommentBy);
        TextView subDescription = subInflater.findViewById(R.id.rre_subDescription);

        subCommentDate.setText(subItem.getCreateddate());
        subCommentBy.setText(subItem.getName());
        subDescription.setText(subItem.getComment());
        return subInflater;
    }

    public List<RREApplication.Comment> getCommentList() {
        RREApplication data = constants.getApplicationData();
        RREApplication.Data commentData;
        if (data != null) {
            commentData = data.getData();
        } else {
            data = new Gson().fromJson(repo.getData(constants.getCACHE_APPLICATION_DATA()), RREApplication.class);
            commentData = data.getData();
        }
        List<RREApplication.Comment> comment = null;
        if (data != null) {
            comment = commentData.getComments();
            if (comment == null) {
                return null;
            }
        }
        if (comment != null) {
            return comment;
        }

        return null;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getRole() {
        final int role = constants.getuserRole();
        return role;
    }

    public String getToken() {
        return token;
    }

    public JsonObject buildJson(int i, String comment) {
        JsonObject jsonObject = new JsonObject();
        if (i == -1) {
            i = 0;
        }
        jsonObject.addProperty("commentid", i);
        jsonObject.addProperty("content", comment);
        return jsonObject;
    }

    public void setDocumentList(RREApplication data) {
        repo.storeData(constants.getCACHE_APPLICATION_DATA(), new Gson().toJson(data));
        constants.setApplicationData(data);
    }

    public void setRREorCRREID(int pa_user_id) {
        rreOrCrreID = pa_user_id;
    }


    public interface ICommentListener {
        void showMessage(String message);

        void updateUI(List<ViewActCompProject.Comment> commentList);

        void showProgress();

        void dismissProgress();

        void triggerApi(int commentId, String success);

        void updateMCRRE(List<RREApplication.Comment> comments);
    }
}
