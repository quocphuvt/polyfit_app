package com.example.polyfit_app.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.polyfit_app.MainActivity;
import com.example.polyfit_app.Model.Response;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Retrofit.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.polyfit_app.Utils.Validation.validateEmail;
import static com.example.polyfit_app.Utils.Validation.validateFields;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private CompositeSubscription mSubscriptions;
    EditText edt_username;
    EditText edt_password;
    LinearLayout btn_Login;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        initSharedPreferences();
        mSubscriptions = new CompositeSubscription();
        connectView();
    }


    private void connectView() {
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        btn_Login = findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(this);
    }


    private void loginProcess(String email, String password) {
        mSubscriptions.add(RetrofitClient.getRetrofit(email, password).login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError));
    }

    private void login() {
        setError();
        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String email = edt_username.getText().toString();
        String password = edt_password.getText().toString();

        int err = 0;

        if (!validateEmail(email)) {

            err++;
            edt_username.setError("Email should be valid !");
            progressDialog.dismiss();

        }

        if (!validateFields(password)) {

            err++;
            edt_password.setError("Password should not be empty !");
            progressDialog.dismiss();

        }

        if (err == 0) {

            loginProcess(email, password);
//            mProgressBar.setVisibility(View.VISIBLE);
            progressDialog.dismiss();

        } else {

//            showSnackBarMessage(view,"Enter Valid Details !");
        }
    }

    private void setError() {

        edt_username.setError(null);
        edt_password.setError(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_Login:
                Log.e("Login","login");
                login();
                break;
        }
    }

    private void handleResponse(Response response) {

//        mProgressBar.setVisibility(View.GONE);
        Log.e("PhayTran::",response.getMessage());
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.TOKEN, response.getToken());
        editor.putString(Constants.EMAIL, response.getMessage());
        editor.apply();

        edt_username.setText(null);
        edt_password.setText(null);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }

    private void handleError(Throwable error) {
        Log.e("PhayTran:::","ERROR"+error);
        /*mProgressBar.setVisibility(View.GONE);*/

        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody, Response.class);
                Log.e("PhayTRan",response.getMessage());
//                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            /*showSnackBarMessage(view,"Network Error !");*/
        }
    }


    private void initSharedPreferences() {

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
    }
}
