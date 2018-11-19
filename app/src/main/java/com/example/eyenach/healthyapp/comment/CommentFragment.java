package com.example.eyenach.healthyapp.comment;

import android.os.AsyncTask;
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

import com.example.eyenach.healthyapp.R;
import com.example.eyenach.healthyapp.post.PostFragment;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CommentFragment extends Fragment {

    private Bundle _bundle;
    private Button _backBtn;

    private int position;
    private String body;

    JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        _bundle = getArguments();

        if(_bundle != null){
            position = _bundle.getInt("id");
            Log.d("COMMENT", "Position = " + position);
            run();
        } else {
            Log.d("COMMENT", "id is NULL");
        }

        initBackBtn();
    }

    void initBackBtn(){
        _backBtn = getView().findViewById(R.id.comment_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("POST", "GOTO MENU");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new PostFragment())
                        .commit();
            }
        });
    }

    private void run(){
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                try {
                    String url = "https://jsonplaceholder.typicode.com/posts/" + position + "/comments";

                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();

                    body = response.body().string();
                    jsonArray = new JSONArray(body);
                } catch (IOException e) {
                    Log.d("COMMENT", "catch IOException : " + e.getMessage());
                } catch (JSONException e) {
                    Log.d("COMMENT", "catch JSONException : " + e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    final ArrayList<JSONObject> commentList = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        commentList.add(obj);
                    }

                    ListView _commentList = getView().findViewById(R.id.comment_list);
                    CommentAdapter _commentAdapter = new CommentAdapter(getContext(), R.layout.fragment_comment_item, commentList);
                    _commentList.setAdapter(_commentAdapter);
                }
                catch (JSONException e)
                {
                    Log.d("COMMENT", "catch JSONException : " + e.getMessage());
                }
            }
        };
        task.execute();
    }
}
