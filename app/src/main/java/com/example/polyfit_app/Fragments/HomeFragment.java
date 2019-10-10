package com.example.polyfit_app.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.polyfit_app.Activity.ExercisesActivity;
import com.example.polyfit_app.Model.Excercise;
import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.remote.PolyFitService;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    PolyFitService polyFitService;
    TextView tv_UserName, tv_startDate, tv_height, tv_weight, tv_bmi;
    ImageView btn;
    ImageView imv_avatar;
    PhotoView viewAvatar;
    LinearLayout changeAvatar;
    TextView tv_ChangeAvatar;
    AlertDialog mDialog;
    View mView;
    AlertDialog.Builder mBuilder;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
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
        connectView(view);
        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @SuppressLint("InflateParams")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_avatar:
                Toast.makeText(getActivity(), "Click on avatar!!!", Toast.LENGTH_SHORT).show();
//        Picasso.get().load(linkAvatar).into(photoView);
                mBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
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
                        Crop.pickImage(getActivity());
                    }
                });
                break;
            case R.id.btn:
                startActivity(new Intent(getActivity(), ExercisesActivity.class));
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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
                    assert users != null;
                    setData(users.get(0).getDisplay_name(), users.get(0).getCreate_at(), users.get(0).getHeight(), users.get(0).getWeight(), users.get(0).getBmi());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @SuppressLint("InflateParams")
    private void connectView(View view) {
        tv_UserName = view.findViewById(R.id.tv_UserName);
        tv_startDate = view.findViewById(R.id.tv_startDate);
        tv_height = view.findViewById(R.id.tv_height);
        tv_weight = view.findViewById(R.id.tv_weight);
        tv_bmi = view.findViewById(R.id.tv_BMI);
        imv_avatar = view.findViewById(R.id.imv_avatar);
        imv_avatar.setOnClickListener(this);
        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(this);

    }

    private void setData(String userName, String startDate, Float height, Float weight, Float bmi) {
        tv_UserName.setText(userName);
        tv_startDate.setText(startDate);
        tv_height.setText(String.valueOf(height));
        tv_weight.setText(String.valueOf(weight));
        tv_bmi.setText(String.valueOf(bmi));
    }
}
