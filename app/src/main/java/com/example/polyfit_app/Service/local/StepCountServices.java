package com.example.polyfit_app.Service.local;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.polyfit_app.Activity.MainActivity;
import com.example.polyfit_app.R;

import static com.example.polyfit_app.Service.ForegroundServices.CHANNEL_ID;

public class StepCountServices extends Service implements SensorEventListener {
    boolean isRunning = false;
    int step = 0;
    int id=99;
    double MagnitudePrevious = 0;
    SensorManager sensorManager;
    NotificationCompat.Builder notification;
    PendingIntent pendingIntent;
    NotificationManager notificationManager;
    Vibrator vibrator;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("PhayTran")
                .setContentText("Your step today " + step)
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(id,notification.build());
        isRunning = true;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            Log.e("PhayTran", "not found sensor!!!");
        }
        startForeground(id, notification.build());
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (isRunning) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            double Magnitude = Math.sqrt(x * x + y * y + z * z);
            double MagnitudeDenta = Magnitude - MagnitudePrevious;
            if (MagnitudeDenta > 20) {
                step++;
            }
            SharedPreferences.Editor editor=getSharedPreferences("StepCount",MODE_PRIVATE).edit();
            editor.putString("Step",step+"");
            editor.apply();
//            Log.e("PhayTran", "Running " + step);
                notification.setContentText("Your step today " + step);
                notification.setSmallIcon(R.drawable.logo);
                notificationManager.notify(id,notification.build());

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    private void disableVibrate(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,"PolyFit", NotificationManager.IMPORTANCE_HIGH);
            //Disabling vibration!
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
            notification = new NotificationCompat.Builder(this, CHANNEL_ID);

        } else {
            notification = new NotificationCompat.Builder(this);
            notification.setVibrate(new long[]{0L});
        }
    }

}
