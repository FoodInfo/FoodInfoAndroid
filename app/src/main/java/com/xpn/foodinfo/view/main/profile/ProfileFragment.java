package com.xpn.foodinfo.view.main.profile;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xpn.foodinfo.Dependency;
import com.xpn.foodinfo.FoodInfoApp;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.ProfileFragmentBinding;
import com.xpn.foodinfo.view.SplashScreenActivity;
import com.xpn.foodinfo.viewmodels.main.profile.PreferencesVM;
import com.xpn.foodinfo.viewmodels.main.profile.PreferencesVMFactory;
import com.xpn.foodinfo.viewmodels.main.profile.ProfileVM;
import com.xpn.foodinfo.viewmodels.main.profile.ProfileVMFactory;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.keyvalue.DefaultMapEntry;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class ProfileFragment extends Fragment {

    private ProfileVM profileVM;
    private PreferencesVM preferencesVM;
    private ProfileFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);

        Map<PreferencesVM.PreferenceTag, List<String>> availablePreferences = MapUtils.putAll(new HashMap<>(), new Map.Entry[] {
                new DefaultMapEntry<>(PreferencesVM.PreferenceTag.ENERGY, Arrays.asList(getResources().getStringArray(R.array.energy_units))),
                new DefaultMapEntry<>(PreferencesVM.PreferenceTag.MASS, Arrays.asList(getResources().getStringArray(R.array.mass_units))),
                new DefaultMapEntry<>(PreferencesVM.PreferenceTag.VOLUME, Arrays.asList(getResources().getStringArray(R.array.volume_units))),
        });

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser == null ) {
            signOut();
        }
        else {
            Dependency dependency = ((FoodInfoApp) Objects.requireNonNull(getActivity()).getApplication()).getDependency();
            profileVM = ViewModelProviders.of(this, new ProfileVMFactory(currentUser)).get(ProfileVM.class);
            preferencesVM = ViewModelProviders.of(this, new PreferencesVMFactory(dependency.getPreferenceService(), availablePreferences)).get(PreferencesVM.class);

            binding.setProfileVM(profileVM);
            binding.setPreferencesVM(preferencesVM);
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        profileVM.onSignOutListener().observe(this, o -> signOut());
    }

    private void signOut() {
        Activity parentActivity = getActivity();
        assert parentActivity != null;

        AuthUI.getInstance().signOut(parentActivity).addOnSuccessListener(task -> {
            SplashScreenActivity.start(parentActivity);
            parentActivity.finish();
        });
    }
}
