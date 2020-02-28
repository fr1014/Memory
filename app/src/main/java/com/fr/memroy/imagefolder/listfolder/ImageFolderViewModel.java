package com.fr.memroy.imagefolder.listfolder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.fr.MyApplication;
import com.fr.memroy.data.room.AppDataBase;
import com.fr.memroy.data.room.dao.ImageFolderDao;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.rx.RxSchedulers;
import com.fr.memroy.rx.SimpleConsumer;

import java.util.List;

import io.reactivex.Observable;

/**
 * 创建时间:2020/2/28
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ImageFolderViewModel extends ViewModel {
    private ImageFolderDao imageFolderDao;
    private LiveData<List<ImageFolderEntity>> allImageFoldersLive;

    public ImageFolderViewModel() {
        AppDataBase dataBase = MyApplication.getInstance().getDataBase();
        imageFolderDao = dataBase.getImageFolderDao();
        allImageFoldersLive = imageFolderDao.getAllImageFoldersLive();
    }

    public LiveData<List<ImageFolderEntity>> getAllImageFoldersLive() {
        return allImageFoldersLive;
    }

    public void insert(ImageFolderEntity imageFolderEntity){
        Observable.just(imageFolderEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<ImageFolderEntity>() {
                    @Override
                    public void accept(ImageFolderEntity imageFolderEntity) {
                        imageFolderDao.insertImageFolder(imageFolderEntity);
                    }
                });
    }

    public void update(ImageFolderEntity imageFolderEntity){
        Observable.just(imageFolderEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<ImageFolderEntity>() {
                    @Override
                    public void accept(ImageFolderEntity imageFolderEntity) {
                        imageFolderDao.updateImageFolder(imageFolderEntity);
                    }
                });
    }

    public void delete(List<ImageFolderEntity> imageFolderEntities){
        Observable.just(imageFolderEntities)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<List<ImageFolderEntity>>() {
                    @Override
                    public void accept(List<ImageFolderEntity> imageFolderEntities) {
                        for (ImageFolderEntity imageFolderEntity : imageFolderEntities) {
                            imageFolderDao.deleteImageFolder(imageFolderEntity);
                        }
                    }
                });
    }
}
