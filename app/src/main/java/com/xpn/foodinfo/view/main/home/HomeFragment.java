package com.xpn.foodinfo.view.main.home;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.HomeFragmentBinding;
import com.xpn.foodinfo.viewmodels.main.home.HomeVM;
import com.xpn.foodinfo.viewmodels.main.home.HomeVMFactory;


public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private HomeVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        assert currentUser != null;
        viewModel = ViewModelProviders.of(this, new HomeVMFactory(currentUser)).get(HomeVM.class);
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
