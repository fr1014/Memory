package com.fr.mediafile.imageselect;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * 创建时间:2020/3/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CollectionAdapter extends FragmentStateAdapter {
    private String[] tabs;

    public CollectionAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setTabs(String[] tabs){
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putString(MediaFragment.ARG_PARAM, tabs[position]);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return tabs.length;
    }
}
