package com.xpn.foodinfo.viewmodels.camera;

import android.databinding.Bindable;
import android.graphics.Bitmap;
import android.net.Uri;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.services.image.ImageService;
import com.xpn.foodinfo.services.user.UserService;
import com.xpn.foodinfo.view.util.image.loading.BindingAdapters;
import com.xpn.foodinfo.viewmodels.BaseViewModel;

import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;
import timber.log.Timber;


@RequiredArgsConstructor
public class CameraVM extends BaseViewModel implements BindingAdapters.ImageListener {

    private final UserService userService;
    private final ImageService imageService;


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

        uploadImage(imageUri);
    }

    private void uploadImage(Uri imageUri) {
        String uriString = imageUri.toString();
        String imageName = uriString.substring( uriString.lastIndexOf('/')+1);
        addSubscription(
                imageService.upload(userService.getCurrentUser(), imageName, new Date(), imageUri)
                        .flatMap(image -> imageService.add(image).toSingleDefault(true))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                obs -> Timber.e("Uploaded and inserted into the database: %s", imageName),
                                Timber::e
                        ));
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
        setState(State.ANALYZING_IMAGE);
    }

    @Override
    public void onFailure(Exception e) {
        setState(State.CAPTURING);
    }

    public enum State {
        CAPTURING,
        LOADING_IMAGE,
        ANALYZING_IMAGE,
        DISPLAYING_RESULTS,
    }
}
