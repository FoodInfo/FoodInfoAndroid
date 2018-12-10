package com.xpn.foodinfo.view.camera;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.ActivityCameraBinding;
import com.xpn.foodinfo.util.Time;
import com.xpn.foodinfo.viewmodels.camera.CameraVM;
import com.xpn.foodinfo.viewmodels.camera.CameraVMFactory;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import timber.log.Timber;


public class CameraActivity extends AppCompatActivity implements CameraFragment.CameraContract {

    private static final int CAMERA_IMAGE_CAPTURE_CODE = 701;

    private static final String CAMERA_FRAGMENT = "camera_fragment";
    private ActivityCameraBinding binding;
    private CameraVM viewModel;
    private Uri imageUri;

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
        onShowCamera();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult(%d, %d)->%d", requestCode, resultCode, Activity.RESULT_OK);
        if( requestCode == CAMERA_IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK ) {
            setImage(imageUri);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("imageUri", imageUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        imageUri = savedInstanceState.getParcelable("imageUri");
    }

    private void onShowCamera() {
        openDeviceCamera();
    }

    private void openDeviceCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) == null) {
            Toast.makeText(this, "No camera found", Toast.LENGTH_SHORT).show();
            return;
        }

        File photoFile;
        try {
            String imageFileName = "capture_" + Time.getDateInIso(new Date()) + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        }
        catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        imageUri = FileProvider.getUriForFile(this, "com.xpn.foodinfo", photoFile);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_IMAGE_CAPTURE_CODE);
    }

    private void openCameraFragment() {
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
        viewModel.setImageData(data, rotationDegrees);
    }
}
