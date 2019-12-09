package com.example.polyfit_app.fragment;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.polyfit_app.adapter.DietFragmentPagerAdapter;
import com.example.polyfit_app.databinding.FragmentDietsBinding;
import com.example.polyfit_app.interfaces.ItemClickListener;
import com.example.polyfit_app.models.Diet;
import com.example.polyfit_app.models.Responses.DietsResponse;
import com.example.polyfit_app.R;
import com.example.polyfit_app.models.User;
import com.example.polyfit_app.service.remote.DietsAPI;
import com.example.polyfit_app.service.remote.RetrofitClient;
import com.example.polyfit_app.utils.Helpers;
import com.example.polyfit_app.utils.Util;
import com.example.polyfit_app.view_model.DietViewModel;
import com.example.polyfit_app.view_model.UserViewModel;
import com.example.polyfit_app.view_model.repository.DietRepository;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.rd.PageIndicatorView;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DietsFragment extends Fragment implements ItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private DiscreteScrollView rv_diets;
    private PageIndicatorView pageIndicatorView;
    private OnFragmentInteractionListener mListener;
    private DietsResponse dietsResponse = null;
    private DietFragmentPagerAdapter dietFragmentPagerAdapter;
    private DietViewModel dietViewModel;
    private User user;

    private void initView(View view) {
        rv_diets = view.findViewById(R.id.rv_diets_fragment);
        pageIndicatorView = view.findViewById(R.id.pageIndicatorView);
        // specify total count of indicators
    }

    public DietsFragment() {
    }

    public static DietsFragment newInstance(String param1, String param2) {
        DietsFragment fragment = new DietsFragment();
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
        FragmentDietsBinding fragmentDietsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_diets, container, false);
        View view = fragmentDietsBinding.getRoot();
        initView(view);

//        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        Observer<User> userObserver = new Observer<User>() {
//            @Override
//            public void onChanged(User userModel) {
//                Log.e("phu:", "HELLO");
//                user = userModel;
//            }
//        };
//        userViewModel.getUser().observe(, userObserver);

        rv_diets.addScrollListener(new DiscreteScrollView.ScrollListener<RecyclerView.ViewHolder>() {
            @Override
            public void onScroll(float v, int i, int i1, @Nullable RecyclerView.ViewHolder viewHolder, @Nullable RecyclerView.ViewHolder t1) {
                pageIndicatorView.setSelection(i1);
            }
        });
        return view;
    }

//    private void getDietsByLevelId(Integer levelId) {
//        Log.e("diet:", "Nhay vao ham");
//        Call<DietsResponse> dietsResponseCall = dietsAPI.getAllDietsByLevelId(levelId);
//        dietsResponseCall.enqueue(new Callback<DietsResponse>() {
//            @Override
//            public void onResponse(Call<DietsResponse> call, Response<DietsResponse> response) {
//                if(response.isSuccessful()) {
//                    if(dietsResponse == null) {
//                        onGetDietDataSuccess(response.body().getResponse());
//                        dietsResponse = response.body();
//                        Log.e("diet:", "Khoi tao");
//                    } else {
//                        dietFragmentPagerAdapter.updateDietData(response.body().getResponse());
//                        Log.e("diet:", "update diet");
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DietsResponse> call, Throwable t) {
//
//            }
//        });
//    }

    private void onGetDietDataSuccess(ArrayList<Diet> diets) {
        pageIndicatorView.setCount(diets.size());
        dietFragmentPagerAdapter = new DietFragmentPagerAdapter(diets, getContext());
        rv_diets.setOffscreenItems(20);
        rv_diets.setSlideOnFlingThreshold(1000);
        rv_diets.setOverScrollEnabled(true);
        rv_diets.setItemTransformer(new ScaleTransformer.Builder()
                .setMaxScale(1.05f)
                .setMinScale(0.8f)
                .build());
        rv_diets.setAdapter(dietFragmentPagerAdapter);
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
