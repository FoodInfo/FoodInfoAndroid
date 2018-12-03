package com.xpn.foodinfo.services.preference;

import com.xpn.foodinfo.models.Preference;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface PreferenceService {
    Completable save(Preference preference);
    Single<Preference> get(String tag, String defaultValue);
}
