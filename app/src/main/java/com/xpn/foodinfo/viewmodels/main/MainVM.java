package com.xpn.foodinfo.viewmodels.main;

import android.arch.lifecycle.ViewModel;

import com.xpn.foodinfo.viewmodels.util.SingleLiveEvent;

import timber.log.Timber;


public class MainVM extends ViewModel {

    private SingleLiveEvent <Void> launchCamera = new SingleLiveEvent<>();

    public void onCameraClicked() {
        Timber.d("onCameraClicked!!!");
        launchCamera.call();
    }

    public SingleLiveEvent <Void> onLaunchCamera() {
        return launchCamera;
    }
}
