package com.example.polyfit_app.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.polyfit_app.R;

public class StepOneSignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ic_closeSignUpStepOne;
    LinearLayout nextStepSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step_one_sign_up);
        connectView();
    }

    private void connectView(){
        ic_closeSignUpStepOne=findViewById(R.id.ic_closeSignUpStepOne);
        ic_closeSignUpStepOne.setOnClickListener(this);
        nextStepSignUp=findViewById(R.id.nextStepSignUp);
        nextStepSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ic_closeSignUpStepOne:
                Log.e("PhayTran","Close stepOne");
                finish();
                break;
            case R.id.nextStepSignUp:
                startActivity(new Intent(StepOneSignUpActivity.this,StepTwoSignUpActivity.class));
                break;
        }
    }
}
