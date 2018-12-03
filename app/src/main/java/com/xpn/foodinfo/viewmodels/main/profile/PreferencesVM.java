package com.xpn.foodinfo.viewmodels.main.profile;

import android.support.annotation.NonNull;

import com.xpn.foodinfo.models.Preference;
import com.xpn.foodinfo.services.preference.PreferenceService;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import timber.log.Timber;


public class PreferencesVM extends BaseViewModel {
    private final PreferenceService preferenceService;

    private final Map<PreferenceTag,  List<String>> availablePreferences;
    private Map<PreferenceTag, Integer> currentPreference = new HashMap<>();

    public PreferencesVM(Map<PreferenceTag, List<String>> availablePreferences, PreferenceService preferenceService) {
        this.availablePreferences = availablePreferences;
        this.preferenceService = preferenceService;

        onLoadPreferences();
    }

    public List <String> getPreferenceList(PreferenceTag preference) {
        return availablePreferences.get(preference);
    }

    public void onPreferenceSelected(PreferenceTag tag, int preferenceId) {
        String preference = Objects.requireNonNull(availablePreferences.get(tag)).get(preferenceId);
        Preference pref = new Preference(tag.tag, preference);
        currentPreference.put(tag, preferenceId);
        Timber.d(pref.toString());
    }

    // @Bindable
    public boolean getIsPreferenceLoaded(PreferenceTag tag) {
        return currentPreference.containsKey(tag);
    }

    private void onLoadPreferences() {
        // TODO
        onPreferenceSelected(PreferenceTag.VOLUME, 0);
    }


    public enum PreferenceTag {
        ENERGY("energy"),
        MASS("mass"),
        VOLUME("volume");

        private String tag;
        PreferenceTag(String tag) {
            this.tag = tag;
        }

        @NonNull
        @Override
        public String toString() {
            return tag;
        }
    }
}
