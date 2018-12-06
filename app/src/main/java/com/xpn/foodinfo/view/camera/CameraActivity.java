package com.xpn.foodinfo.view.camera;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.ActivityCameraBinding;
import com.xpn.foodinfo.viewmodels.camera.CameraVM;
import com.xpn.foodinfo.viewmodels.camera.CameraVMFactory;


public class CameraActivity extends AppCompatActivity implements CameraFragment.CameraContract {

    private static final String CAMERA_FRAGMENT = "camera_fragment";
    private ActivityCameraBinding binding;
    private CameraVM viewModel;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, CameraActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
        viewModel = ViewModelProviders.of(this, new CameraVMFactory()).get(CameraVM.class);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onShowCamera();
    }

    private void onShowCamera() {
        // get fragment manager, Make sure the current transaction finishes first
        FragmentManager fm = getSupportFragmentManager();
        fm.executePendingTransactions();

        // Don't make new transaction if it's already present
        if( fm.findFragmentByTag(CAMERA_FRAGMENT) == null ) {
            fm.beginTransaction()
                    .replace(R.id.camera_fragment, new CameraFragment(), CAMERA_FRAGMENT)
                    .setCustomAnimations(android.R.anim.bounce_interpolator, android.R.anim.linear_interpolator)
                    .commit();
        }
    }

    @Override
    public void onCloseCamera() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CAMERA_FRAGMENT);
        if(fragment != null)
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();

    }

    @Override
    public void setImage(Uri uri) {
        viewModel.setImageUri(uri);
    }

    @Override
    public void setImage(byte[] data, int rotationDegrees) {
        /// TODO: get real rotation degrees!!
        viewModel.setImageData(data, 0);
    }
}
