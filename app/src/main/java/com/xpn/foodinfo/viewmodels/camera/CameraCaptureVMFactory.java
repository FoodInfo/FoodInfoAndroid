package com.xpn.foodinfo.viewmodels.camera;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;


public class CameraCaptureVMFactory implements ViewModelProvider.Factory {
    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CameraCaptureVM();
    }
}
