package com.xpn.foodinfo.viewmodels.imagedetails;

import android.databinding.Bindable;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.models.Image;
import com.xpn.foodinfo.models.Nutrient;
import com.xpn.foodinfo.services.image.FoodImageProcessingService;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;


@RequiredArgsConstructor
public class ImageDetailsVM extends BaseViewModel {
    private final FoodImageProcessingService foodImageProcessingService;
    private final Image image;
    private List<Nutrient> info;


    public void onStart() {
        loadInfo();
    }

    public String getDownloadUrl() {
        return image.getDownloadUrl();
    }

    private void loadInfo() {
        addSubscription(
                foodImageProcessingService.processImage(image.getDownloadUrl())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .lastOrError()
                        .subscribe(
                                this::setInto,
                                Timber::e
                        )
        );
    }


    @Bindable
    public String getInfo() {
        if( info == null )
            return "";
        return info.toString();
    }

    private void setInto(List<Nutrient> info) {
        Timber.e(info.toString());
        this.info = info;
        notifyPropertyChanged(BR.info);
    }
}
