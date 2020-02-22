package com.fr.memroy.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fr.MyApplication;
import com.fr.memroy.data.room.AppDataBase;

/**
 * 创建时间:2020/2/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseFragment extends Fragment {
    protected AppDataBase dataBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        initView(view);

        dataBase = MyApplication.getInstance().getDataBase();

        initData();
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initData();
}
