package com.xpn.foodinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.xpn.foodinfo.R;
import com.xpn.foodinfo.ui.auth.Authentication;


public class SplashScreenActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 7701;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreen);
        super.onCreate(savedInstanceState);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if( currentUser == null )   startLogin();
        else                        startApp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK)
                startApp();
            else {
                // Failed to log in or canceled by user
                Toast.makeText(this, "Please sign in to continue", Toast.LENGTH_SHORT).show();
                startLogin();
            }
        }
    }


    private void startApp() {
        MainActivity.start(this);
        finish();
    }

    private void startLogin() {
        Authentication.startForResult(this, RC_SIGN_IN);
    }
}
