package com.example.polyfit_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polyfit_app.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class ExerciseDetailsActivity extends AppCompatActivity {
    private TextView tv_sets, tv_reps, tv_restTime, tv_title, tv_introduction, tv_content;
    private FloatingActionButton fab_tips;
    private Toolbar toolbar;

    private void initView() {
        tv_sets = findViewById(R.id.tv_sets_detail);
        tv_reps = findViewById(R.id.tv_reps_detail);
        tv_restTime = findViewById(R.id.tv_rest_time_detail);
        tv_title = findViewById(R.id.tv_title_ex_detail);
        tv_introduction = findViewById(R.id.tv_intro_ex_detail);
        tv_content = findViewById(R.id.tv_content_ex_detail);
        fab_tips = findViewById(R.id.fab_tips);
        toolbar = findViewById(R.id.toolbar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Back success", Toast.LENGTH_SHORT);
        }
        return super.onOptionsItemSelected(item);
    }
}
