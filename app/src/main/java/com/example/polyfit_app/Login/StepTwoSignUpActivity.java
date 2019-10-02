package com.example.polyfit_app.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Retrofit.PolyFitService;
import com.example.polyfit_app.Retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class StepTwoSignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ic_backToStepOneSignUp;
    LinearLayout btn_SignUpNow;
    EditText edt_heigth,edt_weigth;
    RadioButton rb_Male,rb_Female;
    CheckBox cb_Accept;
    PolyFitService polyFitService;
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step_two_sign_up);
        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
        connectView();
    }
    private void connectView(){
        ic_backToStepOneSignUp=findViewById(R.id.ic_backToStepOneSignUp);
        ic_backToStepOneSignUp.setOnClickListener(this);
        btn_SignUpNow=findViewById(R.id.btn_SignUpNow);
        btn_SignUpNow.setOnClickListener(this);
        edt_heigth=findViewById(R.id.edt_height);
        edt_weigth=findViewById(R.id.edt_weight);
        rb_Male=findViewById(R.id.rb_Male);
        rb_Female=findViewById(R.id.rb_female);
        cb_Accept=findViewById(R.id.cb_Accept);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ic_backToStepOneSignUp:
                super.onBackPressed();
                break;
            case R.id.btn_SignUpNow:

                if(edt_heigth.getText().toString().isEmpty()||edt_weigth.getText().toString().isEmpty()){
                    Toast.makeText(this, "Please enter full your information", Toast.LENGTH_SHORT).show();
                }else if(!rb_Male.isChecked()&&!rb_Female.isChecked()){
                    Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
                }else if(!cb_Accept.isChecked()){
                    Toast.makeText(this, "Please accept my policy", Toast.LENGTH_SHORT).show();
                }else {
                    Bundle extras = getIntent().getExtras();
                    assert extras != null;
                    String displayName=extras.getString("displayName");
                    String userName=extras.getString("userName");
                    String password=extras.getString("password");
                    Float height=Float.valueOf(edt_heigth.getText().toString());
                    Float weight=Float.valueOf(edt_weigth.getText().toString());
                    Float bmi=weight/(height*height);

                    Log.e("PhayTran","height"+height+":::"+"weight"+weight+"\n"+"BMI"+bmi);

                    int gender = 2;
                    if(rb_Male.isChecked()){
                        gender=1;
                    }else if(rb_Female.isChecked()){
                        gender=0;
                    }
                    Log.e("PhayTran","gender ::: "+gender);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    String currentDate=formatter.format(date);
                    registerUser(displayName,userName,password,weight,height,bmi,gender,currentDate);

                }

                break;
        }
    }
    private void registerUser(String displayName,String userName, String password,Float weight,Float height,Float bmi,int gender,String createAt) {
        mSubscriptions.add(polyFitService.registerUser(displayName,userName, password,weight,height,bmi,gender,createAt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.contains("Register Success!")) {
                            getUserByUserName(userName);
                            startActivity(new Intent(StepTwoSignUpActivity.this, LoginActivity.class));
                        }else
                            Toast.makeText(StepTwoSignUpActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }


    private void addHistory(float user_bmi,int user_id) {
        mSubscriptions.add(polyFitService.addHistory(user_bmi,user_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.contains("Add Success!")) {
                            startActivity(new Intent(StepTwoSignUpActivity.this, LoginActivity.class));
                        }else
                            Toast.makeText(StepTwoSignUpActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void getUserByUserName(String userName) {
      polyFitService.getUserByUserName(userName).enqueue(new Callback<String>() {
          @Override
          public void onResponse(Call<String> call, Response<String> response) {
              if(response.isSuccessful()){
                  Gson gson = new Gson();
                  String jsonOutput = response.body();
                  Type listType = new TypeToken<List<User>>(){}.getType();
                  List<User> users = gson.fromJson(jsonOutput, listType);
                  Log.e("Phaytv","Success::"+response.body());
                  Log.e("PhayTv",users.get(0).getId()+"");
                  addHistory(users.get(0).getBmi(),users.get(0).getId());
              }

          }

          @Override
          public void onFailure(Call<String> call, Throwable t) {

          }
      });
    }

}
