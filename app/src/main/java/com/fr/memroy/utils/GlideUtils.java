package com.fr.memroy.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fr.mediafile.utils.FileManager;

/**
 * 创建时间:2020/3/10
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class GlideUtils {

    public static void load(String path,ImageView view){
        Glide.with(view.getContext())
                .load(FileManager.getImageContentUri(view.getContext(),path))
                .into(view);
    }
}
