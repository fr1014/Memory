package com.fr.memroy.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 创建时间:2020/2/20
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public abstract class SimpleConsumer<T> implements Observer<T> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        accept(t);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void accept(T t);
}
