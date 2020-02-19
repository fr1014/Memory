package com.fr.mediafile.utils;

import java.io.File;

/**
 * 创建时间：2020/2/10
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FileUtils {

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if (CommonUtils.isNotEmptyString(filename)) {
            int dot = filename.lastIndexOf('.');
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }

    /**
     * 根据图片路径获取文件夹名称
     *
     * @param path 图片路径
     * @return 文件夹名称
     */
    public static String getFolderName(String path) {
        if (CommonUtils.isNotEmptyString(path)) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";
    }

    /**
     * 是否是图片文件
     */
    public static boolean isPicFile(String path) {
        path = path.toLowerCase();
        return path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".png");
    }

    /**
     * 检查图片是否存在。ContentResolver查询处理的数据有可能文件路径并不存在。
     *
     * @param filePath 文件路径
     * @return 该文件路径下文件是否存在
     */
    public static boolean checkImgExists(String filePath) {
        return new File(filePath).exists();
    }
}
