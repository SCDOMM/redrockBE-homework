package com.example.work6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.work6.message.MessageActivity;
import com.example.work6.recycle.RecycleActivity;

public class MainActivity extends AppCompatActivity {
    private Button mBtm;
    private Button mBtr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBt();
        initEvent();
    }
    private void initBt(){
        mBtm=findViewById(R.id.main_button_mess);
        mBtr=findViewById(R.id.main_button_recy);
    }
    private void initEvent(){
        mBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkMessage();
                }
        });
        mBtr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRecycle();
            }
        });
    }
    private void checkMessage(){
    Intent intent=new Intent(MainActivity.this, MessageActivity.class);
    startActivity(intent);
    }
    private void checkRecycle(){
    Intent intent=new Intent(MainActivity.this, RecycleActivity.class);
    startActivity(intent);
    }
}