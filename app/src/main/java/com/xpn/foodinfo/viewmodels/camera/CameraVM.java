package com.xpn.foodinfo.viewmodels.camera;

import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.net.Uri;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.view.util.image.loading.BindingAdapters;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import timber.log.Timber;


public class CameraVM extends BaseViewModel implements BindingAdapters.ImageListener {

    private State state = State.CAPTURING;
    private String imageUrl;
    private Uri imageUri;
    private byte[] imageData;
    private Integer rotationDegrees;

    @Bindable
    public State getState() {
        return this.state;
    }
    private void setState(State state) {
        this.state = state;
        notifyPropertyChanged(BR.state);
    }

    public void clearImage() {
        imageUrl = null;
        imageUri =  null;
        imageData = null;
        rotationDegrees = null;
    }


    @Bindable
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        clearImage();
        this.imageUrl = imageUrl;
        setState(State.LOADING_IMAGE);
        notifyPropertyChanged(BR.imageUrl);
    }


    @Bindable
    public Uri getImageUri() {
        return imageUri;
    }
    public void setImageUri(Uri imageUri) {
        clearImage();
        this.imageUri = imageUri;
        setState(State.LOADING_IMAGE);
        notifyPropertyChanged(BR.imageUri);
    }


    @Bindable
    public byte[] getImageData() {
        return imageData;
    }
    @Bindable
    public Integer getRotationDegrees() {
        return rotationDegrees;
    }
    public void setImageData(byte[] imageData, Integer rotationDegrees) {
        clearImage();
        this.imageData = imageData;
        this.rotationDegrees = rotationDegrees;
        setState(State.LOADING_IMAGE);
        notifyPropertyChanged(BR.imageData);
        notifyPropertyChanged(BR.rotationDegrees);
    }

    @Override
    public void onImageLoaded(Bitmap image) {
        Timber.e("IMAGE LOADED!!!!!!!!!!!!!!!!!!!!!!!!");
        setState(State.ANALYZING_IMAGE);
    }

    @Override
    public void onFailure(Exception e) {
        Timber.e("IMAGE LOAD FAILED!!!!!!!!!!!!!!!!!!!!!!!!");
        setState(State.CAPTURING);
    }

    public enum State {
        CAPTURING,
        LOADING_IMAGE,
        ANALYZING_IMAGE,
        DISPLAYING_RESULTS,
    }
}
