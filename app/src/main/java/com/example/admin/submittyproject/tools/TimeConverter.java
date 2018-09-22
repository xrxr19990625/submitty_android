package com.example.admin.submittyproject.tools;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2018/9/16.
 */

public class TimeConverter {
    public static String timestampToDateAndTime(long timeStamp){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format =  new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return format.format(timeStamp);
    }
    public static long dateToTimestamp(String time){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date date = simpleDateFormat.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }
}
