package com.example.level2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private Button rBtLogin;
    private Button rBtRegister;
    private EditText rEtUsername;
    private EditText rEtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    private void initView() {
        rBtLogin = findViewById(R.id.button_rebottom_login);
        rEtPass = findViewById(R.id.edit_rebottom_pass);
        rEtUsername = findViewById(R.id.edit_retop_account);
        rBtRegister = findViewById(R.id.button_rebottom_register);
    }

    private void initEvent() {
        rBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Register() {
        String password = rEtPass.getText().toString();
        String username = rEtUsername.getText().toString();
        if (password.length() >= 6 && username.length() != 0) {
            SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
            SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
            String name=pref.getString(password,"");
            if(username.equals(name)){
                Toast.makeText(this, "账号重复，，，", Toast.LENGTH_SHORT).show();
            }else{
            Toast.makeText(this, "注册成功，，，", Toast.LENGTH_SHORT).show();
            editor.putString(password,username);
            editor.apply();
            finish();}
        } else {
            Toast.makeText(this, "密码不可小于六个字且名字不可为空", Toast.LENGTH_SHORT).show();
        }
    }


}