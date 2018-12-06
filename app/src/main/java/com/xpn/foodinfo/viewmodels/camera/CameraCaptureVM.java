package com.xpn.foodinfo.viewmodels.camera;

import android.databinding.Bindable;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.viewmodels.BaseViewModel;
import com.xpn.foodinfo.viewmodels.util.SingleLiveEvent;


public class CameraCaptureVM extends BaseViewModel {

    private SingleLiveEvent <Void> clearImage = new SingleLiveEvent<>();
    private SingleLiveEvent <Void> close = new SingleLiveEvent<>();
    private SingleLiveEvent <Void> captureImage = new SingleLiveEvent<>();
    private SingleLiveEvent <Void> chooseFromGallery = new SingleLiveEvent<>();

    private State state = State.CAPTURING;


    @Bindable
    public State getState() {
        return this.state;
    }
    private void setState(State state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }

    public void onClearImage() {
        clearImage.call();
    }
    public SingleLiveEvent <Void> onClearImageListener() {
        return clearImage;
    }

    public void onClose() {
        close.call();
    }
    public SingleLiveEvent <Void> onCloseListener() {
        return close;
    }

    public void captureImage() {
        captureImage.call();
    }
    public SingleLiveEvent <Void> onCaptureImageListener() {
        return captureImage;
    }

    public void onChooseFromGallery() {
        chooseFromGallery.call();
    }
    public SingleLiveEvent <Void> onChooseFromGalleryListener() {
        return chooseFromGallery;
    }


    public void onRetakeImage() {
        onClearImage();
        setState(State.CAPTURING);
    }
    public void onFailure() {
        onClearImage();
        setState(State.CAPTURING);
    }
    public void onImageDisplayed() {
        setState(State.DONE);
        onClose();
    }

    public enum State {
        CAPTURING,
        PROCESSING,
        DONE,
    }
}
