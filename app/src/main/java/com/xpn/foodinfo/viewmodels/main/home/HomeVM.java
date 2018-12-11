package com.xpn.foodinfo.viewmodels.main.home;

import android.databinding.Bindable;
import android.util.Pair;
import android.view.View;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.services.image.ImageService;
import com.xpn.foodinfo.services.user.UserService;
import com.xpn.foodinfo.viewmodels.BaseViewModel;
import com.xpn.foodinfo.viewmodels.util.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;


@RequiredArgsConstructor
public class HomeVM extends BaseViewModel implements ImageItemVM.Contract {
    private final UserService userService;
    private final ImageService imageService;
    private List <Image> images = new ArrayList<>();
    private List <ImageItemVM> imageVMs = new ArrayList<>();
    private SingleLiveEvent <Pair<View, Integer> > showPopupMenu = new SingleLiveEvent<>();
    private SingleLiveEvent <Image> showImageDetails = new SingleLiveEvent<>();


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
        this.images = images;
        imageVMs.clear();
        for( int i=0; i < images.size(); ++i ) {
            imageVMs.add(new ImageItemVM(i, images.get(i), this));
        }
        notifyPropertyChanged(BR.imageVMs);
    }

    @Override
    public void onShowPopupMenu(View view, int id) {
        showPopupMenu.setValue(new Pair<>(view, id));
    }

    @Override
    public void onShowImageDetails(int id) {
        showImageDetails.setValue(images.get(id));
    }

    public SingleLiveEvent <Pair<View, Integer> > showPopupMenuListener() {
        return showPopupMenu;
    }
    public SingleLiveEvent <Image> showImageDetailsListener() {
        return showImageDetails;
    }
}
