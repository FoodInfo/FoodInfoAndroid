package com.xpn.foodinfo.view.camera;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.databinding.CameraFragmentBinding;
import com.xpn.foodinfo.view.util.CameraInfoUtil;
import com.xpn.foodinfo.viewmodels.camera.CameraVM;
import com.xpn.foodinfo.viewmodels.camera.CameraVMFactory;


public class CameraFragment extends Fragment {

    CameraFragmentBinding binding;
    CameraVM viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.camera_fragment, container, false);
        viewModel = ViewModelProviders.of(this, new CameraVMFactory()).get(CameraVM.class);
        binding.setViewModel(viewModel);

        binding.camera.setLifecycleOwner(getViewLifecycleOwner());
        binding.camera.mapGesture(Gesture.PINCH, GestureAction.ZOOM);
        binding.camera.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER);
        binding.camera.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
//                setImage(picture, CameraInfoUtil.getRotation(picture));
            }
        });

        return binding.getRoot();
    }
}
