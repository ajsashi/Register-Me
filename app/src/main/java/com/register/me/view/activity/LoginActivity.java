package com.register.me.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.presenter.LoginPresenter;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginPresenter.ILogin {

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.editPassword)
    EditText password;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;

    @Inject
    Constants constants;
    @Inject
    LoginPresenter presenter;
    @Inject
    CacheRepo repo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(this, this);
        if (presenter.isLoggedIn()) {
            navigate();
        }

    }

    @OnClick(R.id.card_signIn)
    public void signInClick() {

        String emailAddress = email.getText().toString();
        String pass = password.getText().toString();
//        String emailAddress = "ajsashiapp@gmail.com";
//        String pass = "Test@123";
//        email.setText(emailAddress);
//        password.setText(pass);
//        presenter.validation("satheeshkumarait@gmail.com", "123456");//Client

        presenter.validation(emailAddress, pass);//Client
//        presenter.validation("rajesh13@20minutemail.iti","123456");//RRE
//       presenter.validation("test@test.com","123456789");

    }

    @OnClick(R.id.txt_SignUp)
    public void signUpClick() {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    @OnClick(R.id.txtForgotPassword)
    public void onClickForgotPassword() {
        presenter.forgotPassword();
    }

    @Override
    public void showErroMessage(String message) {
        KToast.customColorToast(this, message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void showProgress() {
        if (progressLayout.getVisibility() == View.GONE) {
            progressLayout.setVisibility(View.VISIBLE);
            progressLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public void dismissProgress() {
        if (progressLayout.getVisibility() == View.VISIBLE) {
            progressLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void navigate() {

            startActivity(new Intent(this, WelcomeActivity.class));
            finish();
    }

    @Override
    public void clearFields() {
        password.setText("");
    }
}
