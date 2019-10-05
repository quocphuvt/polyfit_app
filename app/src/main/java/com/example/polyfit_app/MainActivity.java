package com.example.polyfit_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.polyfit_app.Login.LoginActivity;
import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.Retrofit.PolyFitService;
import com.example.polyfit_app.Retrofit.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.soundcloud.android.crop.Crop;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    PolyFitService polyFitService;
    TextView tv_UserName, tv_startDate, tv_height, tv_weight, tv_bmi;
    ImageView imv_avatar;
    PhotoView viewAvatar;
    LinearLayout changeAvatar;
    TextView tv_ChangeAvatar;
    AlertDialog mDialog;
    View mView;
    AlertDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
        Log.e("PhayTRan", sharedPreferences.getString("token", ""));
        JSONObject tokenObject = null;
        String token = null;
        try {
            tokenObject = new JSONObject(sharedPreferences.getString("token", ""));
            token = tokenObject.getString("token");
            Log.e("phayTran", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        getUserByUserName(sharedPreferences.getString("username", ""));
        connectView();
    }

    private void getUserByUserName(String username) {
        polyFitService.getUserByUserName(username).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String jsonOutput = response.body();
                    Type listType = new TypeToken<List<User>>() {
                    }.getType();
                    List<User> users = gson.fromJson(jsonOutput, listType);
                    Log.e("Phaytv", "Success::" + response.body());
//                    Log.e("PhayTv",users.get(0).getId()+"\n"+"username"+users.get(0).getUsername()+"\n"+"date"+users.get(0).getCreate_at()+"\n"+"height"+users.get(0).getHeight()+"\n"+"weight"+users.get(0).getWeight()+"\n"+"BMI"+users.get(0).getBmi());
                    setData(users.get(0).getDisplay_name(), users.get(0).getCreate_at(), users.get(0).getHeight(), users.get(0).getWeight(), users.get(0).getBmi());
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @SuppressLint("InflateParams")
    private void connectView() {
        tv_UserName = findViewById(R.id.tv_UserName);
        tv_startDate = findViewById(R.id.tv_startDate);
        tv_height = findViewById(R.id.tv_height);
        tv_weight = findViewById(R.id.tv_weight);
        tv_bmi = findViewById(R.id.tv_BMI);
        imv_avatar = findViewById(R.id.imv_avatar);
        imv_avatar.setOnClickListener(this);
    }

    private void setData(String userName, String startDate, Float height, Float weight, Float bmi) {
        tv_UserName.setText(userName);
        tv_startDate.setText(startDate);
        tv_height.setText(String.valueOf(height));
        tv_weight.setText(String.valueOf(weight));
        tv_bmi.setText(String.valueOf(bmi));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_avatar:
                Toast.makeText(this, "Click on avatar!!!", Toast.LENGTH_SHORT).show();
//        Picasso.get().load(linkAvatar).into(photoView);
                mBuilder = new AlertDialog.Builder(MainActivity.this);
                mView = getLayoutInflater().inflate(R.layout.dialog_view_avatar, null, false);
                viewAvatar = mView.findViewById(R.id.avatarView);
                changeAvatar = mView.findViewById(R.id.changeAvatar);
                tv_ChangeAvatar = mView.findViewById(R.id.tv_ChangeAvatar);
                mBuilder.setView(mView);
                mDialog = mBuilder.create();
                mDialog.show();
                changeAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Crop.pickImage(MainActivity.this);
                    }
                });
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Crop.REQUEST_PICK) {
                Uri source_uri = data.getData();
                Uri destination_uri = Uri.fromFile(new File(getCacheDir(), "cropped"));
                Crop.of(source_uri, destination_uri).asSquare().start(MainActivity.this);
                viewAvatar.setImageURI(Crop.getOutput(data));
                Log.e("aaa", "abc");
                tv_ChangeAvatar.setText("Apply");
                changeAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        updateAvtar();
//                        imv_avatar.setImageURI(Crop.getOutput(data));

                    }
                });

            } else if (requestCode == Crop.REQUEST_CROP) {
                handle_crop(resultCode, data);
            }
        }

    }

    private void handle_crop(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            viewAvatar.setImageURI(Crop.getOutput(data));
        }
    }

}
