package com.example.polyfit_app.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.polyfit_app.Activity.Login.LoginMethod;
import com.example.polyfit_app.R;
import com.example.polyfit_app.Utils.Constants;

import java.util.ArrayList;
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

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LineChartView history_chart;
    public final static String[] hours = new String[]{"6", "12", "18", "24"};

    private String mParam1;
    private String mParam2;
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icLogout:
                SharedPreferences.Editor sharedPreferences = getActivity().getSharedPreferences(Constants.LOGIN, Context.MODE_PRIVATE).edit();
                sharedPreferences.putString("username", "");
                sharedPreferences.putString("password", "");
                sharedPreferences.apply();
                startActivity(new Intent(getActivity(), LoginMethod.class));
                getActivity().finish();
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

        List<PointValue> values = new ArrayList<PointValue>();

        for (int i = 0; i < hours.length; ++i) {
            axisValues.add(new AxisValue(i).setLabel(hours[i]));
            values.add(new PointValue(i, i + 10));
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
}
