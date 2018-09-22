package com.example.admin.submittyproject.application.userservice;

import android.content.Context;

import com.android.volley.VolleyError;
import com.example.admin.submittyproject.data.Callback;
import com.example.admin.submittyproject.data.NetworkManager;
import com.example.admin.submittyproject.sources.ErrorCodes;
import com.example.admin.submittyproject.sources.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by admin on 2018/9/13.
 */

public class UserService {
    private String username_, password_;
    private NetworkManager mNetworkManager;

    public UserService(String username, String password){
        this.username_ = username;
        this.password_ = password;
    }

    public void login(Context context, final LoginListener loginListener){
        this.mNetworkManager = NetworkManager.getInstance(context);
        JSONObject loginRequest = new JSONObject();
        try {
            loginRequest.put("username", this.username_);
            loginRequest.put("password", this.password_);
        } catch (JSONException e) {
            loginListener.onFailure(ErrorCodes.PARSE_ERROR);
        }
        try {
            this.mNetworkManager.post(Urls.login, loginRequest, new Callback() {
                @Override
                public void onSuccess(JSONObject response) {
                    try {
                        if (response.getInt("status") == 200){
                            loginListener.onSuccess(response.getString("token"));
                        } else {
                            loginListener.onFailure(ErrorCodes.WRONG_PASSWORD);
                        }
                    } catch (JSONException e) {
                        loginListener.onFailure(ErrorCodes.PARSE_ERROR);
                    }
                }

                @Override
                public void onFailure(VolleyError error) {
                    loginListener.onFailure(ErrorCodes.NETWORK_ERROR);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface LoginListener{
        void onSuccess(String token);
        void onFailure(int errorCode);
    }
}
