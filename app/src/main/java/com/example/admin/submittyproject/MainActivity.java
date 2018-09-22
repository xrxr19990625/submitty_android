package com.example.admin.submittyproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.admin.submittyproject.application.userservice.UserService;
import com.example.admin.submittyproject.sources.ErrorCodes;
import com.example.admin.submittyproject.sources.LoginInformation;
import com.example.admin.submittyproject.ui.HomeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        final String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        UserService login = new UserService(username, password);
        login.login(this, new UserService.LoginListener() {
            @Override
            public void onSuccess(String token) {
                Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                LoginInformation.username = username;
                LoginInformation.token = token;
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
            }
            @Override
            public void onFailure(int errorCode) {
                switch (errorCode){
                    case ErrorCodes.WRONG_PASSWORD:
                        Toast.makeText(MainActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                        break;
                    case ErrorCodes.PARSE_ERROR:
                        Toast.makeText(MainActivity.this, "Error parsing data from server", Toast.LENGTH_SHORT).show();
                        break;
                    case ErrorCodes.NETWORK_ERROR:
                        Toast.makeText(MainActivity.this, "Error connecting to server", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }
}
