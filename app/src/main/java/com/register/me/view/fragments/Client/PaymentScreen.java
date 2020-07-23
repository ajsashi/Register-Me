package com.register.me.view.fragments.Client;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.register.me.R;
import com.register.me.model.data.stripe.StripeSupportManager;
import com.register.me.presenter.PaymentPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;
import com.stripe.android.model.Card;
import com.stripe.android.model.ShippingInformation;
import com.stripe.android.view.CardInputWidget;
import com.stripe.android.view.ShippingInfoWidget;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class PaymentScreen extends BaseFragment implements IFragment {

    private static ShippingInformation mShippingInfo;
    private static String dueAmount;
    @BindView(R.id.info_widget)
    ShippingInfoWidget infoWidget;
    @BindView(R.id.card_widget)
    CardInputWidget cardInputWidget;
    @BindView(R.id.progressbar)
    ConstraintLayout pBar;
    @Inject
    StripeSupportManager stripeSupportManager;
    @Inject
    PaymentPresenter presenter;
    Card card;

    public static IFragment newInstance(String nextdueamount, ShippingInformation shippingInfo) {
        dueAmount = nextdueamount;
        mShippingInfo = shippingInfo;
        return new PaymentScreen();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardInputWidget.setPostalCodeEnabled(false);



    }

    @OnClick(R.id.pay_btn)
    public void onPayClick() {
        if (infoWidget.validateAllFields()) {
            try {
                final ShippingInformation shippingInformation = infoWidget.getShippingInformation();
                card = cardInputWidget.getCardBuilder()
                        .addressLine1(shippingInformation.getAddress().getLine1())
                        .addressCity(shippingInformation.getAddress().getCity())
                        .addressState(shippingInformation.getAddress().getState())
                        .addressCountry(shippingInformation.getAddress().getCountry())
                        .addressZip(shippingInformation.getAddress().getPostalCode()).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (card!=null&&presenter.checkCard(card).equals("isValid")) {
                showProgress();
                stripeSupportManager.init(getContext());
                stripeSupportManager.setStripeInput(dueAmount.replace("$", ""), "Register me due payment - Android");
                stripeSupportManager.convertCardInfoToToken(card);
            } else {
                Toast.makeText(getContext(), "Please enter a valid card", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), "Validation Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mShippingInfo != null) {
            infoWidget.populateShippingInfo(mShippingInfo);
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        fragmentChannel.setTitle("Payment Screen");
    }

    @Override
    public void onPause() {
        super.onPause();
            EventBus.getDefault().unregister(this);
    }

    @Override
    public String getFragmentName() {
        return "Payment Screen";
    }
    /*
    * Event bus to receiver to receive the stripe url from the Stripe Support Manager Class*/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String receiptUrl) {
        dismissProgress();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(receiptUrl));
        startActivityForResult(i,159);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==159){
            fragmentChannel.popUp();
        }
    }

    public void showProgress() {
        pBar.setVisibility(View.VISIBLE);
    }


    public void dismissProgress() {
        pBar.setVisibility(View.GONE);
    }
}
