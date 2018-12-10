package com.xpn.foodinfo;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.xpn.foodinfo.repositories.image.FirebaseImageService;
import com.xpn.foodinfo.repositories.preference.local.SharedPrefPreferenceService;
import com.xpn.foodinfo.repositories.user.FirebaseUserService;
import com.xpn.foodinfo.services.image.ImageService;
import com.xpn.foodinfo.services.preference.PreferenceService;
import com.xpn.foodinfo.services.user.UserService;

import lombok.Getter;


public class Dependency {
    private static final String SHARED_PREFS_NAME = "cache";
    private static final String IMAGES_LOCATION = "images";

    @Getter private final PreferenceService preferenceService;
    @Getter private final UserService userService;
    @Getter private final ImageService imageService;

    Dependency(Context context) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
        final StorageReference imageStorageReference = FirebaseStorage.getInstance().getReference(IMAGES_LOCATION);
        final DatabaseReference imageDatabaseReference = FirebaseDatabase.getInstance().getReference(IMAGES_LOCATION);

        this.preferenceService = new SharedPrefPreferenceService(sharedPreferences);
        this.userService = new FirebaseUserService();
        this.imageService = new FirebaseImageService(imageStorageReference, imageDatabaseReference);
    }
}
