package com.example.polyfit_app.Service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;

import com.example.polyfit_app.Utils.Constants;

public class ForegroundServices extends Application {
    public static final String CHANNEL_ID = "PolyFit";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            SharedPreferences sharedPreferences=getSharedPreferences(Constants.LOGIN,MODE_PRIVATE);
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Step count",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            serviceChannel.enableVibration(false);
            serviceChannel.setVibrationPattern(new long[]{0L});
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
