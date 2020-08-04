package com.register.me.view.fragments.Client.portfolio.addProducts;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.JsonObject;
import com.register.me.APIs.RRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.model.ApplicationRRESubmission;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.AddProductPresenter;
import com.register.me.view.BaseActivity;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;
import com.thomashaertel.widget.MultiSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jennifer - AIT.
 */
public class AddProductFragment extends BaseFragment implements IFragment, AddProductPresenter.IAddProduct, Utils.UtilNetworkInterface {

    private static final String FRAGMENT_NAME = "Add Product";

    @Inject
    AddProductPresenter addProductPresenter;
    private List<QandA> questList;
    @BindView(R.id.progressBar)
    ConstraintLayout progressBar;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.txtBtn)
    TextView addEditBtn;
    @BindView(R.id.sub_header)
    TextView subHeader;
    @BindView(R.id.card_add)
    CardView add;
    @BindView(R.id.card_clear)
    CardView clear;
    @BindView(R.id.product_container)
    LinearLayout container;
    private String mTrue = "true";
    private String mFalse = "false";
    private String mSelect = "Select ";
    private Context context;
    private List<EditText> editList;
    @Inject
    RRENetworkCall rreNetworkCall;

    private Observer<String> message = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(String s) {
            dismissProgress();
            if (utils.isOnline(getContext())) {
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }
            rreNetworkCall.clearDisposable();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    private Observer<ApplicationRRESubmission> submitObserver;
    @Inject
    Utils utils;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        context = getContext();
        assert context != null;
        addProductPresenter.init(context, this);
        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        editList = new ArrayList<>();
        if (addProductPresenter.getRole() != 1) {
            fragmentChannel.setTitle(getResources().getString(R.string.add_product));
        }

    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (addProductPresenter.getRole() == 0) {
            if (addProductPresenter.getStatusAddOrEdit()) {
                questList = addProductPresenter.getQuestions();
            } else {
                addProductPresenter.setIsEdit();
                questList = addProductPresenter.getEditData();
                subHeader.setText("Update Product in Portfolio");
            }
            String lable = addProductPresenter.getLable();
            addEditBtn.setText(lable);
            fragmentChannel.setTitle(lable.toUpperCase());
        } /*else if (addProductPresenter.getRole() == 1) {
            subHeader.setVisibility(View.GONE);
//            rreNetworkCall.viewRREApplication(applicationObserver);
            questList = addProductPresenter.getRREApplication();
            addEditBtn.setText("SUBMIT");
        }*/
        if (questList != null) {
            buildUI();
        }
    }

    private void buildUI() {
        View inflateView;
        LayoutInflater inflater = LayoutInflater.from(context);

        int i = 0;
        container.removeAllViews();
        for (QandA item : questList) {
            switch (item.getViewType()) {
                case 1:
                    inflateView = getEditTextView(inflater, i, item);
                    break;
                case 2:
                    inflateView = getRadioGroupView(inflater, i, item);

                    break;
                case 3:
                    inflateView = getSpinnerView(inflater, i, item);
                    break;
                case 4:
                    inflateView = getRadioRadioView(inflater, i, item);
                    break;
                case 5:
                    inflateView = getRadioSpinnerView(inflater, i, item);

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getViewType());
            }
            container.addView(inflateView);
            i++;
        }

    }

    @NotNull
    private View getRadioSpinnerView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_radio_spinner, null, false);
        ConstraintLayout subRS = inflateView.findViewById(R.id.sub_layout);
        TextView mainTextRS = inflateView.findViewById(R.id.itemTextTitle);
        TextView selectTxtRS = inflateView.findViewById(R.id.sub_spinner_text);
        RadioGroup rrGroupRS = inflateView.findViewById(R.id.sub_rdGroup);
        RadioButton rrYes = inflateView.findViewById(R.id.rdValue_yes);
        RadioButton rrNo = inflateView.findViewById(R.id.rdValue_no);
        MultiSpinner spinner = inflateView.findViewById(R.id.sub_spinnerValue);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        int finalI14 = i;
        String question = item.getQuestion();
        List<String> sublist = item.getSubQA().getSubList();
        if (item.getAnswer() != null && item.getAnswer().equals(mTrue)) {
            rrYes.setChecked(true);
            subRS.setVisibility(View.VISIBLE);
        } else if (item.getAnswer() != null && item.getAnswer().equals(mFalse)) {
            rrNo.setChecked(true);
            subRS.setVisibility(View.GONE);
        }
        mainTextRS.setText(question);

        String[] array;
        if (question.contains("sterilization")) {
            array = context.getResources().getStringArray(R.array.sterile);
            adapter.addAll(array);
            setSpinnerAdapter(spinner, finalI14, adapter, selectTxtRS, item.getSubQA().getQuestion());
            if (sublist != null) {
                updateSpinner(item, selectTxtRS, spinner, sublist, array);
            } else {
                selectTxtRS.setText(String.format("%s%s", mSelect, item.getSubQA().getQuestion()));
            }
        } else {
            array = context.getResources().getStringArray(R.array.battery);
            adapter.addAll(array);
            setSpinnerAdapter(spinner, finalI14, adapter, selectTxtRS, item.getSubQA().getQuestion());
            if (sublist != null) {
                updateSpinner(item, selectTxtRS, spinner, sublist, array);
            }
        }
        rrGroupRS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.rdValue_yes) {
                    subRS.setVisibility(View.VISIBLE);
                    selectTxtRS.setText(String.format("%s%s", mSelect, item.getSubQA().getQuestion()));
                    questList.get(finalI14).setAnswer(mTrue);
                } else {
                    subRS.setVisibility(View.GONE);
                    questList.get(finalI14).setAnswer(mFalse);
                    questList.get(finalI14).getSubQA().setAnswer("");
                    boolean[] selected = new boolean[array.length];
                    for (int x = 0; x < array.length; x++) {
                        selected[x] = false;
                    }
                    spinner.setSelected(selected);

                }
            }
        });
        return inflateView;
    }

    @NotNull
    private View getRadioRadioView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_radio_radio, null, false);
        ConstraintLayout sub = inflateView.findViewById(R.id.sub);

        /*Radio Main*/
        TextView rrTxtView = inflateView.findViewById(R.id.itemTextTitle);
        rrTxtView.setText(item.getQuestion());
        RadioGroup rrGroup = inflateView.findViewById(R.id.rdValue);
        RadioButton yes = inflateView.findViewById(R.id.rdValue_yes);
        RadioButton no = inflateView.findViewById(R.id.rdValue_no);

        /*Radio Sub*/
        TextView rrSubTxtView = inflateView.findViewById(R.id.sub_itemTextTitle);
        rrSubTxtView.setText(item.getSubQA().getQuestion());
        RadioGroup rrSubGroup = inflateView.findViewById(R.id.sub_rdValue);
        RadioButton subYes = inflateView.findViewById(R.id.sub_rdValue_yes);
        RadioButton subNo = inflateView.findViewById(R.id.sub_rdValue_no);


        if (item.getAnswer() != null && item.getAnswer().equals(mTrue)) {
            yes.setChecked(true);
            sub.setVisibility(View.VISIBLE);

        } else if (item.getAnswer() != null && item.getAnswer().equals(mFalse)) {
            no.setChecked(true);
            sub.setVisibility(View.GONE);
        }

        if (item.getAnswer() != null && item.getSubQA().getAnswer().equals(mTrue)) {
            subYes.setChecked(true);
        } else if (item.getAnswer() != null && item.getSubQA().getAnswer().equals(mFalse)) {
            subNo.setChecked(true);
        }
        int finalI13 = i;

        rrGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                for (EditText edit : editList) {
                    edit.clearFocus();
                }
                if (id == R.id.rdValue_yes) {
                    sub.setVisibility(View.VISIBLE);
                    questList.get(finalI13).setAnswer(mTrue);
                    subNo.setChecked(true);
                   /* questList.get(finalI13).getSubQA().setAnswer(mFalse);*/
                } else {
                    sub.setVisibility(View.GONE);
                    questList.get(finalI13).setAnswer(mFalse);
                    questList.get(finalI13).getSubQA().setAnswer(mFalse);
                    rrSubGroup.clearCheck();
                }
            }
        });
        rrSubGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.sub_rdValue_yes) {
                    questList.get(finalI13).getSubQA().setAnswer(mTrue);
                } else {
                    questList.get(finalI13).getSubQA().setAnswer(mFalse);
                }
            }
        });
        return inflateView;
    }

    @NotNull
    private View getSpinnerView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_multispinner, null, false);
        TextView txtSpinnerTitle = inflateView.findViewById(R.id.textSpinnerTitle);
        TextView txtSelect = inflateView.findViewById(R.id.spinner_text);
        MultiSpinner mSpinner = (MultiSpinner) inflateView.findViewById(R.id.spinnerMulti);
        String question = item.getQuestion();
        txtSpinnerTitle.setText(question);
        mSpinner.setText(item.getAnswer());

        txtSelect.setText(String.format("%s%s", mSelect, question));
        List<String> subList = item.getSubList();
        ArrayAdapter<String> adapter = null;
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item);
        String[] array;
        int finalI;
        ArrayAdapter<String> finalAdapter;
        switch (question) {
            case "Product Classification":
                array = context.getResources().getStringArray(R.array.classification_type);
                break;
            case "Connection Type":
                if (addProductPresenter.getRole() == 0) {
                    array = context.getResources().getStringArray(R.array.connection_type);
                } else {
                    array = context.getResources().getStringArray(R.array.pro_connection_type);
                }
                break;
            case "Indications for Use":
                array = context.getResources().getStringArray(R.array.indication);
                break;
            case "Intended Population":
                array = context.getResources().getStringArray(R.array.intended_population);
                break;
            case "Device Type":
                array = context.getResources().getStringArray(R.array.device_type);
                break;
            case "Usage Environment":
                array = context.getResources().getStringArray(R.array.usage_env);
                break;
            case "Product Type":
                array = context.getResources().getStringArray(R.array.product_type);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + question);
        }

        adapter.addAll(array);
        finalI = i;
        finalAdapter = adapter;
        setSpinnerAdapter(mSpinner, finalI, finalAdapter, txtSelect, question);
        if (subList != null) {
            updateSpinner(item, txtSelect, mSpinner, subList, array);
        }
        return inflateView;
    }

    private void updateSpinner(QandA item, TextView txtSelect, MultiSpinner mSpinner, List<String> subList, String[] array) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                boolean[] selected;
                selected = addProductPresenter.getBoolArray(array, subList);
                mSpinner.setSelected(selected);
                mSpinner.setText("");
                String answer;
                if (item.getSubQA() != null) {
                    answer = item.getSubQA().getAnswer();
                } else {
                    answer = item.getAnswer();
                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        txtSelect.setText(answer.replaceAll("\\[", "").replaceAll("\\]", ""));
                    }
                });
            }
        });


    }


    @NotNull
    private View getRadioGroupView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_radio_group, null, false);
        TextView txtRadioView = inflateView.findViewById(R.id.itemTextTitle);
        txtRadioView.setText(item.getQuestion());
        RadioGroup group = inflateView.findViewById(R.id.rdValue);
        RadioButton yes = inflateView.findViewById(R.id.rdValue_yes);
        RadioButton no = inflateView.findViewById(R.id.rdValue_no);

        if (item.getAnswer() != null && item.getAnswer().equals(mTrue)) {
            yes.setChecked(true);
        } else if (item.getAnswer() != null && item.getAnswer().equals(mFalse)) {
            no.setChecked(true);
        }
        int finalI12 = i;
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.rdValue_yes) {
                    questList.get(finalI12).setAnswer(mTrue);
                } else {
                    questList.get(finalI12).setAnswer(mFalse);
                }
            }
        });
        return inflateView;
    }

    @NotNull
    private View getEditTextView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
       /* if (item.getQuestion().toLowerCase().contains("description")) {
            inflateView = inflater.inflate(R.layout.item_edittextdesc, null, false);
        } else {
            inflateView = inflater.inflate(R.layout.item_edittext, null, false);
        }*/

        inflateView = inflater.inflate(R.layout.item_edittext, null, false);
        TextView txtQuest = inflateView.findViewById(R.id.itemTxtTitle);
        EditText txtAns = inflateView.findViewById(R.id.itemEditValue);
        txtQuest.setText(item.getQuestion());
        txtAns.setHint(item.getQuestion());
        txtAns.setText(item.getAnswer());
//        txtAns.setText("Besides finding the source of the issue, I found the solution. If android:inputType is used, then textMultiLine must be used to enable multi-line support");
        int inputType = item.getInputType();
        txtAns.setInputType(addProductPresenter.getInputType(inputType));
        txtAns.setImeOptions(item.getAction() == 1 ? EditorInfo.IME_ACTION_NEXT : EditorInfo.IME_ACTION_DONE);
        editList.add(txtAns);
        int finalI1 = i;
        txtAns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (EditText et : editList) {
                    et.setFocusable(true);
                    et.requestFocus();
                }
            }
        });

    /*txtAns.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            Toast.makeText(context, "Focus", Toast.LENGTH_SHORT).show();
            txtAns.setFocusable(true);
            txtAns.requestFocus();
           txtAns.performClick();
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Service.INPUT_METHOD_SERVICE);
            imm.showSoftInput( txtAns, 0);
            return true;
        }
    });*/
        txtAns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// Nil
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// Nil

            }

            @Override
            public void afterTextChanged(Editable editable) {
                questList.get(finalI1).setAnswer(editable.toString().trim());
            }
        });

        return inflateView;
    }

    private void setSpinnerAdapter(MultiSpinner mSpinner, int finalI, ArrayAdapter<String> finalAdapter, TextView txtSelect, String defaultText) {
        /*  To fix auto scroll - edit text focus is removed */
        for (EditText edit : editList) {
            edit.clearFocus();
        }

        MultiSpinner.MultiSpinnerListener onSelectedListener = selected -> {
            mSpinner.setText("");
            ArrayList<String> list = new ArrayList<>();

            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    String item = Objects.requireNonNull(finalAdapter.getItem(i)).trim();
                    list.add(item);
                }
            }

            if (list.size() != 0) {
                String answer = addProductPresenter.getStringList(list);
                QandA pos = questList.get(finalI);
                if (pos.getAnswer() == null) {
                    pos.setAnswer("true");
                }
                if (questList.get(finalI).getSubQA() != null) {
                    questList.get(finalI).getSubQA().setAnswer(answer);
                } else {
                    questList.get(finalI).setAnswer(answer);
                }
                txtSelect.setText(answer);
            } else {
                questList.get(finalI).setAnswer(null);
                txtSelect.setText(String.format("%s%s", mSelect, defaultText));
            }
        };

        mSpinner.setAdapter(finalAdapter, false, onSelectedListener);
    }


    @OnClick(R.id.card_add)
    public void onClickAdd() {
        add.setEnabled(false);
        showProgress();
        addProductPresenter.validateAnswers(questList);
    }

    @OnClick(R.id.card_clear)
    public void onClearClick(){
        onResume();
    }

    @OnClick(R.id.card_cancel)
    public void onClickCancel() {
        ((HomeActivity) getContext()).onBackPressed();
    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_product;
    }

    public static AddProductFragment newInstance() {
        return new AddProductFragment();
    }

    @Override
    public void showErrorMessage(String message) {
        dismissProgress();
        add.setEnabled(true);
        if (message.toLowerCase().contains("success")) {
            ((BaseActivity) context).onBackPressed();
        }
        utils.showToastMessage(getContext(), message);
    }

    @Override
    public void triggerApi(JsonObject data) {
dismissProgress();
        submitObserver = new Observer<ApplicationRRESubmission>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ApplicationRRESubmission applicationRRESubmission) {
                dismissProgress();
                Toast.makeText(context, applicationRRESubmission.getData().getMessage(), Toast.LENGTH_SHORT).show();
                rreNetworkCall.clearDisposable();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };
        showProgress();
        add.setEnabled(true);
        rreNetworkCall.submitRREApplication(data, submitObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (addProductPresenter.getRole() == 1) {
            subHeader.setVisibility(View.GONE);
//            rreNetworkCall.viewRREApplication(applicationObserver);
            questList = addProductPresenter.getRREApplication();
            addEditBtn.setText("SUBMIT");
            clear.setVisibility(View.VISIBLE);
            if (questList != null) {
                buildUI();
            }
        }

        rreNetworkCall.init(getContext(), message, this);
    }

    @Override
    public void refreshNetwork() {
        if(rreNetworkCall.checkNetStatus())
        {
            dismissProgress();
            onResume();
        }
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
