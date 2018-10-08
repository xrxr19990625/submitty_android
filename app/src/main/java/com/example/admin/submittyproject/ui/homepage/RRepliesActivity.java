package com.example.admin.submittyproject.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.submittyproject.R;
import com.example.admin.submittyproject.application.forum.Forum;
import com.example.admin.submittyproject.sources.LoginInformation;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RRepliesActivity extends AppCompatActivity {

    @BindView(R.id.tv_backToThread)
    TextView tvBackToThread;
    @BindView(R.id.tv_replyUsername)
    TextView tvReplyUsername;
    @BindView(R.id.tv_replyPostTime)
    TextView tvReplyPostTime;
    @BindView(R.id.tv_replyContent)
    TextView tvReplyContent;
    @BindView(R.id.lv_subreplies)
    ListView lvSubreplies;
    @BindView(R.id.et_replyToReply)
    EditText etReplyToReply;
    @BindView(R.id.btn_replyToReply)
    Button btnReplyToReply;
    @BindView(R.id.ll_reply)
    LinearLayout llReply;

    Forum forum;
    int parent_id = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rreplies);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        forum = Forum.getInstance(RRepliesActivity.this);
        Bundle bundle = intent.getExtras();
        final String username = bundle.getString("username");
        String time = bundle.getString("time");
        String content = bundle.getString("message");
        final int root_thread = bundle.getInt("root_thread");
        final int root_id = bundle.getInt("root_id");
        tvReplyUsername.setText(username);
        tvReplyPostTime.setText(time);
        tvReplyContent.setText(content);
        forum.querySubreplies(root_id, new Forum.ResultListener() {
            @Override
            public void onSuccess(List<Map<String, Object>> layout) {
                final RRepliesAdapter adapter = new RRepliesAdapter(layout, RRepliesActivity.this);
                lvSubreplies.setAdapter(adapter);
                lvSubreplies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        parent_id = (int)adapter.getItem(i).get("id");
                        etReplyToReply.setHint("replies to " + (String)adapter.getItem(i).get("username"));
                    }
                });
                btnReplyToReply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("click", "executed");
                        forum.replyToReply(root_thread, root_id, parent_id, etReplyToReply.getText().toString(), LoginInformation.username, new Forum.ResultListener() {
                            @Override
                            public void onSuccess(List<Map<String, Object>> layout) {
                                Toast.makeText(RRepliesActivity.this, "Reply sent successfully", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onFailure(int errorCode) {

                            }
                        });

                    }
                });
            }

            @Override
            public void onFailure(int errorCode) {
                Toast.makeText(RRepliesActivity.this, Integer.toString(errorCode), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    @OnClick({R.id.tv_backToThread})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_backToThread:
                finish();
                break;
        }
    }
}
