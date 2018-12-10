package com.xpn.foodinfo.viewmodels.camera;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.xpn.foodinfo.services.image.ImageUploadingService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class CameraVMFactory implements ViewModelProvider.Factory {

    private final ImageUploadingService imageUploadingService;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CameraVM(imageUploadingService);
    }
}
