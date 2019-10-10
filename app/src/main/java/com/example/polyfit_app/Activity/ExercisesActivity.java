package com.example.polyfit_app.Activity;

import android.os.Bundle;

import com.example.polyfit_app.Adapter.ExcerciseAdapter;
import com.example.polyfit_app.Model.Excercise;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.polyfit_app.R;

import java.util.ArrayList;

public class ExercisesActivity extends AppCompatActivity {
    private ArrayList<Excercise> excercises;
    private RecyclerView rv_exercises;

    private void initView() {
        rv_exercises = findViewById(R.id.rv_exercises);
    }

    private void setSampleExercises() {
        excercises = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            excercises.add(new Excercise("Fit Arms " + i, ""));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Arms");

        initView();
        setSampleExercises();
        rv_exercises.setHasFixedSize(true);
        rv_exercises.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        setDataForExcerciseList(excercises);
    }

    private void setDataForExcerciseList(ArrayList<Excercise> excercises) {
        ExcerciseAdapter excerciseAdapter = new ExcerciseAdapter(excercises, this);
        ;       rv_exercises.setAdapter(excerciseAdapter);
    }
}
