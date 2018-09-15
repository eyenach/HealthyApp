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
import android.widget.TextView;
import android.widget.Toast;

public class BmiFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bmi, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCalcBtn();
    }

    void initCalcBtn(){
        Button _calcBtn = getView().findViewById(R.id.bmi_calc);
        _calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _height = getView().findViewById(R.id.bmi_height);
                EditText _weight = getView().findViewById(R.id.bmi_weight);
                TextView _result = getView().findViewById(R.id.bmi_bmi);

                String _heightStr = _height.getText().toString();
                String  _weightStr = _weight.getText().toString();
                String _resultStr = _result.getText().toString();

                if(_heightStr.isEmpty() || _weightStr.isEmpty()){
                    Log.d("BMI", "FIELD NAME IS EMPTY");
                    Toast.makeText(getActivity(), "กรุณาระบุข้อมูลให้ครบถ้วย", Toast.LENGTH_SHORT).show();
                } else {
                    float _heightFlo = Float.parseFloat(_heightStr)/100;
                    float _weightFlo = Float.parseFloat(_weightStr);

                    float _resultFlo = _weightFlo/(_heightFlo*_heightFlo);
                    _result.setText(String.valueOf(_resultFlo));

                    Log.d("BMI", "BMI IS VALUE");
                }
            }
        });
    }
}
