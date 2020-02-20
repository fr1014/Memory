package com.fr;

import android.app.Application;

import com.fr.memroy.data.room.AppDataBase;
import com.fr.memroy.rx.RxSchedulers;
import com.fr.memroy.rx.SimpleConsumer;

import io.reactivex.Observable;

/**
 * 创建时间:2020/2/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class MyApplication extends Application {

    private static MyApplication application;
    private AppDataBase dataBase;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        Observable.just("")
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<String>() {
                    @Override
                    public void accept(String s) {
                        dataBase = AppDataBase.getInstance(application);
                    }
                });
    }

    public static MyApplication getInstance() {
        return application;
    }

    public AppDataBase getDataBase() {
        return dataBase;
    }
}
