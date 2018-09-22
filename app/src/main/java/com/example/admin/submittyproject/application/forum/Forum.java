package com.example.admin.submittyproject.application.forum;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.admin.submittyproject.data.Callback;
import com.example.admin.submittyproject.data.NetworkManager;
import com.example.admin.submittyproject.sources.ErrorCodes;
import com.example.admin.submittyproject.sources.Urls;
import com.example.admin.submittyproject.tools.TimeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/9/14.
 */

public class Forum {
    private NetworkManager mNetworkManager;
    private List<Map<String, Object>> items;
    private static Forum sInstance = null;

    public static Forum getInstance(Context context){
        if (sInstance == null){
            synchronized (Forum.class){
                if (sInstance == null){
                    sInstance = new Forum(context);
                }
            }
        }
        return sInstance;
    }

    private Forum(Context context){
        mNetworkManager = NetworkManager.getInstance(context);
    }

    public void getAll(final ResultListener callback){
        try {
            JSONObject request = new JSONObject();
            mNetworkManager.post(Urls.forum, request, new Callback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        JSONArray res = (JSONArray) response.get("all");
                        List<Map<String, Object>> layout = new ArrayList<>();
                        for (int i = 0;i < res.length();i ++){
                            Map<String, Object> item = new HashMap<>();
                            JSONObject jobj = new JSONObject(res.getString(i));
                            item.put("username", jobj.getString("username"));
                            item.put("id", jobj.getInt("id"));
                            item.put("title", jobj.getString("title"));
                            item.put("time", TimeConverter.timestampToDateAndTime(jobj.getLong("last_reply")));
                            item.put("message", jobj.getString("message"));
                            layout.add(item);
                        }
                        items = layout;
                        callback.onSuccess(layout);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFailure(ErrorCodes.PARSE_ERROR);
                    }
                }

                @Override
                public void onFailure(VolleyError error) {
                    callback.onFailure(ErrorCodes.NETWORK_ERROR);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            callback.onFailure(ErrorCodes.NETWORK_ERROR);
        }
    }

    public void refresh(ResultListener listener){
        this.getAll(listener);
    }

    public void newPost(String title, String message, String username, final ResultListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", title);
            jsonObject.put("message", message);
            jsonObject.put("username", username);
            try {
                mNetworkManager.post(Urls.NEW_POST, jsonObject, new Callback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        listener.onSuccess(null);
                    }

                    @Override
                    public void onFailure(VolleyError error) {
                        listener.onFailure(ErrorCodes.NETWORK_ERROR);
                    }
                });
            } catch (IOException e) {
                listener.onFailure(ErrorCodes.NETWORK_ERROR);
                e.printStackTrace();
            }
        } catch (JSONException e) {
            listener.onFailure(ErrorCodes.PARSE_ERROR);
            e.printStackTrace();
        }
    }

    public void replyToThread(int root_id, String username, String message, final ResultListener listener){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("root_id", root_id);
            jsonObject.put("username", username);
            jsonObject.put("message", message);
            try {
                mNetworkManager.post(Urls.REPLY_TO_THREAD, jsonObject, new Callback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        listener.onSuccess(null);
                    }

                    @Override
                    public void onFailure(VolleyError error) {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void queryDetail(int id, final DetailListener listener){
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            try {
                mNetworkManager.post(Urls.THREAD_DETAIL, jsonObject, new Callback() {
                    @Override
                    public void onSuccess(JSONObject response) {
                        try {
                            JSONArray replies = (JSONArray)response.get("replies");
                            JSONObject root = new JSONObject(response.getString("root_info"));
                            List<Map<String, Object>> replies_layout = new ArrayList<>();
                            for (int i = 0;i < replies.length();i ++){
                                Map<String, Object> reply = new HashMap<>();
                                JSONObject res = new JSONObject(replies.getString(i));
                                reply.put("id", res.getInt("id"));
                                reply.put("username", res.getString("username"));
                                reply.put("message", res.getString("message"));
                                reply.put("root", res.getInt("root"));
                                reply.put("time", res.getLong("time"));
                                replies_layout.add(reply);
                            }
                            Map<String, Object> root_layout = new HashMap<>();
                            root_layout.put("id", root.getInt("id"));
                            root_layout.put("username", root.getString("username"));
                            root_layout.put("time", root.getLong("time"));
                            root_layout.put("title", root.getString("title"));
                            root_layout.put("message", root.getString("message"));
                            listener.onSuccess(replies_layout, root_layout);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(VolleyError error) {

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface ResultListener{
        void onSuccess(List<Map<String, Object>> layout);
        void onFailure(int errorCode);
    }

    public interface DetailListener{
        void onSuccess(List<Map<String, Object>> replies, Map<String, Object> root);
        void onFailure(int errorCode);
    }
}
