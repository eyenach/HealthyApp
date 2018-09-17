package com.example.eyenach.healthyapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eyenach.healthyapp.weight.Weight;
import com.example.eyenach.healthyapp.weight.WeightFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FormFragment extends Fragment {

    FirebaseFirestore _firestore;
    FirebaseAuth _mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _firestore = FirebaseFirestore.getInstance();
        _mAuth = FirebaseAuth.getInstance();

        initBackBtn();
        initSaveBtn();
    }

    void initBackBtn(){
        Button _backBtn = getView().findViewById(R.id.form_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new WeightFragment())
                        .commit();
            }
        });
    }

    void initSaveBtn(){
        Button _saveBtn = getView().findViewById(R.id.form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _date = getView().findViewById(R.id.form_date);
                EditText _weight = getView().findViewById(R.id.form_weight);

                String _dateStr = _date.getText().toString();
                String _weightStr = _weight.getText().toString();
                String _uid = _mAuth.getCurrentUser().getUid(); //get uid form user

                if(_dateStr.isEmpty() || _weightStr.isEmpty()){
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                } else {
                    //collection less than 5
                    Weight _data = new Weight(_dateStr, Integer.valueOf(_weightStr), "UP");
                    _firestore.collection("myfitness")
                            .document(_uid)
                            .collection("weight")
                            .document(_dateStr)
                            .set(_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d("FORM", "SAVE COMPLETE");
                            Toast.makeText(getActivity(), "บันทึกเรียบร้อย", Toast.LENGTH_SHORT).show();

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view, new WeightFragment())
                                    .commit();
                            Log.d("FORM", "GOTO WEIGHT");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("FORM", "SAVE FAIL");
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
