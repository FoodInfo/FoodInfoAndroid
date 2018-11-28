package com.xpn.foodinfo.viewmodels.main.profile;

import android.databinding.Bindable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.xpn.foodinfo.viewmodels.BaseViewModel;
import com.xpn.foodinfo.viewmodels.util.SingleLiveEvent;
import com.xpn.foodinfo.BR;


public class ProfileVM extends BaseViewModel {
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private State navigationState = State.COLLAPSED;
    private SingleLiveEvent<Void> signOut = new SingleLiveEvent<>();


    public String getUserEmail() {
        if( user == null ) {
            onSignOut();
            return "";
        }
        return user.getEmail();
    }

    public String getUserName() {
        if( user == null ) {
            onSignOut();
            return "";
        }
        return user.getDisplayName();
    }

    public String getPhotoUrl() {
        if( user == null ) {
            onSignOut();
            return "";
        }
        if( user.getPhotoUrl() == null )
            return "error";
        return user.getPhotoUrl().toString();
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
