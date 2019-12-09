package com.example.polyfit_app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polyfit_app.activity.login.LoginMethod;
import com.example.polyfit_app.R;
import com.example.polyfit_app.model.User;
import com.example.polyfit_app.utils.Constants;
import com.example.polyfit_app.utils.Helpers;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private ImageView logo;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        logo = findViewById(R.id.logo);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        logo.startAnimation(animation);
        User user = Helpers.getUserFromPreferences(this);
        if(user == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent mainIntent = new Intent(SplashScreen.this, LoginMethod.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            Intent intentToMain = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intentToMain);
            finish();
        }
    }
}

