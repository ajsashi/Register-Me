package com.register.me.view.fragments.RRE.policytraining;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.model.PolicyTraining;
import com.register.me.model.data.model.ResponseData;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.PolicyPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.activity.VideoActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PolicyTrainingFragment extends BaseFragment implements IFragment, PolicyPresenter.IPolicy, Utils.UtilNetworkInterface {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.restrictView)
    View restrictView;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.next)
    TextView next;
    @BindView(R.id.count_txt)
    TextView countTxt;
    @BindView(R.id.baseContainer)
    ConstraintLayout base;
    @Inject
    RRENetworkCall rreNetworkCall;
    private Observer<String> message;
    private Observer<PolicyTraining> questionObserver;
    private int i = 0;
    private int totalSize;
    PolicyTraining policy_Training;
    private String video_Id;
    @Inject
    PolicyPresenter presenter;
    private Observer<ResponseData> statusObserver;
    @Inject
    Utils utils;
    ArrayList<Boolean> isAnswered;
    private boolean isRefreshed;


    public static IFragment newInstance() {
        return new PolicyTrainingFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_policy_training;
    }

    @Override
    public String getFragmentName() {
        return "PolicyTrainingScreen";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        message = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                try {
                    if (utils.isOnline(getContext())) {
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();}
                    dismissProgress();
                    rreNetworkCall.clearDisposable();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        questionObserver = new Observer<PolicyTraining>() {


            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PolicyTraining policyTraining) {
                policy_Training = policyTraining;
                totalSize = policyTraining.getData().getApplicationtraining().size();
                PolicyTraining.Applicationtraining applicationtraining = policy_Training.getData().getApplicationtraining().get(i);
                buildScreen(applicationtraining);
                buildQuestioniare(applicationtraining);
                rreNetworkCall.clearDisposable();

//                Toast.makeText(getContext(), policyTraining.getData().getApplicationtraining().size() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        statusObserver = new Observer<ResponseData>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ResponseData responseData) {
                dismissProgress();
                rreNetworkCall.clearDisposable();
                Toast.makeText(getContext(), responseData.getData().getMessage(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        restrictView.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Please watch the video to answer the questions", Toast.LENGTH_SHORT).show();
        });
    }

    private void buildQuestioniare(PolicyTraining.Applicationtraining applicationtraining) {
        container.removeAllViews();
        isAnswered = new ArrayList<>();
        int i = 0;
        for (PolicyTraining.Question item : applicationtraining.getQuestions()) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.question_item, null, false);
            TextView txtQuestion = view.findViewById(R.id.txtQuestion);
            RadioGroup rdGroup = view.findViewById(R.id.radioGroup);
            RadioButton rb1 = view.findViewById(R.id.rdBtn1);
            RadioButton rb2 = view.findViewById(R.id.rdBtn2);
            RadioButton rb3 = view.findViewById(R.id.rdBtn3);
            RadioButton rb4 = view.findViewById(R.id.rdBtn4);

            txtQuestion.setText(item.getQuestion());
            rb1.setText(item.getOption1());
            rb2.setText(item.getOption2());
            rb3.setText(item.getOption3());
            rb4.setText(item.getOption4());

            isAnswered.add(false);
            int finalI = i;
            rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    RadioButton button = group.findViewById(checkedId);
                    String st = button.getText().toString();
                    if (button != null) {
                        isAnswered.set(finalI, true);
                    }
                    ;
//                    Toast.makeText(getContext(), st, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
            i++;
        }


    }

    private void buildScreen(PolicyTraining.Applicationtraining applicationtraining) {
        String thumnail = getYoutubeThumbnailUrlFromVideoUrl(applicationtraining.getVideo());
        Glide.with(this).load(thumnail).into(imageView);

        video_Id = getVideoId(applicationtraining.getVideo());
        imageView.setOnClickListener(v -> {
//            Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(getContext(), VideoActivity.class).putExtra("ID", video_Id), 1);
        });
//        Toast.makeText(getContext(), video_Id + "", Toast.LENGTH_SHORT).show();
    }

    public static String getYoutubeThumbnailUrlFromVideoUrl(String videoUrl) {
        return "http://img.youtube.com/vi/" + getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
    }

    public static String getYoutubeVideoIdFromUrl(String inUrl) {
        inUrl = inUrl.replace("&feature=youtu.be", "");
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    private final static String expression = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";

    public static String getVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().length() <= 0) {
            return null;
        }
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(videoUrl);
        try {
            if (matcher.find())
                return matcher.group();
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            String status = data.getStringExtra("STATUS");
//            Toast.makeText(getContext(), status, Toast.LENGTH_SHORT).show();
            restrictView.setVisibility(View.GONE);
            i++;
            if (i == totalSize) {
                next.setText("Comment");
            }
//            Toast.makeText(getContext(), "i : "+i +" total : "+totalSize, Toast.LENGTH_SHORT).show();

        }
    }

    @OnClick(R.id.next)
    public void onClickNext() {
        if (next.getText().toString().equals("Comment")) {
            presenter.showAlert();
            return;
        }
        if (i <= totalSize && restrictView.getVisibility() == View.GONE) {

            for (boolean val : isAnswered) {
                if (!val) {
                    Toast.makeText(getContext(), "Please Answer All The Question !", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            PolicyTraining.Applicationtraining applicationtraining = policy_Training.getData().getApplicationtraining().get(i);
            buildScreen(applicationtraining);
            buildQuestioniare(applicationtraining);
            restrictView.setVisibility(View.VISIBLE);
            scrollView.smoothScrollTo(0, 0);
            countTxt.setText((i + 1) + " / " + totalSize);
        } else if (restrictView.getVisibility() == View.VISIBLE) {
            Toast.makeText(getContext(), "Please answer the questions", Toast.LENGTH_SHORT).show();
        } else if (i + 1 > totalSize) {
            Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showMessage(String message) {
        utils.showToastMessage(getContext(), message);
    }

    @Override
    public void triggerApi(String success) {
        JsonObject object = new JsonObject();
        object.addProperty("comment", success);
        showProgress();
        rreNetworkCall.trainingStatus(object, statusObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("APPLICATION & POLICY TRAINING");
        if (policy_Training == null) {
            rreNetworkCall.init(getContext(), message, this);
            rreNetworkCall.getQuestions(questionObserver);
        } else {
            dismissProgress();
            rreNetworkCall.checkNetStatus();
            if (i ==4 && presenter.isOnline()&&isRefreshed) {
                isRefreshed =false;
                presenter.showAlert();
            }
        }
    }

    @Override
    public void refreshNetwork() {
isRefreshed = true;
        onResume();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        progressLayout.setVisibility(View.VISIBLE);
    }

    private void dismissProgress() {
        progressBar.setVisibility(View.GONE);
        progressLayout.setVisibility(View.GONE);
    }
}
