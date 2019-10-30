package com.example.polyfit_app.Fragments.Meals;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.polyfit_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoonFragment extends Fragment {


    public NoonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_noon, container, false);
    }

}
