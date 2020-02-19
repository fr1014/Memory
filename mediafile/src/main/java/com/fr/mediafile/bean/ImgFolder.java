package com.fr.mediafile.bean;

import com.fr.mediafile.utils.CommonUtils;

import java.util.ArrayList;

/**
 * 创建时间：2020/2/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ImgFolder {
    private String name;
    private ArrayList<Image> images;

    public ImgFolder(String name) {
        this.name = name;
    }

    public ImgFolder(String name, ArrayList<Image> images) {
        this.name = name;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        if (image != null && CommonUtils.isNotEmptyString(image.getPath())) {
            if (images == null) {
                images = new ArrayList<>();
            }
            images.add(image);
        }
    }

    @Override
    public String toString() {
        return "ImgFolder{" +
                "name='" + name + '\'' +
                ", images=" + images +
                '}';
    }
}
