package com.example.polyfit_app.Fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.polyfit_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SensorEventListener {
    TextView stepCount;
    int steps;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences userInf = getActivity().getSharedPreferences("userInf", Context.MODE_PRIVATE);
        stepCount=v.findViewById(R.id.stepCount);
        stepCount.setText("NULL");
        stepCount.setText("Your steps ::"+ steps);



        return v;
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        float[] values = sensorEvent.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }


        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps++;
            Log.e("PhayTran::",steps+"");
            stepCount.setText("Your steps ::"+ steps);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
