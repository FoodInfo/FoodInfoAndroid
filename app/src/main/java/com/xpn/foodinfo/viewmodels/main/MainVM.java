package com.xpn.foodinfo.viewmodels.main;

import com.xpn.foodinfo.viewmodels.BaseViewModel;
import com.xpn.foodinfo.viewmodels.util.SingleLiveEvent;

import timber.log.Timber;


public class MainVM extends BaseViewModel {

    private SingleLiveEvent <Void> launchCamera = new SingleLiveEvent<>();

    public void onCameraClicked() {
        Timber.d("onCameraClicked!!!");
        launchCamera.call();
    }

    public SingleLiveEvent <Void> onLaunchCamera() {
        return launchCamera;
    }
}
