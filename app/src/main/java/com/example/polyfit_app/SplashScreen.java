package com.example.polyfit_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.UserData;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.polyfit_app.Activity.Main2Activity;
import com.example.polyfit_app.Helpers.FullScreen;
import com.example.polyfit_app.Login.LoginMethod;
import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.example.polyfit_app.Service.local.UserDAO;
import com.example.polyfit_app.Utils.Constants;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    ImageView logo;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

//        AsyncTask.execute(new Runnable() { // Insert new User in AsyncTask
//            @Override
//            public void run() {
//                PolyfitDatabase polyfitDatabase = PolyfitDatabase.getInstance(SplashScreen.this);
//                User user = new User("quocphuvt", "123", "quoc phu", 75f, 175f, 1, true, "0987274110");
//                polyfitDatabase.userDAO().registerUser(user);
//                Log.d("iconne", "run: RUN NE");
//            }
//        });
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
}

