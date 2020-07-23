package com.register.me.view.fragments.Client.activeProjects;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.model.RREApplication;
import com.register.me.model.data.model.RREComments;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.CommentPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jennifer - AIT on 11-03-2020AM 11:02.
 */
public class CommentFragment extends BaseFragment implements IFragment, CommentPresenter.ICommentListener, Utils.UtilNetworkInterface {

    private static List<RREApplication.Comment> mMasterComment;
    @BindView(R.id.description_container)
    LinearLayout commentContainer;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.addComment_main)
    CardView addMainButton;
    @BindView(R.id.layout_comment)
    ConstraintLayout postCommentLayout;
    @BindView(R.id.edt_comment)
    EditText rreEdt_comment;
    @BindView(R.id.no_content_layout)
    TextView noContentLayout;
    /*@BindView(R.id.no_content_layout)
    TextView noContentLayout;*/
    @Inject
    CommentPresenter presenter;
    @Inject
    RRENetworkCall rreNetworkCall;
    //project assign Id or user(RRE or CRRE from master app) Id.
    private static int pa_user_Id;
    private static List<ViewActCompProject.Comment> mComment;
    private List<RREApplication.Comment> rreComment;
    private Observer<String> message;
    private String token;
    private Observer<RREComments> commentsObserver;
    private Observer<RREApplication> applicationObserver;
    @Inject
    Utils utils;

    public static IFragment newInstance(List<ViewActCompProject.Comment> comments, int id) {
        mComment = comments;
        pa_user_Id = id;
        return new CommentFragment();
    }

    public static IFragment newMInstance(List<RREApplication.Comment> comments, int id) {
        mMasterComment = comments;
        pa_user_Id = id;
        return new CommentFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMasterComment != null && mMasterComment.size() > 0) {
            rreComment = mMasterComment;
            presenter.setRREorCRREID(pa_user_Id);
            buildMCRREUI();
        } else {
            final int role = presenter.getRole();
            if (role == 0| role ==1) {
                basicSetUp();
            } else {
                // noContentLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void basicSetUp() {
        postCommentLayout.setVisibility(View.VISIBLE);
        addMainButton.setVisibility(View.VISIBLE);
        token = presenter.getToken();
        if (presenter.getRole() == 0 || presenter.getRole() == 2) {
            postCommentLayout.setVisibility(View.GONE);
            presenter.setData(pa_user_Id);
            buildUI();
        } else {

            addMainButton.setVisibility(View.GONE);
            message = new Observer<String>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(String s) {
                    if (utils.isOnline(getContext())) {
                        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    }
                    dismissProgress();
                    rreNetworkCall.clearDisposable();
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            applicationObserver = new Observer<RREApplication>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(RREApplication rreApplication) {
                    dismissProgress();
                    presenter.setDocumentList(rreApplication);
                    if (presenter.getRole() == 0 || presenter.getRole() == 2) {
                        buildUI();
                    } else {
                        buildRREUI();
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            commentsObserver = new Observer<RREComments>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(RREComments rreComments) {
                    String msg = rreComments.getData().getMessage();
//                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    rreEdt_comment.setText("");
                    rreNetworkCall.viewRREApplication(applicationObserver);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            };
            rreNetworkCall.init(getContext(), message, this);
            buildRREUI();
        }
    }

    private void buildMCRREUI() {
        if (rreComment.size() != 0) {
            commentContainer.removeAllViews();
            for (RREApplication.Comment rreComment : rreComment) {
                View inflater = presenter.setUpRREDescription(rreComment);
                commentContainer.addView(inflater);
            }
        }
    }

    private void buildRREUI() {
        dismissProgress();
        rreComment = presenter.getCommentList();
        if (rreComment != null && rreComment.size() != 0) {
            commentContainer.removeAllViews();
            for (RREApplication.Comment rreComment : rreComment) {
                View inflater = presenter.setUpRREDescription(rreComment);
                commentContainer.addView(inflater);
            }
        }
    }

    private void buildUI() {
        if (mComment.size() != 0) {
            commentContainer.removeAllViews();
            for (ViewActCompProject.Comment commentItem : mComment) {
                View inflater = presenter.setUpDescription(commentItem);
                commentContainer.addView(inflater);
            }
        }else {
            noContentLayout.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.addComment_main)
    public void onClickAddComment() {
        presenter.setCommentId(0);
        presenter.showAlert("");
    }

    @OnClick(R.id.rre_submitComment)
    public void onRREClickSubmit() {
/*presenter.setCommentId(0);
presenter.showAlert("");*/
        String comment = rreEdt_comment.getText().toString().trim();
        if (!TextUtils.isEmpty(comment)) {
            apiCall(comment);
        } else {
            showMessage("Please fill all the fields");
        }
    }

    private void apiCall(String comment) {

        JsonObject json = presenter.buildJson(-1, comment);

        rreNetworkCall.postComment(token, json, commentsObserver);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_comment_screen;
    }

    @Override
    public String getFragmentName() {
        return "CommentFragment";
    }

    @Override
    public void onResume() {
        super.onResume();
        if(presenter.getRole()!=1){
        fragmentChannel.setTitle("Project Status");}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mComment = null;
    }

    @Override
    public void showMessage(String message) {
        utils.showToastMessage(getContext(), message);
    }

    @Override
    public void updateUI(List<ViewActCompProject.Comment> commentList) {
        mComment.clear();
        mComment.addAll(commentList);
        buildUI();
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressLayout.setVisibility(View.GONE);
    }

    @Override
    public void triggerApi(int commentId, String comment) {

        JsonObject json = presenter.buildJson(commentId, comment);
        rreNetworkCall.postComment(token, json, commentsObserver);
    }

    @Override
    public void updateMCRRE(List<RREApplication.Comment> comments) {

        rreComment = comments;
        buildMCRREUI();
    }

    @Override
    public void refreshNetwork() {
        rreNetworkCall.checkNetStatus();
    }
}
