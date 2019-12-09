package com.example.polyfit_app.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.polyfit_app.adapter.SuggestedExercisesAdapter;
import com.example.polyfit_app.interfaces.ItemClickListener;
import com.example.polyfit_app.model.Exercise;
import com.example.polyfit_app.model.response.BodypartResponse;
import com.example.polyfit_app.model.response.ExerciseResponse;
import com.example.polyfit_app.R;
import com.example.polyfit_app.service.remote.BodypartsAPI;
import com.example.polyfit_app.service.remote.ExerciseAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.utils.Constants;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ExerciseDetailsActivity extends AppCompatActivity implements ItemClickListener {
    private TextView tv_sets, tv_reps, tv_restTime, tv_title, tv_introduction, tv_content;
    private FloatingActionButton fab_tips;
    private Toolbar toolbar;
    private ImageView iv_ex, iv_second_exercise_details;
    private YouTubePlayerView video_ex;
    private ExerciseAPI exerciseAPI;
    private BodypartsAPI bodypartsAPI;
    private Exercise exercise;
    private SharedPreferences.Editor saveTimePractice;
    private RecyclerView rv_suggested_exercise;
    private int bodyPartId;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    private int minutes=0;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private int oldTimePractice;

    private void initView() {
        tv_sets = findViewById(R.id.tv_sets_detail);
        tv_reps = findViewById(R.id.tv_reps_detail);
        tv_restTime = findViewById(R.id.tv_rest_time_detail);
        tv_title = findViewById(R.id.tv_title_ex_detail);
        tv_introduction = findViewById(R.id.tv_intro_ex_detail);
        iv_second_exercise_details = findViewById(R.id.iv_second_exercise_details);
        tv_content = findViewById(R.id.tv_content_ex_detail);
        iv_ex = findViewById(R.id.iv_exercise_details);
        video_ex = findViewById(R.id.video_exercise_details);
        fab_tips = findViewById(R.id.fab_tips);
        toolbar = findViewById(R.id.toolbar);
        rv_suggested_exercise = findViewById(R.id.rv_suggested_exercise);
    }

    @Override
    public void finish() {
        super.finish();
        video_ex.release();
        saveTimePractice = getSharedPreferences(Constants.TIME_PRACTICE,MODE_PRIVATE).edit();
        saveTimePractice.putInt(Constants.TIME_PRACTICE,oldTimePractice+minutes);
        saveTimePractice.apply();
        timeSwapBuff += timeInMilliseconds;
        customHandler.removeCallbacks(updateTimerThread);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        initView();
        SharedPreferences sharedPreferences =getSharedPreferences(Constants.TIME_PRACTICE,MODE_PRIVATE);
        oldTimePractice=sharedPreferences.getInt(Constants.TIME_PRACTICE,0);
        Log.e("MINUTES",sharedPreferences.getInt(Constants.TIME_PRACTICE,0)+"");
        startStopwatch();
        Retrofit retrofit = RetrofitClient.getInstance();
        exerciseAPI = retrofit.create(ExerciseAPI.class);
        bodypartsAPI = retrofit.create(BodypartsAPI.class);
        Intent i = getIntent();
        int exId = i.getIntExtra("id", 0);
        bodyPartId = i.getIntExtra("bodyPartId", 0);
        String bodypart = i.getStringExtra("part");
        toolbar.setTitle(bodypart);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.getExerciseData(exId);
        this.getSuggestedExercises(bodyPartId);

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
                powerMenu.showAtLocation(fab_tips, Gravity.CENTER_VERTICAL, 0, 0);
            }
        });
    }

    private void getSuggestedExercises(int bodypartId) { //TODO: Set data for suggested exercises
        Call<BodypartResponse> bodypartResponseCall = bodypartsAPI.getDataOfBodypart(bodypartId);
        bodypartResponseCall.enqueue(new Callback<BodypartResponse>() {
            @Override
            public void onResponse(Call<BodypartResponse> call, Response<BodypartResponse> response) {
                if (response.isSuccessful()) {
                    BodypartResponse bodypartResponse = response.body();
                    if (bodypartResponse.getStatus() == 0) {
                        SuggestedExercisesAdapter suggestedExercisesAdapter = new SuggestedExercisesAdapter(bodypartResponse.getObject().getExercises(), ExerciseDetailsActivity.this, ExerciseDetailsActivity.this);
                        rv_suggested_exercise.setHasFixedSize(true);
                        rv_suggested_exercise.setLayoutManager(new LinearLayoutManager(ExerciseDetailsActivity.this, RecyclerView.HORIZONTAL, false));
                        rv_suggested_exercise.setAdapter(suggestedExercisesAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<BodypartResponse> call, Throwable t) {

            }
        });
    }

    private void getExerciseData(int exerciseId) {
        Call<ExerciseResponse> exerciseResponseCall = exerciseAPI.getExerciseDetails(exerciseId);
        exerciseResponseCall.enqueue(new Callback<ExerciseResponse>() {
            @Override
            public void onResponse(Call<ExerciseResponse> call, Response<ExerciseResponse> response) {
                if (response.isSuccessful()) {
                    ExerciseResponse exerciseResponse = response.body();
                    if (exerciseResponse.getStatus() == 0) {
                        exercise = exerciseResponse.getData();
                        if (exercise.getTips().equals("null")) {
                            fab_tips.hide();
                        }

                        if (exercise.getImage_url().equals("null")) {
                            video_ex.setVisibility(View.VISIBLE);
                            video_ex.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                                    super.onReady(youTubePlayer);
                                    String videoUrl = exercise.getVideo_url();
                                    String videoId = null;
                                    if (videoUrl.length() > 28) {
                                        videoId = videoUrl.substring(videoUrl.lastIndexOf("=") + 1, videoUrl.length());
                                    } else {
                                        videoId = videoUrl.substring(videoUrl.lastIndexOf("/") + 1, videoUrl.length());
                                    }
                                    youTubePlayer.loadVideo(videoId, 0);


                                }
                            });


                        } else if (exercise.getVideo_url().equals("null")) {
                            iv_ex.setVisibility(View.VISIBLE);
                            Glide.with(getApplicationContext()).load(exercise.getImage_url()).centerCrop().into(iv_ex);
                        } else {
                            video_ex.setVisibility(View.VISIBLE);
                            video_ex.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                                @Override
                                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                                    super.onReady(youTubePlayer);
                                    String videoUrl = exercise.getVideo_url();
                                    String videoId = null;
                                    if (videoUrl.length() > 28) {
                                        videoId = videoUrl.substring(videoUrl.lastIndexOf("=") + 1, videoUrl.length());
                                    } else {
                                        videoId = videoUrl.substring(videoUrl.lastIndexOf("/") + 1, videoUrl.length());
                                    }
                                    youTubePlayer.loadVideo(videoId, 0);
                                }
                            });
                            Glide.with(getApplicationContext()).load(exercise.getImage_url()).centerCrop().into(iv_second_exercise_details);
                        }

                        tv_reps.setText(exercise.getReps() + "");
                        tv_sets.setText(exercise.getSets() + "");
                        tv_restTime.setText(exercise.getRest() + "s");
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
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "Back success", Toast.LENGTH_SHORT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickItem(int id) {
        getExerciseData(id);
        getSuggestedExercises(bodyPartId);
    }

    @Override
    public void onClick(View view, int posittion) {

    }


    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int seconds = (int) (updatedTime / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            int milliseconds = (int) (updatedTime % 1000);

            String string = "";
            string += "" + String.format("%02d", minutes);
            string += ":" + String.format("%02d", seconds);
            string += ":" + String.format("%03d", milliseconds);
//            Log.e("PhayDev", string);
            updatedTime = timeSwapBuff + timeInMilliseconds;
            customHandler.postDelayed(this, 0);
        }
    };

    private void startStopwatch() {
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);
//        Log.e("PhayDev", startTime + "");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}