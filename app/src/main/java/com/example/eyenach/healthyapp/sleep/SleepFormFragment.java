package com.example.eyenach.healthyapp.sleep;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.eyenach.healthyapp.R;

public class SleepFormFragment extends Fragment {

    SQLiteDatabase myDB;
    ContentValues _row;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form_sleep, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initBackBtn();
        initSaveBtn();
    }

    void initBackBtn(){
        Button _backBtn = getView().findViewById(R.id.sleep_form_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    void initSaveBtn(){
        Button _saveBtn = getView().findViewById(R.id.sleep_form_save_btn);
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myDB = getActivity().openOrCreateDatabase("my.db", Context.MODE_PRIVATE, null);

                myDB.execSQL(
                        "CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, sleep VARCHAR(5), wake VARCHAR(5), date VARCHAR(11))"
                );

                Log.d("SLEEP_FORM", "CREATE TABLE ALREADY");

                EditText _date = getView().findViewById(R.id.sleep_form_date);
                EditText _sleepHour = getView().findViewById(R.id.sleep_form_sleep_hour);
                EditText _sleepMin = getView().findViewById(R.id.sleep_form_sleep_min);
                EditText _wakeHour = getView().findViewById(R.id.sleep_form_wake_hour);
                EditText _wakeMin = getView().findViewById(R.id.sleep_form_wake_min);

                String _dateStr = _date.getText().toString();
                String _sleepStr = _sleepHour.getText().toString()+":"+_sleepMin.getText().toString();
                String _wakeStr = _wakeHour.getText().toString()+":"+_wakeMin.getText().toString();

                Sleep _itemSleep = new Sleep();
                _itemSleep.setContent(_sleepStr, _wakeStr, _dateStr);

                _row = _itemSleep.getContent();

                myDB.insert("user", null, _row);

                Log.d("SLEEP_FORM", "INSERT ALREADY");

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view, new SleepFragment())
                        .addToBackStack(null)
                        .commit();

                Toast.makeText(getActivity(), "INSERT ALREADY", Toast.LENGTH_SHORT).show();
                Log.d("SLEEP_FORM", "GOTO SLEEP");

            }
        });
    }
}
