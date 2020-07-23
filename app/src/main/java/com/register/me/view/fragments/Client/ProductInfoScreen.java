package com.register.me.view.fragments.Client;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
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
import com.register.me.model.data.model.ActiveCompProject;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.ProductInfoPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;
import com.stripe.android.model.Address;
import com.stripe.android.model.ShippingInformation;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ProductInfoScreen extends BaseFragment implements IFragment {
    @BindView(R.id.product_container)
    LinearLayout productContainer;
    @BindView(R.id.billing_container)
    LinearLayout billingContainer;

    private static ActiveCompProject.ActiveProjectDetail mActProject;
    @Inject
    Utils utils;
    @Inject
    ProductInfoPresenter presenter;
    private ArrayList<QandA> bv;

    public static IFragment newInstance(ActiveCompProject.ActiveProjectDetail actProject) {
        mActProject = actProject;
        return new ProductInfoScreen();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_product_info;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<KeyValue> iv = new ArrayList<>();
        bv = new ArrayList<>();
        iv.add(new KeyValue("Number", mActProject.getProductnumber(), null));
        iv.add(new KeyValue("Name", mActProject.getProductname(), null));
        iv.add(new KeyValue("Country", mActProject.getCountry(), null));
        iv.add(new KeyValue("Bid Amount", mActProject.getBidamount(), null));
        iv.add(new KeyValue("Completion Date", mActProject.getCompletiondate(), null));
        iv.add(new KeyValue("Amount to pay", mActProject.getNextdueamount(), null));

        bv.add(new QandA("Full Name", "Jennifer ", 0, 1, 1, "", null, null));
        bv.add(new QandA("Email", "ajsashiapp@gmail.com", 0, 2, 1, "", null, null));
        bv.add(new QandA("Address", "Sundaram str", 0, 1, 1, "", null, null));
        bv.add(new QandA("City", "Coimbatore", 0, 1, 1, "", null, null));
        bv.add(new QandA("State", "Tamil nadu", 0, 1, 1, "", null, null));
        bv.add(new QandA("Country", "India", 0, 1, 1, "", null, null));
        bv.add(new QandA("Zip", "645213", 0, 1, 2, "", null, null));

        buildInfoView(iv);
        buildBillingView(bv);
    }

    private void buildBillingView(ArrayList<QandA> bv) {
        if (billingContainer.getChildCount() > 0) {
            billingContainer.removeAllViews();
        }
        int i=0;
        for (QandA item : bv) {
            View inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_edittext, null, false);
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
            txtAns.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    bv.get(finalI).setAnswer(s.toString());
                }
            });
            billingContainer.addView(inflateView);
            i++;
        }
    }

    private int getInputType(int inputType) {

        return utils.getInputType(inputType);

    }

    private void buildInfoView(ArrayList<KeyValue> kv) {
        if (productContainer.getChildCount() > 0) {
            productContainer.removeAllViews();
        }
        for (KeyValue val : kv) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.key_value_item, productContainer, false);
            TextView key = view.findViewById(R.id.txtKey);
            TextView value = view.findViewById(R.id.txtValue);
            key.setText(val.getKey());
            value.setText(val.getValue());
            productContainer.addView(view);
        }
    }

    @OnClick(R.id.card_pay)
    public void onPayClick() {
        if (presenter.validate(bv)) {
            ShippingInformation shippingInfo = new ShippingInformation(new Address.Builder()
                    .setLine1(bv.get(2).getAnswer())
                    .setCity(bv.get(3).getAnswer())
                    .setState(bv.get(4).getAnswer())
                    .setCountry(bv.get(5).getAnswer())
                    .setPostalCode(bv.get(6).getAnswer())
                    .build(),
                    bv.get(0).getAnswer(), "");
            fragmentChannel.showPaymnetScreen(mActProject.getNextdueamount(),shippingInfo);
        }
    }

    @OnClick(R.id.card_cancel)
    public void onCancelClick(){
        fragmentChannel.popUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentChannel.setTitle("Product Info");
    }

    @Override
    public String getFragmentName() {
        return "Product Info";
    }
}
