package com.register.me.model.di.app;

import com.register.me.MyApplication;
import com.register.me.model.di.activity.ActivityComponent;

import javax.inject.Singleton;

import dagger.Module;


@Singleton
@Module(subcomponents = {ActivityComponent.class})
public class ApplicationModule {

    private final MyApplication application;

    public ApplicationModule(MyApplication application) {
        this.application = application;
    }
}
