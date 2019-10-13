package com.example.polyfit_app.Activity.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polyfit_app.Activity.MainActivity;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.remote.PolyFitService;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CompositeSubscription mSubscriptions = new CompositeSubscription();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    EditText edt_username;
    EditText edt_password;
    TextView tvSignUp;
    LinearLayout btn_Login;
    PolyFitService polyFitService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        connectView();
        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void connectView() {
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_Login = findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(this);
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(this);
    }

    private void loginUser(String userName, String password) {
        if(userName.equals("dev")) { //For dev testing
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        mSubscriptions.add(polyFitService.loginUser(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (s.contains("User does not exist")) {
                            Toast.makeText(LoginActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                            Log.e("PhayTV::", s);
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.LOGIN, MODE_PRIVATE).edit();
                            editor.putString("username", userName);
                            editor.putString("password", password);
                            editor.putString("token", s);
                            editor.apply();
                            progressDialog.dismiss();
                            Log.e("PhayTran", "username:" + userName + "\n" + "password" + password);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                }));
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                Log.e("Login", "login");
                loginUser(edt_username.getText().toString(), edt_password.getText().toString());
                break;
            case R.id.tvSignUp:
                startActivity(new Intent(LoginActivity.this, StepOneSignUpActivity.class));
                break;
        }
    }

}
