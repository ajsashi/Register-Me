package com.register.me.model.di.app;

import com.register.me.model.di.activity.ActivityComponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, AndroidModule.class})
public interface ApplicationComponent {
    ActivityComponent.Builder activityComponentBuilder();
}
