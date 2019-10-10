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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Adapter.ChallengeAdapter;
import com.example.polyfit_app.Model.Challenge;
import com.example.polyfit_app.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements SensorEventListener {
    TextView stepCount;
    int steps;
    private RecyclerView rv_challenges;
    private ArrayList<Challenge> sampleChanlleges;
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
        rv_challenges = v.findViewById(R.id.rv_challenges);
        SharedPreferences userInf = getActivity().getSharedPreferences("userInf", Context.MODE_PRIVATE);
        stepCount=v.findViewById(R.id.stepCount);
        stepCount.setText("NULL");
        stepCount.setText("Your steps ::"+ steps);

        sampleChanlleges = new ArrayList<>();
        sampleChanlleges.add(new Challenge("30 ngày thanh lọc", "Thử thách 30 ngày thanh lọc cơ thể", ""));
        sampleChanlleges.add(new Challenge("16 ngày giảm cân", "Thử thách 16 ngảy giảm cân", ""));
        sampleChanlleges.add(new Challenge("1 ngày ăn kiêng", "Thử thách 1 ngày thanh lọc cơ thể", ""));
        renderChallenges(sampleChanlleges);

        return v;
    }

    private void renderChallenges (ArrayList<Challenge> challenges) {
        ChallengeAdapter challengeAdapter = new ChallengeAdapter(challenges, getContext());
        rv_challenges.setHasFixedSize(true);
        rv_challenges.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_challenges.setAdapter(challengeAdapter);
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
