package com.xpn.foodinfo.viewmodels.main.home;

import android.databinding.Bindable;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.services.image.ImageService;
import com.xpn.foodinfo.services.user.UserService;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;


@RequiredArgsConstructor
public class HomeVM extends BaseViewModel {
    private final UserService userService;
    private final ImageService imageService;
    private List <ImageItemVM> imageVMs;

    public String getPhotoUrl() {
        return userService.getCurrentUser().getPhotoUrl();
    }

    private void loadImages() {
        addSubscription(
                imageService.getImages(userService.getCurrentUser())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                this::setImageVMs,
                                Timber::e
                        )
        );
    }

    @Bindable
    public List <ImageItemVM> getImageVMs() {
        if(imageVMs == null) {
            imageVMs = new ArrayList<>();
            loadImages();
        }
        return imageVMs;
    }
    private void setImageVMs(List<Image> images) {
        imageVMs.clear();
        for( Image image : images ) {
            imageVMs.add(new ImageItemVM(image));
        }
        notifyPropertyChanged(BR.imageVMs);
    }
}
