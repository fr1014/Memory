package com.fr.memroy.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fr.memroy.data.room.entity.ImageEntity;

import java.util.List;

/**
 * 创建时间:2020/2/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
@Dao
public interface ImageDao {
    @Insert
    void insetImages(ImageEntity...imageEntities);

    @Update
    void updateImages(ImageEntity...imageEntities);

    @Delete
    void deleteImages(ImageEntity...imageEntities);

    @Query("SELECT * FROM images WHERE imageId IN (:imageId) ORDER BY ID DESC")
    LiveData<List<ImageEntity>> getAllImagesLive(int imageId);
}
