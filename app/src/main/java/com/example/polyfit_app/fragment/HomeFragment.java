package com.example.polyfit_app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.polyfit_app.activity.DishesTodayActivity;
import com.example.polyfit_app.activity.login.LoginActivity;
import com.example.polyfit_app.adapter.DietsHomeAdapter;
import com.example.polyfit_app.adapter.home.HomeBodypartsAdapter;
import com.example.polyfit_app.databinding.FragmentHomeBinding;
import com.example.polyfit_app.model.BodyParts;
import com.example.polyfit_app.activity.ReminderActivity;
import com.example.polyfit_app.model.Diet;
import com.example.polyfit_app.model.response.BodypartResponse;
import com.example.polyfit_app.model.response.DietsResponse;
import com.example.polyfit_app.model.response.UserResponse;
import com.example.polyfit_app.model.User;
import com.example.polyfit_app.R;
import com.example.polyfit_app.service.remote.BodypartsAPI;
import com.example.polyfit_app.service.remote.DietsAPI;
import com.example.polyfit_app.service.remote.PolyFitService;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.user.UserViewModel;
import com.example.polyfit_app.utils.Constants;
import com.example.polyfit_app.utils.Helpers;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skyfishjy.library.RippleBackground;
import com.soundcloud.android.crop.Crop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.UnknownServiceException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private ProgressDialog processDialog;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl(Constants.STORAGE_IMAGE);
    private RelativeLayout layout_reminder, layout_morning, layout_noon, layout_night;
    private String avatarUrl;
    private User user;
    private Retrofit retrofit = RetrofitClient.getInstance();
    private FragmentHomeBinding homeBinding;
    private UserViewModel userViewModel;

    public HomeFragment() {
    }

    private void configSetOnClick() {
        homeBinding.layoutReminder.setOnClickListener(this);
        homeBinding.layoutMorning.setOnClickListener(this);
        homeBinding.layoutNoon.setOnClickListener(this);
        homeBinding.layoutNight.setOnClickListener(this);
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
    
    private void setUpRetrofit() {
        polyFitService = retrofit.create(PolyFitService.class);
        bodypartsAPI = retrofit.create(BodypartsAPI.class);
        dietsAPI = retrofit.create(DietsAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = homeBinding.getRoot();
        homeBinding.setUserViewModel(userViewModel);
        user = userViewModel.getUser().getValue();
        
        configSetOnClick();
        setUpRetrofit();
        homeBinding.rippleReminder.startRippleAnimation();
        this.getDietData();
        this.getAllBodyParts();
        this.setBackgroundImageForMeals();
        return view;
    }

    private void getDietData() {
        Call<DietsResponse> dietsResponseCall = dietsAPI.getAllDiets();
        dietsResponseCall.enqueue(new Callback<DietsResponse>() {
            @Override
            public void onResponse(Call<DietsResponse> call, Response<DietsResponse> response) {
                if (response.isSuccessful()) {
                    DietsResponse dietsResponse = response.body();
                    if (dietsResponse.getStatus() == 0) {
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
        homeBinding.rvDiets.setHasFixedSize(true);
        homeBinding.rvDiets.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        homeBinding.rvDiets.setAdapter(dietsHomeAdapter);
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
                mBuilder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                mView = getLayoutInflater().inflate(R.layout.dialog_view_avatar, null, false);
                viewAvatar = mView.findViewById(R.id.avatarView);
                setAvatarToPhotoView(viewAvatar);
                changeAvatar = mView.findViewById(R.id.changeAvatar);
                tv_ChangeAvatar = mView.findViewById(R.id.tv_ChangeAvatar);
                mBuilder.setView(mView);
                mDialog = mBuilder.create();
                mDialog.show();
                changeAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tv_ChangeAvatar.getText().toString().equals("Change Avatar")) {
                            Crop.pickImage(getActivity(), HomeFragment.this);
                        }
                        if (tv_ChangeAvatar.getText().toString().equals("Apply")) {
                            updateAvatar();
                        }
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

    private void updateAvatar() {
        processDialog = new ProgressDialog(getActivity());
        processDialog.setCancelable(false);
        processDialog.setMessage("Processing...");
        processDialog.show();
        final StorageReference mountainsRef = storageRef.child(new Date() + ".png");
        viewAvatar.setDrawingCacheEnabled(true);
        viewAvatar.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) viewAvatar.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();
        final UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                processDialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return mountainsRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            assert downloadUri != null;
                            avatarUrl = downloadUri.toString();

//                            viewAvatar.setImageResource(R.drawable.ic_launcher_foreground);
                            Log.d("Link", avatarUrl);
                            handleUpdateUser();
                        } else {
                            processDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    private void handleUpdateUser() {
        User updatedUser = new User(user.getId(), avatarUrl);
        Call<UserResponse> callUpdate = polyFitService.updateUser(updatedUser);
        callUpdate.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse.getStatus() == 0) {
                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT).show();
                        Helpers.putUserIntoPreferences(getContext(), userResponse.getObject());
                        processDialog.dismiss();
                    } else {
                        Log.e("PhayTran", "Create failed");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                processDialog.dismiss();

                Log.e("PhayTran", "failed" + call.request() + ":::" + t.getMessage());
            }
        });
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setBackgroundImageForMeals() {
        Glide.with(getActivity())
                .load("https://cdn.loveandlemons.com/wp-content/uploads/2019/09/breakfast-ideas.jpg")
                .centerCrop()
                .into(homeBinding.imageBgMorning);
        Glide.with(getActivity())
                .load("https://cheddars.com/wp-content/uploads/images/menu-item-images/menu-599-potato-soup-house-salad.jpg")
                .centerCrop()
                .into(homeBinding.imageBgNoon);
        Glide.with(getActivity())
                .load("https://www.budgetbytes.com/wp-content/uploads/2018/01/Sheet-Pan-BBQ-Meatloaf-Dinner-plate.jpg")
                .centerCrop()
                .into(homeBinding.imageBgNight);
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
                    homeBinding.rvBodypartsHome.setHasFixedSize(true);
                    homeBinding.rvBodypartsHome.setLayoutManager(new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false));
                    homeBinding.rvBodypartsHome.setAdapter(homeBodypartsAdapter);
                }
            }

            @Override
            public void onFailure(Call<BodypartResponse> call, Throwable t) {

            }
        });
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
                        updateAvatar();
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

    private void setAvatarToPhotoView(PhotoView avatarToPhotoView) {
        Glide.with(getActivity().getApplicationContext())
                .load(user.getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_avatar)
                .into(avatarToPhotoView);
    }

}
