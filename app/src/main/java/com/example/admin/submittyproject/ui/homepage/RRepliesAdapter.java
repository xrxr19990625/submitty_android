package com.example.admin.submittyproject.ui.homepage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.submittyproject.R;
import com.example.admin.submittyproject.tools.TimeConverter;

import java.util.List;
import java.util.Map;

public class RRepliesAdapter extends BaseAdapter{

    private List<Map<String, Object>> mLayout;
    private LayoutInflater mInflater;
    public RRepliesAdapter(List<Map<String, Object>> layout, Context context){
        this.mLayout = layout;
        this.mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return mLayout.size();
    }

    @Override
    public Map<String, Object> getItem(int i) {
        return mLayout.get(i);
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
            view = mInflater.inflate(R.layout.rreplies_item_layout, null);
            viewHolder.username = view.findViewById(R.id.tv_subFloorHeader);
            viewHolder.content = view.findViewById(R.id.tv_subFloorContent);
            viewHolder.time = view.findViewById(R.id.tv_subFloorTime);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String header;
        if ((int)this.getItem(i).get("parent") != -1) header = (String)this.getItem(i).get("username") + " replied to " + (String)this.getItem(i).get("parent_name");
        else header = (String)this.getItem(i).get("username");
        viewHolder.username.setText(header);
        viewHolder.content.setText((String)this.getItem(i).get("message"));
        viewHolder.time.setText(TimeConverter.timestampToDateAndTime((long)this.getItem(i).get("time")));
        return view;
    }

    private class ViewHolder{
        TextView username, content, time;
    }
}
