package com.register.me.model.di.activity;

import android.content.Context;
import androidx.fragment.app.FragmentManager;

import com.register.me.view.Adapter.AdapterModule;
import com.register.me.model.data.Constants;
import com.register.me.presenter.PresenterModule;
import com.register.me.view.BaseActivity;
import com.register.me.view.fragmentmanager.manager.FragmentManagerHandler;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;


@ActivityScope
@Module(includes = {PresenterModule.class, AdapterModule.class})
public class ActivityModule {

    private final BaseActivity baseActivity;

    public ActivityModule(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    @Provides
    @Named(Constants.ACTIVITY_CONTEXT)
    public Context provideActivityContext() {
        return baseActivity;
    }

    @Provides
    public FragmentManager provideFragmentManager() {
        return baseActivity.getSupportFragmentManager();
    }

    @Provides
    public FragmentManagerHandler provideStarterKitFragmentManager(FragmentManager fragmentManager) {
        return new FragmentManagerHandler(fragmentManager);
    }

}
