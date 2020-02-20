package com.fr.memroy.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.fr.memroy.room.dao.ImageDao;
import com.fr.memroy.room.dao.ImageFolderDao;
import com.fr.memroy.room.entity.ImageEntity;
import com.fr.memroy.room.entity.ImageFolderEntity;

/**
 * 创建时间:2020/2/18
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
//singleton
@Database(entities = {ImageFolderEntity.class, ImageEntity.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase INSTANCE;

    public static synchronized AppDataBase getInstance(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "app_database")
//                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract ImageFolderDao getImageFolderDao();
    public abstract ImageDao getImageDao();
}
