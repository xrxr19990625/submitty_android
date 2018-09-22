package com.example.admin.submittyproject.ui.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.submittyproject.R;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2018/9/15.
 */

public class ForumAdapter extends BaseAdapter {
    private List<Map<String, Object>> mLayout;
    private LayoutInflater inflater;
    public ForumAdapter(List<Map<String, Object>> layout, Context context){
        this.mLayout = layout;
        this.inflater = LayoutInflater.from(context);
    }

    public void addItem(Map<String, Object> item){
        this.mLayout.add(item);
        notifyDataSetChanged();
    }

    public void setNewLayout(List<Map<String, Object>> layout){
        this.mLayout = layout;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mLayout.size();
    }

    @Override
    public Map<String, Object> getItem(int i) {
        return this.mLayout.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            view = inflater.inflate(R.layout.forum_item_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.user_id = view.findViewById(R.id.tv_userid);
            viewHolder.lastReply = view.findViewById(R.id.tv_lastReply);
            viewHolder.message = view.findViewById(R.id.tv_message);
            viewHolder.title = view.findViewById(R.id.tv_postTitle);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.user_id.setText((String)this.getItem(i).get("username"));
        viewHolder.lastReply.setText((String)this.getItem(i).get("time"));
        viewHolder.message.setText((String)this.getItem(i).get("message"));
        viewHolder.title.setText((String)this.getItem(i).get("title"));
        return view;
    }

    private class ViewHolder{
        TextView user_id, lastReply, message, title;
    }
}
