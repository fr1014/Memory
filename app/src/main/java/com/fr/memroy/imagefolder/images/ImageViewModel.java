package com.fr.memroy.imagefolder.images;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fr.MyApplication;
import com.fr.memroy.data.room.AppDataBase;
import com.fr.memroy.data.room.dao.ImageDao;
import com.fr.memroy.data.room.entity.ImageEntity;
import com.fr.memroy.rx.RxSchedulers;
import com.fr.memroy.rx.SimpleConsumer;

import java.util.List;

import io.reactivex.Observable;

/**
 * 创建时间:2020/3/10
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ImageViewModel extends ViewModel {
    private LiveData<List<ImageEntity>> allImagesLive;
    private ImageDao imageDao;

    public ImageViewModel() {
        AppDataBase dataBase = MyApplication.getInstance().getDataBase();
        imageDao = dataBase.getImageDao();
    }

    public LiveData<List<ImageEntity>> getAllImagesLive(int imageId) {
        if (allImagesLive == null) {
            allImagesLive = imageDao.getAllImagesLive(imageId);
        }
        return allImagesLive;
    }

    public void insert(ImageEntity... imageEntities) {
        Observable.just(imageEntities)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<ImageEntity[]>() {
                    @Override
                    public void accept(ImageEntity[] imageEntities) {
                        imageDao.insetImages(imageEntities);
                    }
                });
    }

    public void update(ImageEntity imageEntity) {
        Observable.just(imageEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<ImageEntity>() {
                    @Override
                    public void accept(ImageEntity imageEntity) {
                        imageDao.updateImages(imageEntity);
                    }
                });
    }

    public void delete(ImageEntity... imageEntities) {
        Observable.just(imageEntities)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<ImageEntity[]>() {
                    @Override
                    public void accept(ImageEntity[] imageEntities) {
                        imageDao.deleteImages(imageEntities);
                    }
                });
    }
}
