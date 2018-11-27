package com.xpn.foodinfo.view.auth;

import android.app.Activity;
import android.content.Intent;

import com.firebase.ui.auth.AuthUI;
import com.xpn.foodinfo.R;

import java.util.Arrays;
import java.util.List;


public class Authentication {

    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.TwitterBuilder().build()
    );

    public static void startForResult(Activity activity, final int signInCode) {

        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls("https://foodinfo.github.io/FoodInfoAndroid/terms_and_conditions",
                                                    "https://foodinfo.github.io/FoodInfoAndroid/privacy_policy")
                        .setLogo(R.mipmap.ic_launcher)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .setAlwaysShowSignInMethodScreen(true)
                        .build()
                        .setAction(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_HOME)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP),
                signInCode);
    }
}
