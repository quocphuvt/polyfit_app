package com.example.polyfit_app.Helpers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class FullScreen extends AppCompatActivity {
    private static FullScreen instance;

    public static void setFullScreen (Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Objects.requireNonNull(activity.getActionBar()).hide();
    }
}
