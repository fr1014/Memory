package com.fr.memroy.room.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.fr.mediafile.bean.Image;

/**
 * 创建时间:2020/2/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
/*
 *外键可以允许你定义被引用的Entity更新时发生的行为。
 * 例如，你可以定义当删除ImageFolder时对应的Image类也被删除。
 * 可以在@ForeignKey中添加onDelete = CASCADE实现
 */
@Entity(tableName = "images", foreignKeys = {
        @ForeignKey(entity = ImageFolderEntity.class,
                parentColumns = "id",
                childColumns = "imageId", onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "imageId")
        })
public class ImageEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int imageId;
    @Embedded
    private Image image;

    public ImageEntity(int imageId, Image image) {
        this.imageId = imageId;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
