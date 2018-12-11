package com.xpn.foodinfo.viewmodels.imagedetails;

import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import lombok.AllArgsConstructor;


@AllArgsConstructor
public class ImageDetailsVM extends BaseViewModel {
    private final Image image;

    public String getDownloadUrl() {
        return image.getDownloadUrl();
    }
}
