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
        lv_ingredients = bottomSheetIngredientView.findViewById(R.id.lv_ingredients_dish_details);
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
        for (int i = 0; i < 10; i++) {
            Dishes dishes = new Dishes("Bò hầm cải chua" + i, "", 20, 20, 20, 100, 1);
            dishesArrayList.add(dishes);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        setDataForDishesList(dishesArrayList);
        setSampleIngredientData();
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
        showDishBottomSheet();
    }

    @Override
    public void onClick(View view, int posittion) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void showDishBottomSheet() {
        sheet_dish.showWithSheetView(bottomSheetIngredientView);
        setSampleIngredientData();
    }

    private void setSampleIngredientData() {
        ingredients = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ingredients.add(new Ingredient("Thit bo" + i, 1000, "100g"));
        }

        IngredientAdapter ingredientAdapter = new IngredientAdapter(ingredients, getContext());
        lv_ingredients.setAdapter(ingredientAdapter);
    }

    private void setDataForDishesList(ArrayList<Dishes> dishesList) {
        DishesAdapter dishesAdapter = new DishesAdapter(dishesArrayList, getActivity(), this);
        recyclerView.setAdapter(dishesAdapter);
    }

    class IngredientAdapter extends BaseAdapter {
        private ArrayList<Ingredient> ingredients;
        private Context context;

        public IngredientAdapter(ArrayList<Ingredient> ingredients, Context context) {
            this.ingredients = ingredients;
            this.context = context;
        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.one_item_ingredient, viewGroup, false);
            Ingredient ingredient = ingredients.get(i);
            TextView tv_title = view.findViewById(R.id.tv_item_title_ingredient);
            TextView tv_unit = view.findViewById(R.id.tv_item_quantity_ingredient);
            tv_title.setText(ingredient.getTitle());
            tv_unit.setText(ingredient.getUnit());
            return view;
        }
    }
}
