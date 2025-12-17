package com.example.work7;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.work7.GET.GETActivity;
import com.example.work7.POST.POSTActivity;


public class MainActivity extends AppCompatActivity {
    private Button mBt;
    private Button mBt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        mBt = findViewById(R.id.main_mBt);
        mBt2 = findViewById(R.id.main_mBt1);
    }

    private void initEvent() {
        mBt.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, GETActivity.class);
            startActivity(intent);
        });
        mBt2.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, POSTActivity.class);
            startActivity(intent);
        });
    }
}