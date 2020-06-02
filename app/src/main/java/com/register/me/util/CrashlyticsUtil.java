package com.register.me.util;

import com.register.me.BuildConfig;
import com.crashlytics.android.Crashlytics;


public class CrashlyticsUtil {
    public static void log(Throwable throwable) {
        if (!BuildConfig.DEBUG && Crashlytics.getInstance() != null) {
            Crashlytics.getInstance().logException(throwable);
        }
    }

    public static void logMessage(String message) {
        if (!BuildConfig.DEBUG && Crashlytics.getInstance() != null) {
            Crashlytics.getInstance().log(message);
        }
    }

    public static void setUsername(String username) {
        if (!BuildConfig.DEBUG && Crashlytics.getInstance() != null) {
            Crashlytics.setUserName(username);
        }
    }
}
