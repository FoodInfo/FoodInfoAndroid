package com.xpn.foodinfo.repositories.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xpn.foodinfo.models.User;
import com.xpn.foodinfo.services.user.UserService;


public class FirebaseUserService implements UserService {

    @Override
    public User getCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if( user == null )
            throw new IllegalStateException("There are no users logged in");

        return new User(user.getUid(), user.getDisplayName(), user.getEmail(), user.getPhotoUrl().toString());
    }
}
