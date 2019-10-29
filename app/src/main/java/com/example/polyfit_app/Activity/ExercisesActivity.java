package com.example.polyfit_app.Activity;

import android.os.Bundle;
import android.view.View;

import com.example.polyfit_app.Adapter.ExcercisesAdapter;
import com.example.polyfit_app.Interface.ItemClickListener;
import com.example.polyfit_app.Model.Exercise;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.Model.Responses.ExerciseResponse;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.remote.ExerciseAPI;
import com.example.polyfit_app.Service.remote.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExercisesActivity extends AppCompatActivity implements ItemClickListener {
    private ArrayList<Exercise> exercises;
    private RecyclerView rv_exercises;
    private ExerciseAPI exerciseAPI;

    private void initView() {
        rv_exercises = findViewById(R.id.rv_exercises);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Arms");
        initView();

        Retrofit retrofit = RetrofitClient.getInstance();
        exerciseAPI = retrofit.create(ExerciseAPI.class);
        this.getExerciseData();
    }

    private void getExerciseData() {
        Call<ExerciseResponse> exerciseResponseCall = exerciseAPI.getAllExercise();
        exerciseResponseCall.enqueue(new Callback<ExerciseResponse>() {
            @Override
            public void onResponse(Call<ExerciseResponse> call, Response<ExerciseResponse> response) {
                if(response.isSuccessful()) {
                    ExerciseResponse exerciseResponse = response.body();
                    if(exerciseResponse.getStatus() == 0) {
                        setDataForExcerciseList(exerciseResponse.getResponse());
                    }
                }
            }

            @Override
            public void onFailure(Call<ExerciseResponse> call, Throwable t) {

            }
        });
    }

    private void setDataForExcerciseList(ArrayList<Exercise> exercises) {
        rv_exercises.setHasFixedSize(true);
        rv_exercises.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        ExcercisesAdapter excercisesAdapter = new ExcercisesAdapter(exercises, this, this);
        rv_exercises.setAdapter(excercisesAdapter);
    }

    @Override
    public void onClickItem(int id) {
//        showDishBottomSheet();
    }

    @Override
    public void onClick(View view, int posittion) {

    }
}
