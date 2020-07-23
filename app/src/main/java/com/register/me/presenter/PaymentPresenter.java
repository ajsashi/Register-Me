package com.register.me.presenter;

import android.content.Context;

import com.register.me.view.BaseActivity;
import com.stripe.android.model.Card;

public class PaymentPresenter {
    Context context;

    public void init(Context context) {
        this.context = context;
        ((BaseActivity)context).injector().inject(this);
    }

    public String checkCard(Card card) {

        if (card.validateCard()) {
            //Toast.makeText(this, "Card Information is Valid", Toast.LENGTH_SHORT).show();
            return "isValid";
//            chargeToken(card);
        } else if (!card.validateNumber()) {
            return "Card Number Not Valid";
        } else if (!card.validateCVC()) {
            return "Card CVC Not Valid";
        } else if (!card.validateExpiryDate()) {
            return "Card Date Not Valid";
        }
        return "Card not Valid";
    }

}
