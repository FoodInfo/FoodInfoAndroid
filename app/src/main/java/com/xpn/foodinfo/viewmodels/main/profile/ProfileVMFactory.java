package com.xpn.foodinfo.viewmodels.main.profile;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.xpn.foodinfo.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ProfileVMFactory implements ViewModelProvider.Factory {
    private final User user;

    public ProfileVMFactory(FirebaseUser user) {
        this(new User(user.getUid(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPhotoUrl() == null ? "error" : user.getPhotoUrl().toString()));
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ProfileVM(user, ProfileVM.State.COLLAPSED);
    }
}
