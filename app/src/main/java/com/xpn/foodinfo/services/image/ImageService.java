package com.xpn.foodinfo.services.image;

import android.net.Uri;

import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.models.User;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;


public interface ImageService {
    Single<Image> upload(User user, String imageName, Date dateCaptured, Uri uri);
    Completable add(Image image);
    Completable remove(Image image);
    Single<List<Image>> getImages(User user);
}
