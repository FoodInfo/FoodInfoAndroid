package com.xpn.foodinfo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


public class FoodInfoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize crash reporting
        if( !BuildConfig.DEBUG ) {
            Fabric.with(this, new Crashlytics());
        }

        // Initialize logger
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
