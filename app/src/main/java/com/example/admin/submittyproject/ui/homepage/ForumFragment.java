package com.example.admin.submittyproject.ui.homepage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.submittyproject.R;
import com.example.admin.submittyproject.application.forum.Forum;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ForumFragment extends Fragment {

    @BindView(R.id.lv_messages)
    ListView lvMessages;
    Unbinder unbinder;
    @BindView(R.id.tv_forumTitle)
    TextView tvForumTitle;
    @BindView(R.id.tv_refresh)
    TextView tvRefresh;
    @BindView(R.id.pb_refresh)
    ProgressBar pbRefresh;
    ForumAdapter forumAdapter;
    Forum forum;
    @BindView(R.id.tv_newPost)
    TextView tvNewPost;
    @BindView(R.id.search_bar)
    FrameLayout searchBar;
    private float mFirstY;
    private float mCurrentY;
    private int mTouchShop;
    boolean mShow;
    int direction;

    public ForumFragment() {
        // Required empty public constructor
    }

    public static ForumFragment getInstance() {
        return new ForumFragment();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forum, container, false);
        unbinder = ButterKnife.bind(this, v);
        forum = Forum.getInstance(getActivity());
        mTouchShop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
        forum.getAll(new Forum.ResultListener() {
            @Override
            public void onSuccess(List<Map<String, Object>> layout) {
                forumAdapter = new ForumAdapter(layout, getActivity());
                lvMessages.setAdapter(forumAdapter);
            }

            @Override
            public void onFailure(int errorCode) {
                Log.e("executed", Integer.toString(errorCode));
            }
        });
        tvNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddPostActivity.class));
                getActivity().overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
            }
        });
        lvMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String, Object> item = (Map<String, Object>) lvMessages.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(), RepliesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", (int)item.get("id"));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        lvMessages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mFirstY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mCurrentY = event.getY();
                        if (mCurrentY - mFirstY > mTouchShop) {
                            direction = 0;
                        } else if (mFirstY - mCurrentY > mTouchShop) {
                            direction = 1;
                        }
                        if (direction == 1) {
                            if (mShow) {
                                searchBar.setVisibility(View.GONE);
                                mShow = !mShow;
                            }
                        } else if (direction == 0) {
                            if (!mShow) {
                                searchBar.setVisibility(View.VISIBLE);
                                mShow = !mShow;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return false;
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_refresh)
    public void onViewClicked() {
        tvRefresh.setVisibility(View.INVISIBLE);
        pbRefresh.setVisibility(View.VISIBLE);
        Log.e("visible", Boolean.toString(pbRefresh.getVisibility() == View.VISIBLE));
        forum.refresh(new Forum.ResultListener() {
            @Override
            public void onSuccess(List<Map<String, Object>> layout) {
                forumAdapter.setNewLayout(layout);
                pbRefresh.setVisibility(View.INVISIBLE);
                tvRefresh.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int errorCode) {
            }
        });
    }
}
