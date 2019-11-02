package com.example.polyfit_app.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.polyfit_app.Model.StepCount;
import com.example.polyfit_app.Service.local.PolyfitDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hades on 01,November,2019
 **/
public class SaveStepCount extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH");
        StepCount stepCount=new StepCount();
        stepCount.setHour(Integer.parseInt(simpleDateFormat.format(date)));
//        stepCount.setStep();
        PolyfitDatabase.getInstance(context).stepDAO().insert();
    }
}
