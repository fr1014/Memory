package com.fr.memroy.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.fr.memroy.room.entity.ImageEntity;

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

//    @Query("SELECT * FROM images ORDER BY ID DESC")
//    List<Image> getAllImages();
}
