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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class WeightFragment extends Fragment {

    FirebaseFirestore mdb;
    FirebaseAuth mAuth;
    ArrayList<Weight> weights = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_weight, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mdb = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String _uid = mAuth.getCurrentUser().getUid();

        final ListView _weightList = getView().findViewById(R.id.weight_list);
        final WeightAdapter _weightAdapter = new WeightAdapter(getActivity(), R.layout.fragment_weight_item, weights);

        _weightAdapter.clear();

        mdb.collection("myfitness").document(_uid).collection("weight").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                _weightAdapter.clear();
                for(QueryDocumentSnapshot doc : queryDocumentSnapshots){
                    if(doc.get("date") != null && doc.get("weight") != null && doc.get("status") != null){
                        weights.add(new Weight(doc.get("date").toString(), Integer.parseInt(doc.get("weight").toString()), doc.get("status").toString()));
                    }
                }
                _weightList.setAdapter(_weightAdapter);
            }
        });

        initAddBtn();
    }

    void initAddBtn(){
        Button _addBtn = getView().findViewById(R.id.weight_add_btn);
        _addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("WEIGHT", "GOTO FORM");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new FormFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
