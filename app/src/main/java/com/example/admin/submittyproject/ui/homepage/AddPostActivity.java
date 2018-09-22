package com.example.admin.submittyproject.ui.homepage;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.submittyproject.R;
import com.example.admin.submittyproject.application.forum.Forum;
import com.example.admin.submittyproject.sources.LoginInformation;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPostActivity extends AppCompatActivity {

    @BindView(R.id.et_postTitle)
    EditText etPostTitle;
    @BindView(R.id.et_postMessage)
    EditText etPostMessage;
    @BindView(R.id.btn_post)
    Button btnPost;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    Forum forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //判断是否有焦点
        if (hasFocus) {
            final View decorView = getWindow().getDecorView();
            final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(flags);
            decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                @Override
                public void onSystemUiVisibilityChange(int i) {
                    if ((i & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                        decorView.setSystemUiVisibility(flags);
                    }
                }
            });
        }
    }

    @OnClick({R.id.btn_post, R.id.iv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_post:
                String title = etPostTitle.getText().toString();
                String message = etPostMessage.getText().toString();
                String username = LoginInformation.username;
                forum = Forum.getInstance(this);
                forum.newPost(title, message, username, new Forum.ResultListener() {
                    @Override
                    public void onSuccess(List<Map<String, Object>> layout) {
                        Toast.makeText(AddPostActivity.this, "Thread was posted successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(int errorCode) {

                    }
                });
                break;
            case R.id.iv_cancel:
                finish();
                break;
        }
    }
}
