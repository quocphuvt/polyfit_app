package com.example.polyfit_app.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.polyfit_app.R;

public class StepTwoSignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ic_backToStepOneSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step_two_sign_up);
        connectView();
    }
    private void connectView(){
        ic_backToStepOneSignUp=findViewById(R.id.ic_backToStepOneSignUp);
        ic_backToStepOneSignUp.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ic_backToStepOneSignUp:
                super.onBackPressed();
                break;
        }
    }

}
