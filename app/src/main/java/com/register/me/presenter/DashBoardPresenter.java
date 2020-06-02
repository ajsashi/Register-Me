package com.register.me.presenter;

import android.os.Bundle;


public class DashBoardPresenter implements FragmentPresenter {
    private View view;

    @Override
    public void dispose() {

    }

    @Override
    public void restore() {

    }

    public void setView(View view) {
        this.view = view;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {

    }

    public interface View extends FragmentPresenter.View {


    }
}
