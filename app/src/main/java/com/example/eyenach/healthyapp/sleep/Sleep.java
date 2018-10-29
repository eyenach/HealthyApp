package com.example.eyenach.healthyapp.sleep;

import android.content.ContentValues;

//ContentValues
public class Sleep {

    ContentValues _row = new ContentValues();

    int diff_time;

    public  void setSleep(String sleep, String wake, String date) {
        this._row.put("sleep", sleep);
        this._row.put("wake", wake);
        this._row.put("date", date);
    }

    public ContentValues getContent() {
        return _row;
    }

    public int getDiff_time() {
        return diff_time;
    }

    public void setDiff_time(int diff_time) {
        this.diff_time = diff_time;
    }
}
