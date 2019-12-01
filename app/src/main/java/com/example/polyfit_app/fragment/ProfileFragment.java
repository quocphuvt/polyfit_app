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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.polyfit_app.activity.Login.LoginMethod;
import com.example.polyfit_app.databinding.FragmentProfileBinding;
import com.example.polyfit_app.models.History;
import com.example.polyfit_app.models.Responses.HistoryResponse;
import com.example.polyfit_app.models.Responses.UserResponse;
import com.example.polyfit_app.models.StepCount;
import com.example.polyfit_app.models.User;
import com.example.polyfit_app.R;
import com.example.polyfit_app.service.local.PolyfitDatabase;
import com.example.polyfit_app.service.remote.PolyFitService;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.utils.Constants;
import com.example.polyfit_app.utils.Helpers;
import com.example.polyfit_app.utils.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private Button btnUpdateBMI;
    private FragmentProfileBinding fragmentProfileBinding;
    private User user;
    private Boolean isFocus = false;

    public ProfileFragment() {
    }

    public ProfileFragment(User user) {
        this.user = user;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View view = fragmentProfileBinding.getRoot();
        fragmentProfileBinding.setUser(this.user);

        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
        connectView(view);
        toggleFocusForEditText();
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
                logoutUser(this.user.getId());
                break;
            case R.id.btnUpdateBMI:
                if (btnUpdateBMI.getText().toString().equals("Tôi muốn cập nhật BMI")) {
                    btnUpdateBMI.setText("Cập nhật ngay");
                    toggleFocusForEditText();
                } else {
                    addHistory();
                }
                break;
        }
    }

    private void addHistory() {
        String weight = fragmentProfileBinding.edtWeight.getText().toString().trim();
        String height = fragmentProfileBinding.edtHeight.getText().toString().trim();
        if (TextUtils.isEmpty(weight)) {
            Toast.makeText(getContext(), "Vui lòng nhập cân nặng", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(height)) {
            Toast.makeText(getContext(), "Vui lòng nhập cân nặng", Toast.LENGTH_SHORT).show();
        } else {
            Float bmi = Util.calculateBMI(Float.parseFloat(weight), Float.parseFloat(height));
            History history = new History(bmi, user.getId());
            Call<HistoryResponse> calledRegister = polyFitService.addHistory(history);
            calledRegister.enqueue(new Callback<HistoryResponse>() {
                @Override
                public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                    HistoryResponse historyResponse = response.body();
                    if (historyResponse.getStatus() == 0) {
                        Log.e("PhayTran", "success");
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

    private void connectView(View view) {
        ImageView icSetting = view.findViewById(R.id.icLogout);
        history_chart = view.findViewById(R.id.history_chart);
        btnUpdateBMI = view.findViewById(R.id.btnUpdateBMI);
        btnUpdateBMI.setOnClickListener(this);
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
        for (int i = 0; i < listStep.size(); ++i) {
//            axisValues.add(new AxisValue(i).setLabel(hours[i]));
            axisValues.add(new AxisValue(i).setLabel(listStep.get(i).getHour() + ""));
            values.add(new PointValue(i, listStep.get(i).getStep() * 4));
            Log.e("listStep", listStep.get(i).getStep() + " : " + listStep.get(i).getHour());

        }


        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.parseColor("#be7575")).setCubic(true);

        line.setHasPoints(true);

        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);
//        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
//        axisX.setName("Axis X");
        axisY.setName("Calories");
        data.setAxisXBottom(new Axis(axisValues).setHasLines(true));
        data.setAxisYLeft(axisY);
        history_chart.setLineChartData(data);

        icSetting.setOnClickListener(this);
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

    private void toggleFocusForEditText() {
        fragmentProfileBinding.edtHeight.setFocusable(isFocus);
        fragmentProfileBinding.edtHeight.setFocusableInTouchMode(isFocus);
        fragmentProfileBinding.edtWeight.setFocusable(isFocus);
        fragmentProfileBinding.edtWeight.setFocusableInTouchMode(isFocus);
        isFocus = !isFocus;
    }

    private void updateUser(int user_id) {
        String weight = fragmentProfileBinding.edtWeight.getText().toString().trim();
        String height = fragmentProfileBinding.edtHeight.getText().toString().trim();
        User user = new User(user_id, Float.valueOf(weight), Float.valueOf(height));
        Call<UserResponse> callUpdate = polyFitService.updateUser(user);
        callUpdate.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                if (userResponse.getStatus() == 0) {
                    btnUpdateBMI.setText("Tôi muốn cập nhật BMI");
                    Helpers.putUserIntoPreferences( getContext(), userResponse.getObject());
                    toggleFocusForEditText();
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
}
