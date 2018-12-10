package com.xpn.foodinfo.viewmodels.main.home;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.services.image.ImageService;
import com.xpn.foodinfo.services.user.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class HomeVMFactory implements ViewModelProvider.Factory {
    private final UserService userService;
    private final ImageService imageService;

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeVM(userService, imageService);
    }
}
