package com.example.admin.submittyproject;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.VolleyError;
import com.example.admin.submittyproject.data.Callback;
import com.example.admin.submittyproject.data.NetworkManager;
import com.example.admin.submittyproject.sources.Urls;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.admin.submittyproject", appContext.getPackageName());
    }

    @Test
    public void testLoginAPI() throws Exception{
        Context appContext = InstrumentationRegistry.getTargetContext();
        NetworkManager networkManager = NetworkManager.getInstance(appContext);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "xricxy1314");
        jsonObject.put("password", "xuran1");
        networkManager.post(Urls.login, jsonObject, new Callback() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    assert(response.getInt("status") == 200);
                    assert(!response.getString("token").isEmpty());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
    }
}
