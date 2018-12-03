package com.xpn.foodinfo.viewmodels.main.profile;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.xpn.foodinfo.services.preference.PreferenceService;

import java.util.List;
import java.util.Map;

import lombok.Data;


@Data
public class PreferencesVMFactory implements ViewModelProvider.Factory {
    private final PreferenceService preferenceService;
    private final Map<PreferencesVM.PreferenceTag, List<String>> availablePreferences;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new PreferencesVM(availablePreferences, preferenceService);
    }
}
