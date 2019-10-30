package com.example.polyfit_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.polyfit_app.Model.Exercise;
import com.example.polyfit_app.Model.Responses.ExerciseResponse;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.remote.ExerciseAPI;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.skydoves.powermenu.CircularEffect;
import com.skydoves.powermenu.MenuAnimation;
import com.skydoves.powermenu.PowerMenu;
import com.skydoves.powermenu.PowerMenuItem;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExerciseDetailsActivity extends AppCompatActivity {
    private TextView tv_sets, tv_reps, tv_restTime, tv_title, tv_introduction, tv_content;
    private FloatingActionButton fab_tips;
    private Toolbar toolbar;
    private ImageView iv_ex;
    private YouTubePlayerView video_ex;
    private ExerciseAPI exerciseAPI;
    private Exercise exercise;

    private void initView() {
        tv_sets = findViewById(R.id.tv_sets_detail);
        tv_reps = findViewById(R.id.tv_reps_detail);
        tv_restTime = findViewById(R.id.tv_rest_time_detail);
        tv_title = findViewById(R.id.tv_title_ex_detail);
        tv_introduction = findViewById(R.id.tv_intro_ex_detail);
        tv_content = findViewById(R.id.tv_content_ex_detail);
        iv_ex = findViewById(R.id.iv_exercise_details);
        video_ex = findViewById(R.id.video_exercise_details);
        fab_tips = findViewById(R.id.fab_tips);
        toolbar = findViewById(R.id.toolbar);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initView();
        Retrofit retrofit = RetrofitClient.getInstance();
        exerciseAPI = retrofit.create(ExerciseAPI.class);
        Intent i = getIntent();
        int exId = i.getIntExtra("id", 0);
        String bodypart = i.getStringExtra("part");
        toolbar.setTitle(bodypart);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.getExerciseData(exId);

        fab_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    PowerMenu powerMenu = new PowerMenu.Builder(ExerciseDetailsActivity.this)
                        .addItem(new PowerMenuItem(exercise.getTips(), false)) // add an item.
                        .setAnimation(MenuAnimation.SHOWUP_TOP_LEFT)
                        .setMenuRadius(10f) // sets the corner radius.
                        .setMenuShadow(10f) // sets the shadow.
                        .setCircularEffect(CircularEffect.BODY)
                        .setTextGravity(Gravity.CENTER)
                        .setTextTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD))
                        .setSelectedTextColor(Color.WHITE)
                        .setMenuColor(Color.WHITE)
                        .build();
                    powerMenu.showAtLocation(fab_tips, Gravity.BOTTOM, 370, 0);
            }
        });
    }

    private void getExerciseData(int exerciseId) {
        Call<ExerciseResponse> exerciseResponseCall = exerciseAPI.getExerciseDetails(exerciseId);
        exerciseResponseCall.enqueue(new Callback<ExerciseResponse>() {
            @Override
            public void onResponse(Call<ExerciseResponse> call, Response<ExerciseResponse> response) {
                if(response.isSuccessful()) {
                    ExerciseResponse exerciseResponse = response.body();
                    if(exerciseResponse.getStatus() == 0) {
                        exercise = exerciseResponse.getData();
                        if(exercise.getTips().equals("null")) {
                            fab_tips.hide();
                        }

                        if(exercise.getImage_url().equals("null")) {
                            video_ex.setVisibility(View.VISIBLE);
                            video_ex.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                                    super.onReady(youTubePlayer);
                                    String videoUrl = exercise.getVideo_url();
                                    String videoId = videoUrl.substring(videoUrl.indexOf("=") + 1, videoUrl.length());
                                    youTubePlayer.loadVideo(videoId, 0);
                                }
                            });
                        } else if(exercise.getVideo_url().equals("null")) {
                            iv_ex.setVisibility(View.VISIBLE);
                            Glide.with(ExerciseDetailsActivity.this).load(exercise.getImage_url()).centerCrop().into(iv_ex);
                        }

                        tv_reps.setText(exercise.getReps()+"");
                        tv_sets.setText(exercise.getSets()+"");
                        tv_restTime.setText(exercise.getRest()+"s");
                        tv_title.setText(exercise.getTitle());
                        tv_introduction.setText(exercise.getIntroduction());
                        tv_content.setText(exercise.getContent());
                    }
                }
            }

            @Override
            public void onFailure(Call<ExerciseResponse> call, Throwable t) {
                Log.d("err:", t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Back success", Toast.LENGTH_SHORT);
        }
        return super.onOptionsItemSelected(item);
    }
}
