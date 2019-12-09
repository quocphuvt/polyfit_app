package com.example.polyfit_app.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.polyfit_app.activity.login.LoginMethod;
import com.example.polyfit_app.adapter.RoutineAdapter;
import com.example.polyfit_app.model.History;
import com.example.polyfit_app.model.response.HistoryResponse;
import com.example.polyfit_app.model.response.RoutineResponse;
import com.example.polyfit_app.model.response.UserResponse;
import com.example.polyfit_app.model.Routine;
import com.example.polyfit_app.model.StepCount;
import com.example.polyfit_app.model.User;
import com.example.polyfit_app.R;
import com.example.polyfit_app.service.local.PolyfitDatabase;
import com.example.polyfit_app.service.remote.PolyFitService;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.service.remote.RoutineAPI;
import com.example.polyfit_app.utils.Constants;
import com.example.polyfit_app.utils.Helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LineChartView history_chart;
    public final static String[] hours = new String[]{"6", "12", "18", "24"};
    private ProgressDialog progressDialog;
    private PolyFitService polyFitService;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private AppCompatEditText edtHeight, edtWeight;
    private Button btnUpdateBMI;
    private RecyclerView viewHistory;
    private RoutineAPI routineAPI;
    private List<Routine> routineList;
    private List<RoutineResponse> routineResponses = new ArrayList<>();
    private Retrofit retrofit = RetrofitClient.getInstance();
    private User user;

    public ProfileFragment() {
    }


    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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

    private void setRetrofitServices() {
        polyFitService = retrofit.create(PolyFitService.class);
        routineAPI = retrofit.create(RoutineAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        setRetrofitServices();
        initView(view);
        user = Helpers.getUserFromPreferences(getContext());
        disableFocus();
        setUserInfo();
        setStepCount();
        getAllHistory(user.getId());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icLogout:
                logoutUser(user.getId());
                break;
            case R.id.btnUpdateBMI:
                if (btnUpdateBMI.getText().toString().equals("Tôi muốn cập nhật BMI")) {
                    btnUpdateBMI.setText("Cập nhật ngay");
                    enableFocus();
                } else {
                    addHistory();
                }
                break;
        }
    }

    private void addHistory() {
        String weight = edtWeight.getText().toString().trim();
        String height = edtHeight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {
            Toast.makeText(getContext(), "Vui lòng nhập cân nặng", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(height)) {
            Toast.makeText(getContext(), "Vui lòng nhập cân nặng", Toast.LENGTH_SHORT).show();
        } else {
            float bmi = (Float.valueOf(weight) / (Float.valueOf(height) * 2)) * 100;
            History history = new History(bmi, user.getId());
            Call<HistoryResponse> calledRegister = polyFitService.addHistory(history);
            calledRegister.enqueue(new Callback<HistoryResponse>() {
                @Override
                public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                    HistoryResponse historyResponse = response.body();
                    if (historyResponse.getStatus() == 0) {
                        updateUser(user.getId());
                    }
                }

                @Override
                public void onFailure(Call<HistoryResponse> call, Throwable t) {
                    progressDialog.dismiss();

                    Log.e("PhayTran", "failed" + call.request() + ":::" + t.getMessage());
                }
            });
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void initView(View view) {
        ImageView icSetting = view.findViewById(R.id.icLogout);
        history_chart = view.findViewById(R.id.history_chart);
        edtHeight = view.findViewById(R.id.edtHeight);
        edtWeight = view.findViewById(R.id.edtWeight);
        viewHistory = view.findViewById(R.id.viewHistory);
        btnUpdateBMI = view.findViewById(R.id.btnUpdateBMI);
        btnUpdateBMI.setOnClickListener(this);
        icSetting.setOnClickListener(this);
    }
    
    private void setStepCount() {
        List<AxisValue> axisValues = new ArrayList<AxisValue>();
        List<StepCount> listStep = new ArrayList<>();
        listStep = PolyfitDatabase.getInstance(getActivity()).stepDAO().getStepCount();
        List<PointValue> values = new ArrayList<PointValue>();
        Collections.reverse(listStep);
        StepCount stepCount = new StepCount();
        stepCount.setHour(0);
        stepCount.setStep(0);
        listStep.add(stepCount);
        Collections.reverse(listStep);
        if (listStep.isEmpty()) {
            Log.e("listStep", "Empty");
        }
        for (int i = 0; i < listStep.size(); ++i) {
//            axisValues.add(new AxisValue(i).setLabel(hours[i]));
            axisValues.add(new AxisValue(i).setLabel(listStep.get(i).getHour() + ""));
            values.add(new PointValue(i, listStep.get(i).getStep() * 4));
            Log.e("listStep", listStep.get(i).getStep() + " : " + listStep.get(i).getHour());
        }
        Line line = new Line(values).setColor(Color.parseColor("#be7575")).setCubic(true);
        line.setHasPoints(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);
        Axis axisY = new Axis().setHasLines(true);
        axisY.setName("Calories");
        data.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        data.setAxisYLeft(axisY);
        history_chart.setLineChartData(data);
    }

    //User logout
    private void logoutUser(Integer userId) {
        showProgressDialog();
        Call<UserResponse> callLogout = polyFitService.userLogout(userId);
        callLogout.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Helpers.removeUserFromPreferences(getContext());
                    startActivity(new Intent(getActivity(), LoginMethod.class));
                    getActivity().finish();
                }
                if (!response.isSuccessful()) {
                    Log.e("PhayTran", response.code() + "");
                }

            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e("Logout", "Failed" + call.toString());
            }
        });
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing...");
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

    private void enableFocus() {
        edtHeight.setFocusable(true);
        edtHeight.setFocusableInTouchMode(true);
        edtWeight.setFocusable(true);
        edtWeight.setFocusableInTouchMode(true);
    }

    private void disableFocus() {
        edtHeight.setFocusable(false);
        edtHeight.requestFocus(1);
        edtHeight.setFocusableInTouchMode(false);
        edtWeight.setFocusable(false);
        edtWeight.setFocusableInTouchMode(false);
    }

    private void setUserInfo() {
        edtHeight.setText(String.valueOf(user.getHeight()));
        edtWeight.setText(String.valueOf(user.getWeight()));
    }

    private void updateUser(int user_id) {
        String weight = edtWeight.getText().toString().trim();
        String height = edtHeight.getText().toString().trim();
        User newUser = new User(user_id, Float.valueOf(weight), Float.valueOf(height));
        Call<UserResponse> callUpdate = polyFitService.updateUser(newUser);
        callUpdate.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse.getStatus() == 0) {
                    Helpers.putUserIntoPreferences(getContext(), userResponse.getObject());
                    btnUpdateBMI.setText("Tôi muốn cập nhật BMI");
                    disableFocus();
                } else {
                    Log.e("PhayTran", "Create failed");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();

                Log.e("PhayTran", "failed" + call.request() + ":::" + t.getMessage());
            }
        });
    }

    private void getAllHistory(Integer id) {
        Call<RoutineResponse> getRoutine = routineAPI.getRoutinesByUserId(id);
        getRoutine.enqueue(new Callback<RoutineResponse>() {
            @Override
            public void onResponse(Call<RoutineResponse> call, Response<RoutineResponse> response) {
                RoutineResponse routineResponse = response.body();
                if (routineResponse.getStatus() == 0) {
//                    routineResponse.getResponse().add(0, new Routine());
                    viewHistory.setHasFixedSize(true);
                    viewHistory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
//                    removeDuplicate(routineResponse.getResponse());
                    RoutineAdapter routineAdapter = new RoutineAdapter(removeDuplicate(routineResponse.getResponse()), getActivity());
                    viewHistory.setAdapter(routineAdapter);
                }
            }

            @Override
            public void onFailure(Call<RoutineResponse> call, Throwable t) {

            }
        });
    }

    private List<Routine> removeDuplicate(List<Routine> list) {
        Set<String> namesAlreadySeen = new HashSet<>();
        list.removeIf(routine -> !namesAlreadySeen.add(routine.getCreate_date()));
        Collections.reverse(list);
        return list;
    }
}
