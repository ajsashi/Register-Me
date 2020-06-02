package com.register.me;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import com.register.me.model.data.util.Utils;
import com.register.me.model.di.activity.ActivityComponent;
import com.register.me.model.di.activity.ActivityModule;
import com.register.me.model.di.app.AndroidModule;
import com.register.me.model.di.app.ApplicationComponent;
import com.register.me.model.di.app.ApplicationModule;
import com.register.me.model.di.app.DaggerApplicationComponent;
import com.register.me.view.BaseActivity;
import com.register.me.util.CrashlyticsUtil;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


public class MyApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
       /* if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/

        Fabric.with(this, new Crashlytics());

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .androidModule(new AndroidModule(this))
                .build();
    }

    public ActivityComponent getActivityComponent(BaseActivity baseActivity) {
        return component.activityComponentBuilder().activityModule(new ActivityModule(baseActivity)).build();
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.ERROR && t != null) {

                StringBuilder errorLogBuilder = new StringBuilder();
                if (!TextUtils.isEmpty(message)) {
                    errorLogBuilder.append("\n");
                    errorLogBuilder.append(message.length() > 100 ? message.substring(0, 100) : message);
                }
                CrashlyticsUtil.logMessage(errorLogBuilder.toString());
                CrashlyticsUtil.log(t);
            }
        }
    }
}
