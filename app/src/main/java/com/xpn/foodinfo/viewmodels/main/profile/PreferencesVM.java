package com.xpn.foodinfo.viewmodels.main.profile;

import android.support.annotation.NonNull;

import com.xpn.foodinfo.models.Preference;
import com.xpn.foodinfo.services.preference.PreferenceService;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class PreferencesVM extends BaseViewModel {
    private final PreferenceService preferenceService;

    private final Map<PreferenceTag,  List<String>> availablePreferences;
    private Map<PreferenceTag, Preference> currentPreference = new HashMap<>();

    PreferencesVM(Map<PreferenceTag, List<String>> availablePreferences, PreferenceService preferenceService) {
        this.availablePreferences = availablePreferences;
        this.preferenceService = preferenceService;

        onLoadPreferences();
    }

    public List <String> getPreferenceList(PreferenceTag preference) {
        return availablePreferences.get(preference);
    }
    public Integer getCurrentPreferenceId(PreferenceTag preference) {
        if(!currentPreference.containsKey(preference))
            return null;
        String current = currentPreference.get(preference).getPreference();
        List <String> preferenceList = getPreferenceList(preference);
        return preferenceList.indexOf(current);
    }
    public boolean isPreferenceLoaded(PreferenceTag tag) {
        return currentPreference.containsKey(tag);
    }

    private void setPreference(Preference preference) {
        currentPreference.put(PreferenceTag.fromTag(preference.getTag()), preference);
        notifyChange();
    }
    public void onPreferenceSelected(PreferenceTag tag, int preferenceId) {
        String preference = Objects.requireNonNull(availablePreferences.get(tag)).get(preferenceId);
        updatePreference(new Preference(tag.tag, preference));
    }
    private void updatePreference(Preference preference) {
        addSubscription(
                preferenceService.save(preference)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> setPreference(preference),
                                Timber::e
                        )
        );
    }


    private void onLoadPreferences() {
        Observable <Preference> request = Observable.empty();
        for( PreferenceTag tag: availablePreferences.keySet()) {
            request = request.mergeWith(
                    preferenceService.get(
                            tag.toString(),
                            Objects.requireNonNull(availablePreferences.get(tag)).get(0)).toObservable()
            );
        }

        addSubscription(
            request.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::setPreference)
        );
    }


    public enum PreferenceTag {
        ENERGY("energy"),
        MASS("mass"),
        VOLUME("volume");

        private static final Map <String, PreferenceTag> lookup = new HashMap<>();

        static {
            for (PreferenceTag m : EnumSet.allOf(PreferenceTag.class))
                lookup.put(m.tag, m);
        }

        private String tag;
        PreferenceTag(String tag) {
            this.tag = tag;
        }

        @NonNull
        @Override
        public String toString() {
            return tag;
        }

        public static PreferenceTag fromTag(String tag) {
            return lookup.get(tag);
        }
    }
}
