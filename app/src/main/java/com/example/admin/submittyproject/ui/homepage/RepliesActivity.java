package com.example.admin.submittyproject.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.submittyproject.R;
import com.example.admin.submittyproject.application.forum.Forum;
import com.example.admin.submittyproject.sources.LoginInformation;
import com.example.admin.submittyproject.tools.TimeConverter;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RepliesActivity extends AppCompatActivity {
    Forum forum;
    @BindView(R.id.tv_refreshThread)
    TextView tvRefreshThread;
    @BindView(R.id.pb_refreshThread)
    ProgressBar pbRefreshThread;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.et_reply)
    EditText etReply;
    @BindView(R.id.btn_reply)
    Button btnReply;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;
    @BindView(R.id.tv_threadTitle)
    TextView tvThreadTitle;
    @BindView(R.id.tv_postUsername)
    TextView tvPostUsername;
    @BindView(R.id.tv_threadPostTime)
    TextView tvThreadPostTime;
    @BindView(R.id.tv_threadBody)
    TextView tvThreadBody;
    @BindView(R.id.lv_replies)
    ListView lvReplies;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replies);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getInt("id");
        forum = Forum.getInstance(this);
        forum.queryDetail(id, new Forum.DetailListener() {
            @Override
            public void onSuccess(List<Map<String, Object>> replies, Map<String, Object> root) {
                tvThreadTitle.setText((String)root.get("title"));
                tvPostUsername.setText((String)root.get("username"));
                tvThreadPostTime.setText(TimeConverter.timestampToDateAndTime((long)root.get("time")));
                tvThreadBody.setText((String)root.get("message"));
                RepliesAdapter repliesAdapter = new RepliesAdapter(replies, RepliesActivity.this);
                lvReplies.setAdapter(repliesAdapter);
                lvReplies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                });
            }

            @Override
            public void onFailure(int errorCode) {

            }
        });
    }

    @OnClick({R.id.tv_refreshThread, R.id.tv_back, R.id.btn_reply})
    public void onViewClicked(View view) {
        forum = Forum.getInstance(this);
        switch (view.getId()) {
            case R.id.tv_refreshThread:
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_reply:
                String message = etReply.getText().toString();
                String username = LoginInformation.username;
                forum.replyToThread(id, username, message, new Forum.ResultListener() {
                    @Override
                    public void onSuccess(List<Map<String, Object>> layout) {
                        Toast.makeText(RepliesActivity.this, "Reply Successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int errorCode) {

                    }
                });
                break;
        }
    }
}
