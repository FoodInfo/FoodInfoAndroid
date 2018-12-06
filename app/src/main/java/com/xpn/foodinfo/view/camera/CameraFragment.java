package com.xpn.foodinfo.view.camera;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.CameraFragmentBinding;
import com.xpn.foodinfo.view.util.CameraInfoUtil;
import com.xpn.foodinfo.view.util.Util;
import com.xpn.foodinfo.viewmodels.camera.CameraCaptureVM;
import com.xpn.foodinfo.viewmodels.camera.CameraCaptureVMFactory;


public class CameraFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST_CODE = 707;

    private CameraContract contract;
    private CameraFragmentBinding binding;
    private CameraCaptureVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.camera_fragment, container, false);
        contract = (CameraContract) getActivity();
        viewModel = ViewModelProviders.of(this, new CameraCaptureVMFactory()).get(CameraCaptureVM.class);
        binding.setViewModel(viewModel);

        binding.camera.setLifecycleOwner(getViewLifecycleOwner());
        binding.camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM);
        binding.camera.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER);
        binding.camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                contract.setImage(picture, CameraInfoUtil.getRotation(picture));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onClearImageListener().observe(this, o -> {});
        viewModel.onCloseListener().observe(this, o -> contract.onCloseCamera());
        viewModel.onCaptureImageListener().observe(this, o -> binding.camera.captureSnapshot());
        viewModel.onChooseFromGalleryListener().observe(this, o -> Util.chooseImageFromGallery(this, PICK_IMAGE_REQUEST_CODE));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert getActivity() != null;

        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null || data.getData() == null) {
                viewModel.onFailure();
                Toast.makeText(getActivity(), "No image selected!", Toast.LENGTH_SHORT).show();
                return;
            }
            contract.setImage(data.getData());
        }
    }

    public interface CameraContract {
        void onCloseCamera();
        void setImage(Uri uri);
        void setImage(byte[] data, int rotationDegrees);
    }
}
