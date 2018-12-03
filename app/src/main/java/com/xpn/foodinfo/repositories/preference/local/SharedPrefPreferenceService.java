package com.xpn.foodinfo.repositories.preference.local;

import android.content.SharedPreferences;

import com.xpn.foodinfo.models.Preference;
import com.xpn.foodinfo.services.preference.PreferenceService;

import io.reactivex.Completable;
import io.reactivex.Single;


public class SharedPrefPreferenceService implements PreferenceService {

    private final SharedPreferences storage;
    public SharedPrefPreferenceService(SharedPreferences preferences) {
        this.storage = preferences;
    }

    @Override
    public Completable save(Preference preference) {
        return Completable.defer(() -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = storage.edit();
            editor.putString( preference.getTag(), preference.getPreference() );
            editor.apply();
        }));
    }

    @Override
    public Single<Preference> get(String tag, String defaultValue) {
        return Single.defer(() -> {
            String preference = storage.getString( tag, defaultValue );
            return Single.just(new Preference(tag, preference));
        });
    }
}
