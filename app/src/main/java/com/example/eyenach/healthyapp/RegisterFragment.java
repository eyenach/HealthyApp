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

public class RegisterFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initRegister();
    }

    void initRegister(){
        Button _regisBtn = getView().findViewById(R.id.regis_regis_btn);
        _regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _user = getView().findViewById(R.id.regis_user_id);
                EditText _name = getView().findViewById(R.id.regis_name);
                EditText _age = getView().findViewById(R.id.regis_age);
                EditText _pass = getView().findViewById(R.id.regis_pass);

                String _userStr = _user.getText().toString();
                String _nameStr = _name.getText().toString();
                String _ageStr = _age.getText().toString();
                String _passStr = _pass.getText().toString();

                if(_userStr.isEmpty() || _nameStr.isEmpty() || _ageStr.isEmpty() || _passStr.isEmpty()){
                    Log.d("REGISTER", "FIELD NAME IS EMPTY");
                    Toast.makeText(getActivity(), "กรุณาระบุข้อมูลให้ครบถ้วย", Toast.LENGTH_SHORT).show();
                } else if(_userStr.equals("admin")){
                    Log.d("REGISTER", "USER ALREADY EXIST");
                    Toast.makeText(getActivity(), "user นี้มีอยู่ในระบบแล้ว", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("REGISTER", "GOTO BMI");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.main_view, new BmiFragment())
                            .commit();
                }
            }
        });
    }
}
