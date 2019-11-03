package com.example.polyfit_app.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.polyfit_app.Activity.Login.LoginMethod;
import com.example.polyfit_app.Model.Responses.UserResponse;
import com.example.polyfit_app.Model.StepCount;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Service.local.PolyfitDatabase;
import com.example.polyfit_app.Service.remote.PolyFitService;
import com.example.polyfit_app.Service.remote.RetrofitClient;
import com.example.polyfit_app.Utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
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
    ProgressDialog progressDialog;
    private PolyFitService polyFitService;
    private String mParam1;
    private String mParam2;
    SharedPreferences sharedPreferences;
    private OnFragmentInteractionListener mListener;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        Retrofit retrofit = RetrofitClient.getInstance();
        polyFitService = retrofit.create(PolyFitService.class);
        connectView(view);
        sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN, Context.MODE_PRIVATE);
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
                Log.e("Logout", "Logout" + sharedPreferences.getInt("id", 0));
                logoutUser(sharedPreferences.getInt("id", 0));
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void connectView(View view) {
        ImageView icSetting = view.findViewById(R.id.icLogout);
        history_chart = view.findViewById(R.id.history_chart);
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
        Line line = new Line(values).setColor(Color.CYAN).setCubic(true);

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
                    SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN, Context.MODE_PRIVATE).edit();
                    sharedPreferences.putString("username", "");
                    sharedPreferences.putString("password", "");
                    sharedPreferences.apply();
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
}
