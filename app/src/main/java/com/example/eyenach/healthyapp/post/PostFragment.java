package com.example.eyenach.healthyapp.post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.eyenach.healthyapp.comment.CommentFragment;
import com.example.eyenach.healthyapp.MenuFragment;
import com.example.eyenach.healthyapp.R;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class PostFragment extends Fragment {

    Button _backBtn;

    String body;
    JSONArray jsonArray;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        run("https://jsonplaceholder.typicode.com/posts");

        initBackBtn();

    }

    void initBackBtn(){
        _backBtn = getView().findViewById(R.id.post_back_btn);
        _backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("POST", "GOTO MENU");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.main_view, new MenuFragment())
                        .commit();
            }
        });
    }

    void run(final String url) {
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                try {
                    Request request = new Request.Builder().url(url).build();
                    Response response = client.newCall(request).execute();
                    body = response.body().string();
                    jsonArray = new JSONArray(body);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    final ArrayList<JSONObject> posts = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        posts.add(obj);
                    }

                    ListView _postList = getView().findViewById(R.id.post_list);
                    PostAdapter _postAdapter = new PostAdapter(getContext(), R.layout.fragment_post_item, posts);

                    _postList.setAdapter(_postAdapter);

                    _postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Bundle bundle = new Bundle();
                            try {
                                bundle.putInt("id", posts.get(position).getInt("id"));
                            } catch (JSONException e) {
                                Log.d("POST", "catch JSONException : " + e.getMessage());
                            }

                            CommentFragment fragment = new CommentFragment();
                            fragment.setArguments(bundle);

                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .addToBackStack(null)
                                    .replace(R.id.main_view, fragment)
                                    .commit();

//                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                            ft.replace(R.id.main_view, fragment).addToBackStack(null).commit();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        task.execute();
    }
}
