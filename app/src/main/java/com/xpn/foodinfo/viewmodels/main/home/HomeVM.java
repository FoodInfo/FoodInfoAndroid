package com.xpn.foodinfo.viewmodels.main.home;

import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.services.image.ImageService;
import com.xpn.foodinfo.services.user.UserService;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;


@RequiredArgsConstructor
public class HomeVM extends BaseViewModel {
    private final UserService userService;
    private final ImageService imageService;
    private List <ImageItemVM> imageVMs = new ArrayList<>();


    public void onStart() {
        loadImages();
    }

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
