package com.fr.memroy.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;

import com.fr.memroy.R;
import com.fr.memroy.utils.CommonUtils;

/**
 * 创建时间:2020/2/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class BaseVMActivity<VM extends ViewModel> extends AppCompatActivity {
    protected VM viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        CommonUtils.setStatusBar(this, getColor(R.color.bg_home));

        initBundle();

        viewModel = initViewModel();

        initView();
        initData(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected void initBundle(){

    }

    protected abstract void initView();
    protected abstract VM initViewModel();
    protected abstract void initData(Bundle savedInstanceState);

    protected void initToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void startActivity(Activity activity,Class clazz){
        Intent intent = new Intent(activity,clazz);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter,R.anim.activity_exit);
    }

    protected void startActivityForResult(Activity activity,Class clazz,int requestCode){
        Intent intent = new Intent(activity,clazz);
        startActivityForResult(intent,requestCode);
        overridePendingTransition(R.anim.activity_enter,R.anim.activity_exit);
    }
}
