package com.example.polyfit_app.Fragments;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.polyfit_app.Adapter.DishesAdapter;
import com.example.polyfit_app.Interface.ItemClickListener;
import com.example.polyfit_app.Model.Dishes;
import com.example.polyfit_app.Model.Ingredient;
import com.example.polyfit_app.R;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.ArrayList;

public class DishesFragment extends Fragment implements ItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //
    private ListView lv_ingredients;
    private ArrayList<Ingredient> ingredients;
    private View bottomSheetIngredientView;
    private BottomSheetLayout sheet_dish;

    private OnFragmentInteractionListener mListener;
    ArrayList<Dishes> dishesArrayList;
    RecyclerView recyclerView;

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.rv_dishes);
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
        View view = inflater.inflate(R.layout.activity_dishes_fragment, container, false);
        initView(view);
        dishesArrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
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
