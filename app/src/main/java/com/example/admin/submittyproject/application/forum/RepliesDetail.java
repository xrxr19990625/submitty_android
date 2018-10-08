package com.example.admin.submittyproject.application.forum;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RepliesDetail {
    private static RepliesDetail sInstance = null;
    private List<Map<String, Object>> mDetail;
    public static RepliesDetail getsInstance(Context context) {
        if (sInstance == null){
            synchronized (Forum.class){
                if (sInstance == null){
                    sInstance = new RepliesDetail(context);
                }
            }
        }
        return sInstance;
    }

    private RepliesDetail(Context context){

    }

    public List<Map<String, Object>> getDetail() {
        return mDetail;
    }

    public void setDetail(List<Map<String, Object>> detail) {
        this.mDetail = detail;
    }
}
