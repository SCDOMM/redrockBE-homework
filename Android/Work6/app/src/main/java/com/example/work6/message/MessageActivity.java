package com.example.work6.message;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.work6.R;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView mRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initRv();
    }
    public void initRv(){
    mRv=findViewById(R.id.mess_rv);
    messAdapter adapter=new messAdapter(getMessData());
    mRv.setAdapter(adapter);
    adapter.setOnItemClickListener(new messAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(messData data) {
            Toast.makeText(MessageActivity.this,"呃呃呃",Toast.LENGTH_SHORT).show();
        }
    });
    mRv.setLayoutManager(new LinearLayoutManager(this));
    }
    private ArrayList<messData> getMessData(){
        ArrayList<messData> dataList=new ArrayList<>();
        dataList.add(new messData("扫福瑞幼儿园","骚福瑞叫爸爸",R.drawable.furry));
        dataList.add(new messData("RR网校","RAF老实耄耋：我去，给移动大蛇跪了",R.drawable.test));
        dataList.add(new messData("扫福瑞幼儿园","骚福瑞叫爸爸",R.drawable.furry));
        dataList.add(new messData("RR网校","RAF老实耄耋：我去，给移动大蛇跪了",R.drawable.test));
        dataList.add(new messData("扫福瑞幼儿园","骚福瑞叫爸爸",R.drawable.furry));
        dataList.add(new messData("RR网校","RAF老实耄耋：我去，给移动大蛇跪了",R.drawable.test));
        return dataList;
    }

}