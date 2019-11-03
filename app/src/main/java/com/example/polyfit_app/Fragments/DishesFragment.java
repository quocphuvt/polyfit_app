package com.example.polyfit_app.Fragments;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.polyfit_app.Interface.ItemClickListener;
import com.example.polyfit_app.R;
import com.flipboard.bottomsheet.BottomSheetLayout;

public class DishesFragment extends Fragment implements ItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private View bottomSheetIngredientView;
    private BottomSheetLayout sheet_dish;


    private OnFragmentInteractionListener mListener;

    private void initView(View view) {
        sheet_dish = view.findViewById(R.id.bottom_sheet_dish_details);
        bottomSheetIngredientView = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_dish_details, sheet_dish, false);
    }

    public DishesFragment() {
    }


    public static DishesFragment newInstance(String param1, String param2) {
        DishesFragment fragment = new DishesFragment();
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
        View view = inflater.inflate(R.layout.fragment_dishes, container, false);
        initView(view);
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
    public void onClickItem(int id) {

    }

    @Override
    public void onClick(View view, int posittion) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
