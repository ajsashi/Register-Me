package com.register.me.presenter;


public interface FragmentPresenter extends Presenter {

    interface View extends Presenter.View {

        void setTitle();
    }
}
