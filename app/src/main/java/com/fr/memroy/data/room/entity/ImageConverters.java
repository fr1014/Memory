package com.fr.memroy.data.room.entity;

import androidx.room.TypeConverter;

import com.fr.mediafile.bean.Image;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * 创建时间:2020/2/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
//用于解决运行时泛型擦除的问题
public class ImageConverters {
    @TypeConverter
    public Image stringToObject(String value){
        Gson gson = new Gson();
        Type type = new TypeToken<Image>() {
        }.getType();
        return gson.fromJson(value,type);
    }

    @TypeConverter
    public String objectToString(Image image){
        Gson gson = new Gson();
        return gson.toJson(image);
    }
}
