package com.register.me.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.register.me.MyApplication;
import com.register.me.R;
import com.register.me.model.di.activity.ActivityComponent;

import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    public ActivityComponent injector() {
        return ((MyApplication) getApplicationContext()).getActivityComponent(this);
    }
}
