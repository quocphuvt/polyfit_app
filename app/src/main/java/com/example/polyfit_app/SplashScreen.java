package com.example.polyfit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.polyfit_app.Activity.Base.FullScreenActivity;
import com.example.polyfit_app.Activity.Main2Activity;
import com.example.polyfit_app.Login.LoginMethod;
import com.example.polyfit_app.Utils.Constants;

public class SplashScreen extends FullScreenActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    ImageView logo;
    Animation animation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logo = findViewById(R.id.logo);
        animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);
        logo.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                if (!username.isEmpty()) {
                    Intent intentToMain = new Intent(SplashScreen.this, Main2Activity.class);
                    startActivity(intentToMain);
                    finish();
                } else {
                    Intent mainIntent = new Intent(SplashScreen.this, LoginMethod.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected int getLayoutResouceId() {
        return R.layout.activity_splash_screen;
    }
}

