package com.xpn.foodinfo.ui.auth;

import android.app.Activity;
import android.content.Intent;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;


public class Authentication {

    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
//            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build()
//            new AuthUI.IdpConfig.GitHubBuilder().build()
//            new AuthUI.IdpConfig.TwitterBuilder().build()
    );

    public static void startForResult(Activity activity, final int signInCode) {

        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
//                        .setTosAndPrivacyPolicyUrls("https://foodinfo.github.io/FoodInfoAndroid/privacy_policy",
//                                                    "https://foodinfo.github.io/FoodInfoAndroid/privacy_policy")
//                        .setLogo(R.drawable.my_great_logo)      // Set logo drawable
//                        .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build()
                        .setAction(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                signInCode);
    }
}
