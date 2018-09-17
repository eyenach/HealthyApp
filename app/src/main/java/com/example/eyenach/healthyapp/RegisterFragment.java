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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

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
                EditText _email = getView().findViewById(R.id.regis_email);
                EditText _pass = getView().findViewById(R.id.regis_pass);
                EditText _repass = getView().findViewById(R.id.regis_repass);

                String _emailStr = _email.getText().toString();
                String _passStr = _pass.getText().toString();
                String _repassStr = _repass.getText().toString();

                if(_emailStr.isEmpty() || _passStr.isEmpty() || _repassStr.isEmpty()){
                    Log.d("REGISTER", "FIELD IS EMPTY");
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วย", Toast.LENGTH_SHORT).show();
                } else if(_passStr.length()<6){
                    Log.d("REGISTER", "PASSWORD TOO SHORT");
                    Toast.makeText(getActivity(), "password ต้องมีความยาวมากกว่า 6 ตัวอักษร", Toast.LENGTH_SHORT).show();
                } else if(!_passStr.equals(_repassStr)){
                    Log.d("REGISTER", "PASSWORD NOT MATCH");
                    Toast.makeText(getActivity(), "password ไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(_emailStr, _passStr).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendVerifiedEmail(mAuth.getCurrentUser());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    void sendVerifiedEmail(FirebaseUser _user){
        _user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("REGISTER", "SEND ALREADY");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new LoginFragment())
                        .commit();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
