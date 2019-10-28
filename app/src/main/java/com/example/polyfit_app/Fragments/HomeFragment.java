package com.example.polyfit_app.Fragments;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polyfit_app.Activity.ExercisesActivity;
import com.example.polyfit_app.Activity.Login.LoginActivity;
import com.example.polyfit_app.Adapter.DietsHomeAdapter;
import com.example.polyfit_app.Model.BodyParts;
import com.example.polyfit_app.Model.Challenge;
import com.example.polyfit_app.Activity.ReminderActivity;
import com.example.polyfit_app.Model.Responses.BodypartResponse;
import com.example.polyfit_app.Model.Responses.UserResponse;
import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.remote.BodypartsAPI;
import com.example.polyfit_app.Service.remote.DietsAPI;
import com.example.polyfit_app.Service.remote.PolyFitService;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;
import com.github.chrisbanes.photoview.PhotoView;
import com.soundcloud.android.crop.Crop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private RecyclerView rv_challenges;
    private ArrayList<Challenge> sampleChanlleges;
    private PolyFitService polyFitService;
    private TextView tv_UserName, tv_startDate, tv_height, tv_weight, tv_bmi;
    private ImageView iv_avatar, ic_reminder, iv_bg_abs, iv_bg_arms, iv_bg_leg, iv_bg_back, iv_bg_chest, iv_bg_morning, iv_bg_noon, iv_bg_night;
    private PhotoView viewAvatar;
    private LinearLayout changeAvatar, abs_exercise_layout, arms_exercise_layout, legs_excercise_layout, back_exercise_layout, chest_exercise_layout;
    private TextView tv_ChangeAvatar;
    private AlertDialog mDialog;
    private View mView;
    private AlertDialog.Builder mBuilder;
    private BodypartsAPI bodypartsAPI;
    private DietsAPI dietsAPI;

    public HomeFragment() {
    }

    private void connectView(View view) {
        tv_UserName = view.findViewById(R.id.tv_UserName);
        tv_startDate = view.findViewById(R.id.tv_startDate);
        tv_height = view.findViewById(R.id.tv_height);
        tv_weight = view.findViewById(R.id.tv_weight);
        tv_bmi = view.findViewById(R.id.tv_BMI);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        rv_challenges = view.findViewById(R.id.rv_challenges);
        iv_avatar.setOnClickListener(this);
        ic_reminder = view.findViewById(R.id.ic_reminder);
        ic_reminder.setOnClickListener(this);
        //Layout bodyparts
        abs_exercise_layout = view.findViewById(R.id.abs_exercise_layout);
        arms_exercise_layout = view.findViewById(R.id.arms_exercise_layout);
        legs_excercise_layout = view.findViewById(R.id.legs_exercise_layout);
        back_exercise_layout = view.findViewById(R.id.shoulder_back_exercise_layout);
        chest_exercise_layout = view.findViewById(R.id.chest_exercise_layout);
        abs_exercise_layout.setOnClickListener(this);
        arms_exercise_layout.setOnClickListener(this);
        legs_excercise_layout.setOnClickListener(this);
        back_exercise_layout.setOnClickListener(this);
        chest_exercise_layout.setOnClickListener(this);
        //Bodypart's image background
        iv_bg_abs = view.findViewById(R.id.image_bg_abs_home);
        iv_bg_arms = view.findViewById(R.id.image_bg_arms_home);
        iv_bg_back = view.findViewById(R.id.image_bg_back_home);
        iv_bg_chest = view.findViewById(R.id.image_bg_chest_home);
        iv_bg_leg = view.findViewById(R.id.image_bg_legs_home);
        //Meal's image background
        iv_bg_morning = view.findViewById(R.id.image_bg_morning);
        iv_bg_noon = view.findViewById(R.id.image_bg_noon);
        iv_bg_night = view.findViewById(R.id.image_bg_night);
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
        connectView(view);
        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
        bodypartsAPI = retrofit.create(BodypartsAPI.class);
        dietsAPI = retrofit.create(DietsAPI.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
        JSONObject tokenObject = null;
        String token = null;
        try {
            tokenObject = new JSONObject(sharedPreferences.getString("token", ""));
            token = tokenObject.getString("token");
            Log.e("phayTran", token);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        int userId = sharedPreferences.getInt("id", 0);


        this.getUserById(userId);
        this.getAllBodyParts();
        this.setBackgroundImageForMeals();

//        sampleChanlleges = new ArrayList<>(); //Set sample challenge data
//        sampleChanlleges.add(new Challenge("30 ngày thanh lọc", "Thử thách 30 ngày thanh lọc cơ thể", ""));
//        sampleChanlleges.add(new Challenge("16 ngày giảm cân", "Thử thách 16 ngảy giảm cân", ""));
//        sampleChanlleges.add(new Challenge("1 ngày ăn kiêng", "Thử thách 1 ngày thanh lọc cơ thể", ""));
        renderChallenges(sampleChanlleges);
        return view;
    }

    private void renderChallenges(ArrayList<Challenge> challenges) {
        DietsHomeAdapter dietsHomeAdapter = new DietsHomeAdapter(challenges, getContext());
        rv_challenges.setHasFixedSize(true);
        rv_challenges.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_challenges.setAdapter(dietsHomeAdapter);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_avatar:
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
            case R.id.ic_reminder:
                startActivity(new Intent(getActivity(), ReminderActivity.class));
                break;
            case R.id.abs_exercise_layout:
                startActivity(new Intent(getActivity(), ExercisesActivity.class));
        }
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setBackgroundImageForMeals() {
        Glide.with(getActivity())
                .load("https://cdn.loveandlemons.com/wp-content/uploads/2019/09/breakfast-ideas.jpg")
                .centerCrop()
                .into(iv_bg_morning);
        Glide.with(getActivity())
                .load("https://cheddars.com/wp-content/uploads/images/menu-item-images/menu-599-potato-soup-house-salad.jpg")
                .centerCrop()
                .into(iv_bg_noon);
        Glide.with(getActivity())
                .load("https://www.budgetbytes.com/wp-content/uploads/2018/01/Sheet-Pan-BBQ-Meatloaf-Dinner-plate.jpg")
                .centerCrop()
                .into(iv_bg_night);
    }

    private Map<Integer, ImageView> mapValueToBodypartImageBackground() {
        Map<Integer, ImageView> map = new HashMap<>();
        map.put(121, iv_bg_back);
        map.put(11, iv_bg_arms);
        map.put(21, iv_bg_leg);
        map.put(31, iv_bg_abs);
        map.put(111, iv_bg_chest);
        return map;
    }

    private void getAllBodyParts() {
        Call<BodypartResponse> calledGetAll = bodypartsAPI.getAllBodyParts();
        calledGetAll.enqueue(new Callback<BodypartResponse>() {
            @Override
            public void onResponse(Call<BodypartResponse> call, Response<BodypartResponse> response) {
                BodypartResponse bodypartResponse = response.body();
                if (bodypartResponse.getStatus() == 0) {
                    mapValueToBodypartImageBackground().forEach((k, v) -> {
                        for(BodyParts bodyParts: bodypartResponse.getResponse()) {
                            if(bodyParts.getId() == k) {
                                Glide.with(getActivity())
                                        .load(bodyParts.getImage_url())
                                        .into(v);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<BodypartResponse> call, Throwable t) {

            }
        });
    }

    private void getUserById(int id) {
        Call<UserResponse> userResponseCall = polyFitService.getCurrentUser(id);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse.getStatus() == 0) {
                        User user = userResponse.getObject();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                        try {
                            Date date = format.parse(user.getCreatedAt());
                            String formattedDate = format.format(date);
                            setData(user.getUsername(), formattedDate, user.getHeight(), user.getWeight(), user.getBmi());
                        } catch (Exception e) {
                            Log.e("err:", e + "");
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Có lỗi xảy ra. Vui lòng đăng nhập lại", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    private void setData(String userName, String startDate, Float height, Float weight, Float bmi) {
        tv_UserName.setText(userName);
        tv_startDate.setText(startDate);
        tv_height.setText(String.valueOf(height));
        tv_weight.setText(String.valueOf(weight));
        tv_bmi.setText(String.valueOf(bmi));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Crop.REQUEST_PICK) {
                Uri source_uri = data.getData();
                Uri destination_uri = Uri.fromFile(new File(Objects.requireNonNull(getActivity()).getCacheDir(), "cropped"));
                Crop.of(source_uri, destination_uri).asSquare().start(getActivity(), HomeFragment.this);
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
