package com.example.level2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private Button mBtLogin;
    private Button mBtRegister;
    private EditText mEtUsername;
    private EditText mEtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        initEvent();
    }
    private void initview() {
        mBtLogin = findViewById(R.id.button_bottom_login);
        mEtUsername = findViewById(R.id.edit_top_account);
        mEtPass = findViewById(R.id.edit_bottom_pass);
        mBtRegister=findViewById(R.id.button_bottom_register);
    }
    private void initEvent() {
        mBtLogin.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            login();
                                        }
                                    }
                                    );
        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void login() {
        String password = mEtPass.getText().toString();
        String username = mEtUsername.getText().toString();
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        String account=pref.getString(password,"");
        if (username.equals(account)) {
            loginSuccess(username, password);
        } else {
            loginFail();
        }
    }
    private void loginSuccess(String username, String password) {
        HomeActivity.startActivity(this, username, password);
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
    }
    private void loginFail() {
        Toast.makeText(this, "登录失败，，", Toast.LENGTH_SHORT).show();
    }
    private void Register(){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}

