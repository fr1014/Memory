package com.fr.mediafile.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 创建时间:2020/2/16
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class CommonUtils {

    public static void ToastShort(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void ToastLong(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    /**
     * 判断字符串是否为空
     * @param s 要判断的字符串
     * @return true 不为空
     */
    public static Boolean isNotEmptyString(String s) {
        return s != null && s.length() > 0;
    }
}
