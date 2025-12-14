package com.example.work6.recycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.work6.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class RecycleActivity extends AppCompatActivity {
    public static void startActivity(Context context){
        Intent intent=new Intent(context,RecycleActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        ViewPager2 vp2 = findViewById(R.id.rec_vp);

        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentFrist());
        fragmentList.add(new FragmentSecond());
        fragmentList.add(new FragmentThird());
        recycleAdapter adapter = new recycleAdapter(this, fragmentList);
        vp2.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.rec_tab);
        new TabLayoutMediator(tabLayout, vp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) {
                    tab.setText("第一 fragment");
                } else if(position ==1){
                    tab.setText("第二 fragment");
                }else {
                    tab.setText("第三 fragment");
                }
            }
        }).attach();
    }
}