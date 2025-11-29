package com.example.level2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initEvent();
        };
    private void initView(){
        textView=findViewById(R.id.tv_home_word);
    }
    private void initEvent(){
        Intent intent=getIntent();
        String username=intent.getStringExtra("username");
        String dps=intent.getStringExtra("密码");
        textView.setText("你是"+username+"，欢迎归来！");
    }
    public static void startActivity(Context context, String username, String password){
        Intent intent=new Intent(context,HomeActivity.class);
        intent.putExtra("username",username);
        intent.putExtra("密码",password);
        context.startActivity(intent);
    }

    }