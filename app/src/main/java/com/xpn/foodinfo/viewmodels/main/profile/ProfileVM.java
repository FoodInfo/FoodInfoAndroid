package com.xpn.foodinfo.viewmodels.main.profile;

import android.databinding.Bindable;

import com.xpn.foodinfo.BR;
import com.xpn.foodinfo.models.User;
import com.xpn.foodinfo.viewmodels.BaseViewModel;
import com.xpn.foodinfo.viewmodels.util.SingleLiveEvent;


public class ProfileVM extends BaseViewModel {
    private final SingleLiveEvent<Void> signOut = new SingleLiveEvent<>();
    private final User user;
    private State navigationState;

    ProfileVM(User user, State navigationState) {
        this.user = user;
        this.navigationState = navigationState;
    }


    public String getUserEmail() {
        return user.getEmail();
    }

    public String getUserName() {
        return user.getDisplayName();
    }

    public String getPhotoUrl() {
        return user.getPhotoUrl();
    }


    @Bindable
    public State getNavigationState() {
        return navigationState;
    }
    public void onFlipNavigationState() {
        navigationState = navigationState == State.COLLAPSED ? State.EXPANDED : State.COLLAPSED;
        notifyPropertyChanged(BR.navigationState);
    }


    public void onSignOut() {
        signOut.call();
    }
    public SingleLiveEvent <Void> onSignOutListener() {
        return signOut;
    }


    public enum State {
        COLLAPSED,
        EXPANDED,
    }
}
