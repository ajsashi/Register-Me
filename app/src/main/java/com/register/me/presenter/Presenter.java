package com.register.me.presenter;

import android.os.Bundle;


public interface Presenter {

    void dispose();

    void onSaveInstanceState(Bundle outState);

    void onRestoreInstanceState(Bundle outState);

    void restore();

    interface View {
        void dispose();

        void restore();
    }
}
