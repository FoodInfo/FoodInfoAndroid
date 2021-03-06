package com.xpn.foodinfo.view.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.ActivityMainBinding;
import com.xpn.foodinfo.view.camera.CameraActivity;
import com.xpn.foodinfo.view.main.home.HomeFragment;
import com.xpn.foodinfo.view.main.profile.ProfileFragment;
import com.xpn.foodinfo.viewmodels.main.MainVM;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainVM viewModel;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainVM.class);
        binding.setViewModel(viewModel);


        binding.navigation.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(new HomeFragment());
                    return true;
                case R.id.navigation_journal:
                    return true;
                case R.id.navigation_profile:
                    showFragment(new ProfileFragment());
                    return true;
                default:
                    return false;
            }
        });
        binding.navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.onLaunchCamera().observe(this, o -> CameraActivity.start(this));
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
