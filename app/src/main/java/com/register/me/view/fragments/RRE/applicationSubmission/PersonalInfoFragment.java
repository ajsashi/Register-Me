package com.register.me.view.fragments.RRE.applicationSubmission;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.register.me.R;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.PersonalInfoPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 13-02-2020PM 03:04.
 */
public class PersonalInfoFragment extends BaseFragment implements IFragment, PersonalInfoPresenter.IPersonalInfo {

    @BindView(R.id.profile_container)
    LinearLayout container;
    @BindView(R.id.base)
    LinearLayout base;
    @BindView(R.id.btn_layout)
    ConstraintLayout layoutBTN;
    @BindView(R.id.sub_header)
    ConstraintLayout subHeader;
    @BindView(R.id.disableClick)
    View disableView;
    @BindView(R.id.img_Edit)
    ImageView editImg;
    private ArrayList<QandA> info;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.txt_add_bank)
    TextView addBank;
    @BindView(R.id.chk_email)
    CheckBox emailChk;
    @BindView(R.id.chk_sms)
    CheckBox smsChk;
    @BindView(R.id.layPbar)
    ConstraintLayout layoutPbar;
    @BindView(R.id.layout_holder)
    ConstraintLayout layout_holder;
    @BindView(R.id.scroll)
    ScrollView scrollView;

    @Inject
    PersonalInfoPresenter presenter;
    private boolean isExit;
    private ArrayList<QandA> subInfo;
    private ArrayList<QandA> notificaionInfo;
    @Inject
    Utils utils;
    private int role;

    public static IFragment newInstance() {
        return new PersonalInfoFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_info;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        role = presenter.getRole();


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (presenter.getRole() == 1) {
            subHeader.setVisibility(View.GONE);
            disableView.setVisibility(View.GONE);
            layoutBTN.setVisibility(View.VISIBLE);
        } else if (presenter.getRole() == 2) {
            disableView.setVisibility(View.GONE);
            editImg.setVisibility(View.GONE);
            addBank.setVisibility(View.VISIBLE);

        } else {
            layoutBTN.setVisibility(View.GONE);
        }
        notificaionInfo = new ArrayList<>();
        layout_holder.setOnClickListener(v -> {
            utils.hideKeyboard(layout_holder, getContext());
        });
        emailChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (notificaionInfo.size() > 0) {
                    QandA n1 = notificaionInfo.get(0);
                    if (n1 != null) {
                        n1.setAnswer(String.valueOf(isChecked));
                        notificaionInfo.set(0, n1);
                    }
                }
            }
        });
        smsChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (notificaionInfo.size() > 1) {
                    QandA n2 = notificaionInfo.get(1);
                    if (n2 != null) {
                        n2.setAnswer(String.valueOf(isChecked));
                        notificaionInfo.set(1, n2);
                    }
                }
            }
        });
        showProgress();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (role != 1) {
            fragmentChannel.setTitle(getResources().getString(R.string.profile_details));
        }
        presenter.getUserDetails();
    }

    @OnClick(R.id.txt_add_bank)
    public void onAddBankClick() {
        fragmentChannel.showBankDetails();
    }

    /*
    * Function that allows only text input for edit text*/
    public static InputFilter acceptonlyAlphabetValuesnotNumbersMethod() {
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean isCheck = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) {
                        sb.append(c);
                    } else {
                        isCheck = false;
                    }
                }
                if (isCheck)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString spannableString = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, spannableString, 0);
                        return spannableString;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
                Matcher match = pattern.matcher(String.valueOf(c));
                return match.matches();
            }
        };
    }

    private void updateUI(ArrayList<QandA> info) {

        View inflateView;
        int i = 0;
        for (QandA item : info) {
            String question = item.getQuestion();
            String answer = item.getAnswer();
            switch (item.getViewType()) {
                /*
                 * case 1 : Edit Text
                 * case 2 : Radio Button
                 * case 3 : Spinner */

                case 1:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_edittext, null, false);
                    if (inflateView == null) {
                        return;
                    }
                    TextView txtQuest = inflateView.findViewById(R.id.itemTxtTitle);


                    EditText txtAns = inflateView.findViewById(R.id.itemEditValue);
                    TextView changeEmail = inflateView.findViewById(R.id.item_change_email);
                    txtQuest.setText(question);
                    txtAns.setText(answer);
                    txtAns.setHint(question);
                    txtAns.setImeOptions(item.getAction() == 1 ? EditorInfo.IME_ACTION_NEXT : EditorInfo.IME_ACTION_DONE);
                    if (question.equals("Email") && (presenter.getRole() == 0||presenter.getRole() == 2)) {
                        changeEmail.setVisibility(View.VISIBLE);
                        changeEmail.setOnClickListener(v -> {
                            presenter.showAlert();
                        });
                    }
                    int inputType = item.getInputType();
                    txtAns.setInputType(presenter.getInputType(inputType));
                    if(inputType==5) {
                        txtAns.setFilters(new InputFilter[]{acceptonlyAlphabetValuesnotNumbersMethod()});
                    }

                    int finalI = i;
                    if (question.equals("Email")) {
                        txtAns.setFocusable(false);
                    } else {
                        txtAns.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                            Log.d("onTextChanged", s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        info.get(finalI).setAnswer(s.toString().trim());
//                                        Log.d("afterTextChanged", s.toString());
                                    }
                                }).start();

                            }
                        });
                    }
                    break;
                case 2:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_radio_group, null, false);
                    if (inflateView == null) {
                        return;
                    }
                    TextView txtRadioView = inflateView.findViewById(R.id.itemTextTitle);
                    txtRadioView.setText(question);
                    RadioGroup group = inflateView.findViewById(R.id.rdValue);
                    RadioButton email = inflateView.findViewById(R.id.rdValue_yes);
                    RadioButton sms = inflateView.findViewById(R.id.rdValue_no);
                    email.setText("Email");
                    if (presenter.getRole() == 0) {
                        sms.setVisibility(View.GONE);
                    } else {
                        sms.setVisibility(View.VISIBLE);
                    }
                    int finalI1 = i;
                    if (answer.equals("true")) {
                        email.setChecked(true);
                    } else {
                        email.setChecked(false);
                    }
                    group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            boolean isEmail = false;
                            boolean isSMS = false;
                            if (checkedId == email.getId()) {
                                PersonalInfoFragment.this.info.get(finalI1).setAnswer("true");
                                isEmail = true;
                                isSMS = false;
                            } else if (checkedId == sms.getId()) {
                                isEmail = false;
                                isSMS = true;
                            }
                            if (presenter.getRole() == 1) {
                                PersonalInfoFragment.this.info.add(new QandA("Notification", String.valueOf(isEmail), 2, 0, 0, "emailnotification", null, null));
                                PersonalInfoFragment.this.info.add(new QandA("Notification", String.valueOf(isSMS), 2, 0, 0, "smsnotification", null, null));
                            }
                        }
                    });

                    break;
                case 3:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, null, false);
                    if (inflateView == null) {
                        return;
                    }
                    TextView txtSpinnerView = inflateView.findViewById(R.id.textSpinnerTitle);
                    txtSpinnerView.setText(question);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getViewType());
            }
            container.addView(inflateView);
            base.setVisibility(View.VISIBLE);

            i = i + 1;
        }

        if (presenter.isEmail()) {
            emailChk.setChecked(true);
        }
        if (presenter.isSMS()) {
            smsChk.setChecked(true);
        }
    }

    @Override
    public String getFragmentName() {
        return "PersonalInfo";
    }

    @Override
    public void showErrorMessage(String message) {
        utils.showToastMessage(getContext(), message);
    }

    @Override
    public void updateUI(ArrayList<QandA> personalInfo, ArrayList<QandA> countryInfo) {
        dismissProgress();
        if (isExit) {
            fragmentChannel.updateNavigation();
//            return;
        }
        container.removeAllViews();
        this.info = new ArrayList<QandA>();
        this.subInfo = new ArrayList<QandA>();
        this.notificaionInfo = new ArrayList<QandA>();

        this.info = (ArrayList<QandA>) personalInfo.clone();
        this.subInfo.addAll(countryInfo);
        updateUI(info);
        int role = presenter.getRole();
        boolean org = presenter.hasORG();
        if ((role == 0 && !org) || role == 1) {
            updateUI(subInfo);
        }
        if (presenter.getRole() == 1) {
            smsChk.setVisibility(View.VISIBLE);
            notificaionInfo.clear();
            notificaionInfo.add(new QandA("Notification", String.valueOf(presenter.isEmail()), 0, 0, 0, "emailnotification", null, null));
            notificaionInfo.add(new QandA("Notification", String.valueOf(presenter.isSMS()), 0, 0, 0, "smsnotification", null, null));
            return;
        }
        notificaionInfo.clear();
        notificaionInfo.add(new QandA("Notification", String.valueOf(presenter.isEmail()), 0, 0, 0, "emailNotification", null, null));
    }

    @Override
    public void errorFetchingData(String s) {
        showErrorMessage(s);
    }

    @Override
    public void exitScreen() {
        isExit = true;
        presenter.getUserDetails();
//        fragmentChannel.popUp();
    }

    @Override
    public void dismissProgress() {
        progressLayout.setVisibility(View.GONE);
        layoutPbar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
        layoutPbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void refresh() {
        onResume();
    }

    @OnClick(R.id.disableClick)
    public void disableClick() {
        utils.showToastMessage(getContext(), getContext().getResources().getString(R.string.edit_icon_alert));
    }

    @OnClick(R.id.img_Edit)
    public void onClickEdit() {
        disableView.setVisibility(View.GONE);
        editImg.setVisibility(View.GONE);
        layoutBTN.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.card_cancel)
    public void onClickCancel() {
        fragmentChannel.popUp();
    }


    @OnClick(R.id.card_update)
    public void onClickUpdate() {
        if (info.size() < 14) {
            info.addAll(subInfo);
            info.addAll(notificaionInfo);
        }
        presenter.validate(info);
//        presenter.updateUser(info);

    }

}
