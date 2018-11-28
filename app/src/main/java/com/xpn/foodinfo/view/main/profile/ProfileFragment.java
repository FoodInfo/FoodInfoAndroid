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
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.ProfileFragmentBinding;
import com.xpn.foodinfo.view.SplashScreenActivity;
import com.xpn.foodinfo.viewmodels.main.profile.ProfileVM;


public class ProfileFragment extends Fragment {

    private ProfileVM viewModel;
    private ProfileFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false);
        viewModel = ViewModelProviders.of(this).get(ProfileVM.class);

        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

        viewModel.onSignOutListener().observe(this, o -> {
            Activity parentActivity = getActivity();
            assert parentActivity != null;

            AuthUI.getInstance().signOut(parentActivity).addOnSuccessListener(task -> {
                Toast.makeText(parentActivity, "Signed out", Toast.LENGTH_SHORT).show();
                SplashScreenActivity.start(parentActivity);
                parentActivity.finish();
            });
        });
    }
}
