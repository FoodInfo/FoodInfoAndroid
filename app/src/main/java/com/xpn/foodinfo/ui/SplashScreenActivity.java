package com.xpn.foodinfo.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.ui.auth.LoginActivity;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreen);
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if( currentUser == null ) {
            LoginActivity.start(this);
        }
        else {
            MainActivity.start(this);
        }
    }
}
