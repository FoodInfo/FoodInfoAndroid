package com.xpn.foodinfo.view.main.home;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
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
import com.xpn.foodinfo.view.imagedetails.ImageDetailsActivity;
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
        binding.images.setItemAnimator(null);


        Dependency dependency = ((FoodInfoApp) getActivity().getApplication()).getDependency();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        viewModel = ViewModelProviders.of(this, new HomeVMFactory(dependency.getUserService(), dependency.getImageService())).get(HomeVM.class);

        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onStart();
        viewModel.showPopupMenuListener().observe(this, p -> showPopupMenu(p.first, p.second));
        viewModel.showImageDetailsListener().observe(this, image -> ImageDetailsActivity.start(getContext(), image));
    }

    @SuppressLint("RestrictedApi")
    private void showPopupMenu(View view, int id) {
        PopupMenu popup = new PopupMenu(getContext(), view);

        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(menuItem -> {
            int itemId = menuItem.getItemId();
            if( itemId == R.id.action_share );
            if( itemId == R.id.action_delete );
            return true;
        });
        popup.inflate(R.menu.menu_image_grid_item);

        MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), (MenuBuilder) popup.getMenu(), view);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
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
