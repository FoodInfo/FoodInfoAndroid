package com.xpn.foodinfo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;


public class FoodInfoApp extends Application {

    private Dependency dependency;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize crash reporting
        if( !BuildConfig.DEBUG ) {
            Fabric.with(this, new Crashlytics());
        }

        // Initialize logger
        Timber.plant(new Timber.DebugTree());

        // Initialize dependencies
        dependency = new Dependency(this);

        // Initialize ads
        MobileAds.initialize(this, getString(R.string.app_id));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public Dependency getDependency() {
        return dependency;
    }
}
