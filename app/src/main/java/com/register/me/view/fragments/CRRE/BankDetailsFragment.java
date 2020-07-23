package com.register.me.view.fragments.CRRE;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class BankDetailsFragment extends BaseFragment implements IFragment {

    @BindView(R.id.bank_container)
    LinearLayout bankContainer;
    private ArrayList<QandA> list;
    @Inject
    Utils utils;

    public static IFragment newInstance() {
        return new BankDetailsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bank_detail;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injector().inject(this);
        list = new ArrayList<>();
        list.add(new QandA("Bank Name", "", 1, 1, 1, "", null, null));
        list.add(new QandA("Routing Number", "", 1, 4, 1, "", null, null));
        list.add(new QandA("Account Number", "", 1, 4, 1, "", null, null));
        list.add(new QandA("Confirm Account Number", "", 1, 4, 2, "", null, null));
        bankContainer.setOnClickListener(v->{ utils.hideKeyboard(bankContainer,getContext());});
        buildUI();
    }

    private void buildUI() {
        int i=0;
        for (QandA item : list){
            View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_edittext, bankContainer, false);
            TextView txtQuest = inflateView.findViewById(R.id.itemTxtTitle);
            EditText txtAns = inflateView.findViewById(R.id.itemEditValue);
            txtQuest.setText(item.getQuestion());
            txtQuest.setTypeface(null, Typeface.BOLD);
            txtAns.setText(item.getAnswer());
//        txtAns.setText("Besides finding the source of the issue, I found the solution. If android:inputType is used, then textMultiLine must be used to enable multi-line support");
            int inputType = item.getInputType();
            txtAns.setInputType(getInputType(inputType));
            txtAns.setImeOptions(item.getAction() == 1 ? EditorInfo.IME_ACTION_NEXT : EditorInfo.IME_ACTION_DONE);
            int finalI = i;
            /*txtAns.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    list.get(finalI).setAnswer(s.toString());
                }
            });*/
            bankContainer.addView(inflateView);
            i++;
        }
    }

    private int getInputType(int inputType) {
        return utils.getInputType(inputType);

    }

    @Override
    public String getFragmentName() {
        return "BankDetailsFragment";
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("BANK DETAIL");
    }

    @OnClick(R.id.card_add)
    public void onAddClick(){
        Toast.makeText(getContext(), "To Do", Toast.LENGTH_SHORT).show();
    }
    @OnClick(R.id.card_cancel)
    public void onCancelClick(){
        fragmentChannel.popUp();
    }

}
