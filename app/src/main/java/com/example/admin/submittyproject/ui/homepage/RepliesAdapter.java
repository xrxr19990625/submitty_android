package com.example.admin.submittyproject.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.submittyproject.R;
import com.example.admin.submittyproject.tools.TimeConverter;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/9/18.
 */

public class RepliesAdapter extends BaseAdapter{

    private List<Map<String, Object>> mReplies;
    private LayoutInflater inflater;

    public RepliesAdapter(List<Map<String, Object>> replies, Context context){
        mReplies = replies;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mReplies.size();
    }

    @Override
    public Map<String, Object> getItem(int i) {
        return mReplies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.replies_item_layout, null);
            viewHolder.user_id = view.findViewById(R.id.tv_replyUsername);
            viewHolder.time = view.findViewById(R.id.tv_replyPostTime);
            viewHolder.message = view.findViewById(R.id.tv_replyBody);
            viewHolder.no_of_replies = view.findViewById(R.id.tv_no_of_replies);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.user_id.setText((String)this.getItem(i).get("username"));
        viewHolder.message.setText((String)this.getItem(i).get("message"));
        viewHolder.time.setText(TimeConverter.timestampToDateAndTime((long)this.getItem(i).get("time")));
        viewHolder.no_of_replies.setText(Integer.toString((int)this.getItem(i).get("count")) + " replies to this floor");
        return view;
    }

    public class ViewHolder{
        TextView user_id, time, message, no_of_replies;
    }
}
