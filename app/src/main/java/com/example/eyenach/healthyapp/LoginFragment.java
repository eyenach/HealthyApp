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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(mUser != null){
            gotoMenu();
        }

        initLoginBtn();
        initRegisBtn();
    }

    void initLoginBtn(){
        Button _loginBtn = getView().findViewById(R.id.login_login_btn);
        _loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText _email = getView().findViewById(R.id.login_email);
                EditText _pass = getView().findViewById(R.id.login_pass);

                String _emailStr = _email.getText().toString();
                String _passStr = _pass.getText().toString();

                if(_emailStr.isEmpty() || _passStr.isEmpty()){
                    Log.d("LOGIN", "USERNAME OR PASSWORD IS EMPTY");
                    Toast.makeText(getActivity(), "กรุณาระบุ username or password", Toast.LENGTH_SHORT).show();
                } else {
                    checkUser(_emailStr, _passStr); //เช็คว่ายืนยันอีเมลแล้วรึยัง
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
                        .replace(R.id.main_view, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    void checkUser(String email, String pass){
        mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(authResult.getUser().isEmailVerified()){
                    gotoMenu();
                } else {
                    Toast.makeText(getActivity(), "กรุณายืนยันอีเมลก่อน", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "กรุณาลงทะเบียนก่อนเข้าใช้งาน", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void gotoMenu(){
        Log.d("LOGIN", "LOGIN WITH EMAIL");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_view, new MenuFragment()).addToBackStack(null).commit();
    }
}
