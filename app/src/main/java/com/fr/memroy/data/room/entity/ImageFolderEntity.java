package com.fr.memroy.data.room.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 创建时间:2020/2/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
@Entity(tableName = "image_folder")
public class ImageFolderEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "folder_name")  //列名
    private String name;               //文件夹名称

    @ColumnInfo(name = "folder_cover")
    private String imagePath;              //文件夹封面

    private String message;           //文件夹描述

    public ImageFolderEntity(String name, String imagePath, String message) {
        this.name = name;
        this.imagePath = imagePath;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
