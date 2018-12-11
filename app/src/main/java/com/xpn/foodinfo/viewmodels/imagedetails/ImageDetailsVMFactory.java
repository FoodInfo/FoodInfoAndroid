package com.xpn.foodinfo.viewmodels.imagedetails;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.xpn.foodinfo.models.Image;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ImageDetailsVMFactory implements ViewModelProvider.Factory {
    private final Image image;


    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ImageDetailsVM(image);
    }
}
