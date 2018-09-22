package com.example.admin.submittyproject.data;


import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * Created by admin on 2018/9/12.
 */

public class NetworkManager {
    private static NetworkManager sInstance = null;
    private RequestQueue mRequestQueue;
    private WeakReference<Context> mContext;
    // Singleton. Only one instance of network manager is needed
    // and this manager should remain constant globally.
    public static NetworkManager getInstance(Context context){
        if (sInstance == null){
            synchronized (NetworkManager.class){
                if (sInstance == null){
                    sInstance = new NetworkManager(context);
                }
            }
        }
        return sInstance;
    }
    private NetworkManager(Context context){
        mContext = new WeakReference<>(context);
        mRequestQueue = Volley.newRequestQueue(mContext.get());
    }
    // post json request
    public void post(String url, JSONObject request, final Callback callback) throws IOException{
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error);
            }
        });
        mRequestQueue.add(jsonObjectRequest);
    }
}
