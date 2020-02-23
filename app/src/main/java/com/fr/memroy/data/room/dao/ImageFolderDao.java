package com.fr.memroy.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.fr.memroy.data.room.entity.ImageFolderEntity;

import java.util.List;

/**
 * 创建时间:2020/2/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
@Dao  //数据库访问接口
public interface ImageFolderDao {
    @Insert
    void insertImageFolder(ImageFolderEntity imageFolder);

    @Update
    void updateImageFolder(ImageFolderEntity imageFolder);

    @Delete
    void deleteImageFolder(ImageFolderEntity...imageFolder);

    @Query("SELECT * FROM image_folder ORDER BY ID DESC")
    LiveData<List<ImageFolderEntity>> getAllImageFoldersLive();

    @Query("SELECT * FROM image_folder ORDER BY ID DESC")
    List<ImageFolderEntity> getAllImageFolders();
}
