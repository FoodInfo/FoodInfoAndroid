package com.xpn.foodinfo.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.xpn.foodinfo.R;

import java.util.Arrays;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 7701;

    private static final List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
//            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build(),
            new AuthUI.IdpConfig.GitHubBuilder().build()
//            new AuthUI.IdpConfig.TwitterBuilder().build()
    );

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
//                        .setTosAndPrivacyPolicyUrls("https://foodinfo.github.io/FoodInfoAndroid/privacy_policy",
//                                                    "https://foodinfo.github.io/FoodInfoAndroid/privacy_policy")
//                        .setLogo(R.drawable.my_great_logo)      // Set logo drawable
//                        .setTheme(R.style.MySuperAppTheme)      // Set theme
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                finish();
            } else {
                // Sign in failed
                Toast.makeText(this, "Please sign in to continue", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Block the user from going back if he is not signed in
    }
}
