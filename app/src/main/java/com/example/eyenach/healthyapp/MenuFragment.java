package com.example.eyenach.healthyapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eyenach.healthyapp.post.PostFragment;
import com.example.eyenach.healthyapp.sleep.SleepFragment;
import com.example.eyenach.healthyapp.weight.WeightFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

    ArrayList<String> menu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        menu = new ArrayList<>();

        menu.add("BMI");
        menu.add("Weight");
        menu.add("Sleep");
        menu.add("Post");
        menu.add("Sign Out");


        SharedPreferences sp = getActivity().getSharedPreferences("USERNAME", getContext().MODE_PRIVATE);

        Boolean remember = sp.getBoolean("remember", false);

        Toast.makeText(getActivity(), "Remember = " +remember, Toast.LENGTH_LONG).show();

        ListView _menuList = getView().findViewById(R.id.menu_list);
        final ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                menu
        );
        _menuList.setAdapter(menuAdapter);
        _menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Log.d("MENU", "SELECT "+menu.get(i));

                if(menu.get(i).equals("BMI")){
                    Log.d("MENU", "GOTO BMI");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.main_view, new BmiFragment())
                            .commit();
                } else if(menu.get(i).equals("Weight")){
                    Log.d("MENU", "GOTO WEIGHT");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.main_view, new WeightFragment())
                            .commit();
                } else if(menu.get(i).equals("Sleep")){
                    Log.d("MENU", "GOTO SLEEP");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.main_view, new SleepFragment())
                            .commit();
                } else if(menu.get(i).equals("Post")){
                    Log.d("MENU", "GOTO POST");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.main_view, new PostFragment())
                            .commit();
                } else {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    mAuth.signOut();

                    Log.d("MENU", "GOTO LOGIN");
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view, new LoginFragment())
                            .addToBackStack(null)
                            .commit();
                }
            }
        });
    }

}
