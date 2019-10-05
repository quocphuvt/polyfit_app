package com.example.polyfit_app.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.polyfit_app.MainActivity;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Retrofit.PolyFitService;
import com.example.polyfit_app.Retrofit.RetrofitClient;

import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class StepOneSignUpActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ic_closeSignUpStepOne;
    LinearLayout nextStepSignUp;

    EditText edtDisplayName, edtUsername, edtPassword, edtRetypePassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_step_one_sign_up);
        connectView();
    }

    private void connectView() {
        ic_closeSignUpStepOne = findViewById(R.id.ic_closeSignUpStepOne);
        ic_closeSignUpStepOne.setOnClickListener(this);
        nextStepSignUp = findViewById(R.id.nextStepSignUp);
        nextStepSignUp.setOnClickListener(this);
        edtDisplayName = findViewById(R.id.edt_DisplayName);
        edtUsername = findViewById(R.id.edt_Username);
        edtPassword = findViewById(R.id.edt_Password);
        edtRetypePassword = findViewById(R.id.edt_ReTypePassword);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ic_closeSignUpStepOne:
                Log.e("PhayTran", "Close stepOne");
                finish();
                break;
            case R.id.nextStepSignUp:
                Intent intent = new Intent(StepOneSignUpActivity.this, StepTwoSignUpActivity.class);
                if (edtDisplayName.getText().toString().isEmpty() || edtUsername.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtRetypePassword.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter full your information", Toast.LENGTH_SHORT).show();
                } else if (!edtPassword.getText().toString().equals(edtRetypePassword.getText().toString())) {
                    Toast.makeText(this, "Password and retype password not matched", Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra("displayName", edtDisplayName.getText().toString());
                    intent.putExtra("userName", edtUsername.getText().toString());
                    intent.putExtra("password", edtPassword.getText().toString());
                    startActivity(intent);
                }


//                String displayName=edtDisplayName.getText().toString();
//                String username=edtUsername.getText().toString();
//                String password=edtPassword.getText().toString();
//                String reType=edtRetypePassword.getText().toString();
//                if(displayName.isEmpty()){
//                    Toast.makeText(this, "Please enter display name", Toast.LENGTH_SHORT).show();
//                }else if(username.isEmpty()){
//                    Toast.makeText(this, "Please enter user name", Toast.LENGTH_SHORT).show();
//                }
//                else if(password.isEmpty() || !password.equals(reType) || reType.isEmpty()){
//                    Toast.makeText(this, "Please check password", Toast.LENGTH_SHORT).show();
//                }else {
//                    registerUser(displayName,username,password);
//                }
                break;
        }
    }


}
