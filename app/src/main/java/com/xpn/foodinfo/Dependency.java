package com.xpn.foodinfo;

import android.content.Context;
import android.content.SharedPreferences;

import com.xpn.foodinfo.repositories.preference.local.SharedPrefPreferenceService;
import com.xpn.foodinfo.services.preference.PreferenceService;

import lombok.Getter;


public class Dependency {
    private static final String SHARED_PREFS_NAME = "cache";

    @Getter private final PreferenceService preferenceService;

    Dependency(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);

        this.preferenceService = new SharedPrefPreferenceService(sharedPreferences);
    }
}
