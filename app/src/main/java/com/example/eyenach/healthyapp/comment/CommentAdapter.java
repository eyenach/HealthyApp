package com.example.eyenach.healthyapp.comment;

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

public class CommentAdapter extends ArrayAdapter {

    ArrayList<JSONObject> comment;
    Context context;

    TextView _id, _body, _name, _email;

    public CommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<JSONObject> objects) {
        super(context, resource, objects);

        this.comment = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View _commentItem = LayoutInflater.from(context).inflate(R.layout.fragment_comment_item, parent, false);

        JSONObject _postObj = comment.get(position);

        _id = _commentItem.findViewById(R.id.comment_item_id);
        _body = _commentItem.findViewById(R.id.comment_item_body);
        _name = _commentItem.findViewById(R.id.comment_item_name);
        _email = _commentItem.findViewById(R.id.comment_item_email);

        try {
            _id.setText(_postObj.getString("postId")+ " : " + _postObj.getString("id"));
            _body.setText(_postObj.getString("body"));
            _name.setText(_postObj.getString("name"));
            _email.setText("( "+_postObj.getString("email")+" )");
        } catch (JSONException e) {
            Log.d("POST", "ERROR!");
        }

        return _commentItem;
    }
}
