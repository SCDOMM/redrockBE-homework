package com.example.work7.GET;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.work7.R;

import java.util.ArrayList;

public class GETActivity extends AppCompatActivity {
    private static RecyclerView rView;
    private Handler mHandler;
    public static ArrayList<GETData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getactivity);
        rView = findViewById(R.id.rv_get);
        initEvent();
    }
    private void initEvent() {
        mHandler = new utils.GETHandler();
        Message message = new Message();
        message.obj = "https://www.wanandroid.com/banner/json";
        message.what = 1;
        mHandler.sendMessage(message);
    }
    private void initEvent2(jsonData jData){
        jsonData.DD dd=jData.data.get(0);
        data.add(new GETData(dd.title,"title"));
        data.add(new GETData(dd.desc,"desc"));
        data.add(new GETData(dd.url,"url"));
        data.add(new GETData(dd.imagePath,"imagePath"));
        data.add(new GETData(String.valueOf(dd.id),"id"));
        data.add(new GETData(String.valueOf(dd.isVisible),"isVisible"));
        data.add(new GETData(String.valueOf(dd.type),"type"));

        GETAdapter adapter=new GETAdapter(data);
        rView.setAdapter(adapter);
        rView.setLayoutManager(new LinearLayoutManager(GETActivity.this));
    }
    static class GetInfo extends Handler {
        public GetInfo(Looper looper){
            super(looper);
        }
        public GetInfo(){
            super();
        }
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 2) {
                String back = (String) msg.obj;
                utils tool = new utils();
                jsonData getData = new jsonData();
                getData = tool.decodeJSON(back);
                GETActivity ac=new GETActivity();
                ac.initEvent2(getData);
            } else {
                String err = (String) msg.obj;
                Log.e("戳啦", err);
            }
        }
    }
}