package com.register.me.presenter;

import android.content.Context;
import android.widget.Toast;

import com.register.me.model.data.model.QandA;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;

public class ProductInfoPresenter {
    Context context;

    public void init(Context context) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
    }

    public boolean validate(ArrayList<QandA> bv) {
        for (QandA item : bv) {
            if (item.getAnswer() == null || item.getAnswer().isEmpty()) {
                Toast.makeText(context, item.getQuestion()+" is mandatory", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
       return true;
    }
}
