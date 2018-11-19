package com.example.eyenach.healthyapp.post;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.eyenach.healthyapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostAdapter extends ArrayAdapter {

    ArrayList<JSONObject> post;
    Context context;

    TextView _title, _body;

    public PostAdapter(@NonNull Context context, int resource, @NonNull ArrayList<JSONObject> objects) {
        super(context, resource, objects);

        this.post = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        JSONObject _postObj = post.get(position);

        View _postItem = LayoutInflater.from(context).inflate(R.layout.fragment_post_item, parent, false);
        _title = _postItem.findViewById(R.id.post_item_title);
        _body = _postItem.findViewById(R.id.post_item_body);

        try {
            _title.setText(_postObj.getString("id")+" : "+_postObj.getString("title"));
            _body.setText(_postObj.getString("body"));
        } catch (JSONException e) {
            Log.d("POST", "ERROR!");
        }

        return _postItem;
    }
}
