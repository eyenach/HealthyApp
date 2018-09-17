package com.example.eyenach.healthyapp.weight;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.eyenach.healthyapp.FormFragment;
import com.example.eyenach.healthyapp.R;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    ArrayList<Weight> weights = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        weights.add(new Weight("1 Jan 2018", 63, "UP"));
        weights.add(new Weight("2 Jan 2018", 64, "DOWN"));
        weights.add(new Weight("3 Jan 2018", 63, "UP"));

        Log.d("WEIGHT", "ADD Weight");

        ListView _weightList = getView().findViewById(R.id.weight_list);
        Log.d("WEIGHT", "_weightList");
        WeightAdapter _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_item, weights);
        Log.d("WEIGHT", "_weightAdapter");
        _weightList.setAdapter(_weightAdapter);
        Log.d("WEIGHT", "_setAdapter");
        initAddBtn();
    }

    void initAddBtn(){
        Button _addBtn = getView().findViewById(R.id.weight_add_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new FormFragment())
                        .commit();
            }
        });
    }
}
