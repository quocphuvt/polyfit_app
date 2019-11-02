package com.example.polyfit_app.Fragments;

import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polyfit_app.Activity.DishesTodayActivity;
import com.example.polyfit_app.Activity.ExercisesActivity;
import com.example.polyfit_app.Activity.Login.LoginActivity;
import com.example.polyfit_app.Adapter.DietsHomeAdapter;
import com.example.polyfit_app.Adapter.Home.HomeBodypartsAdapter;
import com.example.polyfit_app.Model.BodyParts;
import com.example.polyfit_app.Activity.ReminderActivity;
import com.example.polyfit_app.Model.Diet;
import com.example.polyfit_app.Model.Responses.BodypartResponse;
import com.example.polyfit_app.Model.Responses.DietsResponse;
import com.example.polyfit_app.Model.Responses.UserResponse;
import com.example.polyfit_app.Model.User;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.remote.BodypartsAPI;
import com.example.polyfit_app.Service.remote.DietsAPI;
import com.example.polyfit_app.Service.remote.PolyFitService;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;
import com.github.chrisbanes.photoview.PhotoView;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.skyfishjy.library.RippleBackground;
import com.soundcloud.android.crop.Crop;

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
    private RecyclerView rv_diets;
    private PolyFitService polyFitService;
    private TextView tv_UserName, tv_startDate, tv_height, tv_weight, tv_bmi;
    private ImageView iv_avatar, ic_reminder, iv_bg_morning, iv_bg_noon, iv_bg_night;
    private PhotoView viewAvatar;
    private LinearLayout changeAvatar;
    private TextView tv_ChangeAvatar;
    private AlertDialog mDialog;
    private View mView;
    private AlertDialog.Builder mBuilder;
    private BodypartsAPI bodypartsAPI;
    private DietsAPI dietsAPI;
    private RecyclerView rv_bodyparts;
    private RippleBackground ripple_reminder;
    private RelativeLayout layout_reminder, layout_morning, layout_noon, layout_night;

    public HomeFragment() {
    }

    private void connectView(View view) {
        tv_UserName = view.findViewById(R.id.tv_UserName);
        tv_startDate = view.findViewById(R.id.tv_startDate);
        tv_height = view.findViewById(R.id.tv_height);
        tv_weight = view.findViewById(R.id.tv_weight);
        tv_bmi = view.findViewById(R.id.tv_BMI);
        iv_avatar = view.findViewById(R.id.iv_avatar);
        rv_diets = view.findViewById(R.id.rv_challenges);
        iv_avatar.setOnClickListener(this);
        ic_reminder = view.findViewById(R.id.ic_reminder);
        ic_reminder.setOnClickListener(this);
        rv_bodyparts = view.findViewById(R.id.rv_bodyparts_home);
        //Meal's image background
        iv_bg_morning = view.findViewById(R.id.image_bg_morning);
        iv_bg_noon = view.findViewById(R.id.image_bg_noon);
        iv_bg_night = view.findViewById(R.id.image_bg_night);
        ripple_reminder = view.findViewById(R.id.ripple_reminder);
        layout_reminder = view.findViewById(R.id.layout_reminder);
        layout_morning = view.findViewById(R.id.layout_morning);
        layout_noon = view.findViewById(R.id.layout_noon);
        layout_night = view.findViewById(R.id.layout_night);
        layout_reminder.setOnClickListener(this);
        layout_morning.setOnClickListener(this);
        layout_noon.setOnClickListener(this);
        layout_night.setOnClickListener(this);
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
        ripple_reminder.startRippleAnimation();
        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
        bodypartsAPI = retrofit.create(BodypartsAPI.class);
        dietsAPI = retrofit.create(DietsAPI.class);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);

        int userId = sharedPreferences.getInt("id", 0);

        this.getDietData();
        this.getUserById(userId);
        this.getAllBodyParts();
        this.setBackgroundImageForMeals();
        return view;
    }

    private void getDietData() {
        Call<DietsResponse> dietsResponseCall = dietsAPI.getAllDiets();
        dietsResponseCall.enqueue(new Callback<DietsResponse>() {
            @Override
            public void onResponse(Call<DietsResponse> call, Response<DietsResponse> response) {
                if(response.isSuccessful()) {
                    DietsResponse dietsResponse = response.body();
                    if(dietsResponse.getStatus() == 0) {
                        renderDiets(dietsResponse.getResponse());
                    }
                }
            }

            @Override
            public void onFailure(Call<DietsResponse> call, Throwable t) {

            }
        });
    }

    private void renderDiets(ArrayList<Diet> diets) {
        DietsHomeAdapter dietsHomeAdapter = new DietsHomeAdapter(diets, getContext());
        rv_diets.setHasFixedSize(true);
        rv_diets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rv_diets.setAdapter(dietsHomeAdapter);
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
        Intent i;
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
            case R.id.layout_reminder:
                startActivity(new Intent(getActivity(), ReminderActivity.class));
                break;
            case R.id.layout_morning:
                i = new Intent(getContext(), DishesTodayActivity.class);
                i.putExtra("title", "sáng");
                i.putExtra("mealId", 161);
                startActivity(i);
                break;
            case R.id.layout_noon:
                i = new Intent(getContext(), DishesTodayActivity.class);
                i.putExtra("title", "trưa");
                i.putExtra("mealId", 171);
                startActivity(i);
                break;
            case R.id.layout_night:
                i = new Intent(getContext(), DishesTodayActivity.class);
                i.putExtra("title", "chiều tối");
                i.putExtra("mealId", 181);
                startActivity(i);
                break;
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

    private void getAllBodyParts() {
        Call<BodypartResponse> calledGetAll = bodypartsAPI.getAllBodyParts();
        calledGetAll.enqueue(new Callback<BodypartResponse>() {
            @Override
            public void onResponse(Call<BodypartResponse> call, Response<BodypartResponse> response) {
                BodypartResponse bodypartResponse = response.body();
                if (bodypartResponse.getStatus() == 0) {
                    bodypartResponse.getResponse().add(0, new BodyParts(0));
                    HomeBodypartsAdapter homeBodypartsAdapter = new HomeBodypartsAdapter(bodypartResponse.getResponse(), getActivity());
                    rv_bodyparts.setHasFixedSize(true);
                    rv_bodyparts.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
                    rv_bodyparts.setAdapter(homeBodypartsAdapter);
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
