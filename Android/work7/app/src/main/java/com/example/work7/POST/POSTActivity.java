package com.example.work7.POST;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.work7.R;

public class POSTActivity extends AppCompatActivity {
    private EditText mEt1;
    private EditText mEt2;
    private EditText mEt3;
    private Button mBt;
    private Handler mainHandler;
    private static POSTActivity currentInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postactivity);
        utils A = new utils();
        mainHandler = A.new POSTHandler();
        initView();
        mBt.setOnClickListener((view) -> initEvent());
        currentInstance=this;
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        currentInstance=null;
    }

    private void initView() {
        mEt1 = findViewById(R.id.et_post_acc);
        mEt2 = findViewById(R.id.et_post_pass);
        mEt3 = findViewById(R.id.et_post_repass);
        mBt = findViewById(R.id.bt_post_reg);
    }

    private void initEvent() {
        String Account = mEt1.getText().toString();
        String Password = mEt2.getText().toString();
        String rePassword = mEt3.getText().toString();
        Login(Account, Password, rePassword);
    }

    private void Login(String Account, String Password, String rePassword) {
        String url = "https://www.wanandroid.com/user/register";
        utils.MessageData data = new utils.MessageData(url, Account, Password, rePassword);
        Message msg = new Message();
        msg.what = 1;
        msg.obj = data;
        mainHandler.sendMessage(msg);
    }

    public void feedBack(String str) {
        Log.d("测试?", str);
        Toast.makeText(POSTActivity.this,str, Toast.LENGTH_SHORT).show();
    }

    static class response extends Handler {
        public response(Looper mainLooper) {
            super(mainLooper);
        }

        public response() {
            super();
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                String res = msg.obj.toString();
                currentInstance.feedBack(res);
                Log.e("测试：别再报错了我求你了", "？");
            }
        }
    }
}