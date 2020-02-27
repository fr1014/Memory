package com.fr.memroy.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fr.MyApplication;
import com.fr.memroy.R;
import com.fr.memroy.data.room.AppDataBase;
import com.fr.memroy.utils.CommonUtils;

/**
 * 创建时间:2020/2/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected AppDataBase dataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        CommonUtils.setStatusBar(this, getColor(R.color.bg_home));

        dataBase = MyApplication.getInstance().getDataBase();

        initView();
        initData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();
    protected abstract void initData(Bundle savedInstanceState);

    protected void startActivity(Activity activity,Class clazz){
        Intent intent = new Intent(activity,clazz);
        startActivity(intent);
    }

    protected void startActivityForResult(Activity activity,Class clazz,int requestCode){
        Intent intent = new Intent(activity,clazz);
        startActivityForResult(intent,requestCode);
    }
}
