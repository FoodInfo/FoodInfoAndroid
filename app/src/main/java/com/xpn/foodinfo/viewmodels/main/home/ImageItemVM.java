package com.xpn.foodinfo.viewmodels.main.home;

import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ImageItemVM extends BaseViewModel {
    private final Image image;

    public String getDownloadUrl() {
        return image.getDownloadUrl();
    }

    public String getDateCaptured() {
        return image.getDateCaptured();
    }
}
