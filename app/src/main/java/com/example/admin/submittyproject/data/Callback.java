package com.example.admin.submittyproject.data;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by admin on 2018/9/12.
 */

public interface Callback{
    void onSuccess(JSONObject response);
    void onFailure(VolleyError error);
}
