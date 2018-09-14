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

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initLoginBtn();
        initRegisBtn();
    }

    void initLoginBtn(){
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _user = getView().findViewById(R.id.login_user_id);
                EditText _pass = getView().findViewById(R.id.login_pass);

                String _userStr = _user.getText().toString();
                String _passStr = _pass.getText().toString();

                if(_userStr.isEmpty() || _passStr.isEmpty()){
                    Log.d("LOGIN", "USERNAME OR PASSWORD IS EMPTY");
                    Toast.makeText(getActivity(), "กรุณาระบุ username or password", Toast.LENGTH_SHORT).show();
                } else if(_userStr.equals("admin") && _passStr.equals("admin")){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new BmiFragment())
                            .commit();
                } else {
                    Log.d("LOGIN", "INVALID USER OR PASSWORD");
                    Toast.makeText(getActivity(), "username or password ไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void initRegisBtn(){
        TextView _regisBtn = getView().findViewById(R.id.login_regis);
        _regisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new RegisterFragment())
                        .commit();
            }
        });
    }
}
