package com.xpn.foodinfo.view.main.home;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xpn.foodinfo.Dependency;
import com.xpn.foodinfo.FoodInfoApp;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.HomeFragmentBinding;
import com.xpn.foodinfo.view.about.AboutActivity;
import com.xpn.foodinfo.viewmodels.main.home.HomeVM;
import com.xpn.foodinfo.viewmodels.main.home.HomeVMFactory;

import java.util.Objects;


public class HomeFragment extends Fragment {

    private HomeFragmentBinding binding;
    private HomeVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false);
        setHasOptionsMenu(true);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(binding.toolbar);

        binding.images.setNestedScrollingEnabled(false);
        binding.images.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        Dependency dependency = ((FoodInfoApp) getActivity().getApplication()).getDependency();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        viewModel = ViewModelProviders.of(this, new HomeVMFactory(dependency.getUserService(), dependency.getImageService())).get(HomeVM.class);

        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if( id == R.id.action_app_info )    { AboutActivity.start(getActivity());    return true; }
        return super.onOptionsItemSelected(item);
    }
}
