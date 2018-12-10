package com.xpn.foodinfo.services.image;

import android.net.Uri;

import com.xpn.foodinfo.models.Image;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface ImageUploadingService {
    Single<Image> upload(String imageName, Uri uri);
    Completable add(Image image);
    Completable remove(Image image);
}
