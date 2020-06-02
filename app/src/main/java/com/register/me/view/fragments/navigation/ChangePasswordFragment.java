package com.register.me.view.fragments.navigation;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.presenter.ChangePasswordPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 17-02-2020PM 01:55.
 */


public class ChangePasswordFragment extends BaseFragment implements IFragment, ChangePasswordPresenter.IChangePassword {

    @BindView(R.id.edt_ctcPassword)
    EditText edtCtcPassword;
    @BindView(R.id.edt_newPassword)
    EditText edtNewPassword;
    @BindView(R.id.edt_conPassword)
    EditText edtConPassword;
    @BindView(R.id.btn_changePassword)
    CardView btnChangePass;
    @BindView(R.id.checkIcon)
    ImageView checkIcon;


    @Inject
    ChangePasswordPresenter presenter;

    public static IFragment newInstance() {
        return new ChangePasswordFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_change_password;
    }

    @Override
    public String getFragmentName() {
        return "ChangePassword";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(Objects.requireNonNull(getContext()), this);
        fragmentChannel.setTitle(getResources().getString(R.string.change_password));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        edtConPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkIcon.setVisibility(View.VISIBLE);
                if (!edtNewPassword.getText().toString().isEmpty() && edtNewPassword.getText().toString().contentEquals(s)) {
                    checkIcon.setImageResource(R.drawable.checked);
                } else if(edtConPassword.getText().toString().isEmpty()) {
                    checkIcon.setVisibility(View.GONE);
                }
                else {
                        checkIcon.setImageResource(R.drawable.unchecked);
                    }
                }

        });

    }

    @OnClick(R.id.btn_changePassword)
    public void onClickChangePass() {

        if (presenter.validate(checkIcon)) {
            presenter.validate(edtCtcPassword.getText().toString(), edtNewPassword.getText().toString(), edtConPassword.getText().toString());
        } else {
            showErrorMessage("All fields are mandatory");
        }
    }

    @Override
    public void showErrorMessage(String message) {

        KToast.customColorToast((Activity) getContext(), message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void popUp() {
        fragmentChannel.popUp();
    }
}
