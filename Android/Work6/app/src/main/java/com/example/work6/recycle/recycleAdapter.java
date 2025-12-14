package com.example.work6.recycle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class recycleAdapter extends FragmentStateAdapter {
    public final ArrayList<Fragment> fragments;
    public recycleAdapter (@NonNull RecycleActivity recycleActivity,ArrayList<Fragment> fragments){
    super(recycleActivity);
    this.fragments=fragments;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
