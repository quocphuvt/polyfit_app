package com.example.polyfit_app.Login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polyfit_app.R;

public class LoginMethod extends AppCompatActivity implements View.OnClickListener {
    LinearLayout loginWithEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_login_method);
        connectView();



    }
    private void connectView(){
        loginWithEmail=findViewById(R.id.loginWithEmail);
        loginWithEmail.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.loginWithEmail:
                Log.e("PhayTV:::","Intent");
                startActivity(new Intent(LoginMethod.this,LoginActivity.class));
                break;
        }
    }
}
