package com.register.me.view.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.util.Utils;
import com.register.me.presenter.SignUpPresenter;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity implements SignUpPresenter.ISignUp, Utils.UtilAlertInterface {

    @BindView(R.id.email)
    TextView edtEmail;
    @BindView(R.id.editPassword)
    TextView edtPassword;
    @BindView(R.id.spinner_role)
    Spinner spinnerRole;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBarLayout;

    @Inject
    SignUpPresenter presenter;
    @Inject
    Utils utils;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(this, this);
    }

    @OnClick(R.id.btn_signUp)
    public void onClickSignUp() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String role = spinnerRole.getSelectedItem().toString();
        presenter.validation(email, password, role);
    }

    @OnClick(R.id.txtSignIn)
    public void onSignIn() {
        finish();
    }

    @Override
    public void showProgress() {
        if (progressBarLayout.getVisibility() == View.GONE) {
            progressBarLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void dismissProgress() {
        if (progressBarLayout.getVisibility() == View.VISIBLE) {
            progressBarLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void showAlert() {
        utils.showAlert(this, 1, this);
    }

    @Override
    public void showErrorMessage(String message) {
        KToast.customColorToast(this, message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void alertResponse(String success) {
        if (success.equals("Success")) {
            finish();
        }
    }

}
