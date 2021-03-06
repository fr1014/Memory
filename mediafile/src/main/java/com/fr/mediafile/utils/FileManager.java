package com.fr.mediafile.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.fr.mediafile.bean.Image;
import com.fr.mediafile.bean.Video;
import com.fr.mediafile.imageselect.SelectViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static com.fr.mediafile.utils.FileUtils.checkImgExists;
import static com.fr.mediafile.utils.FileUtils.getExtensionName;

/**
 * 创建时间：2020/2/9
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FileManager {

    //从SD卡加载图片
    public static void loadImageFromSDCard(final Context context, final DataCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //扫描图片
                Uri imgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                List<Image> images = new ArrayList<>();
                HashMap<String, List<Image>> folders = new HashMap<>();//所有图片
                ContentResolver mContentResolver = context.getContentResolver();
                /*
                 * uri使用content：//方案的URI，用于检索的内容。
                 * projection:返回哪些列的列表。传递null将返回所有列
                 * selection:声明返回哪些行的过滤器，格式为* SQL WHERE子句（不包括WHERE本身）。传递null将*返回给定URI的所有行。
                 * selectionArgs:在选择中包括？s，然后将?替换为selectionArgs中的值。这些值将绑定为字符串。
                 * sortOrder如何对行进行排序，格式为SQL ORDER BY 子句（不包括ORDER BY本身）。传递null将使用默认排序顺序，该顺序可能是无序的。
                 */
                Cursor c = mContentResolver.query(imgUri,
                        new String[]{
                                MediaStore.Images.Media.DATA,
                                MediaStore.Images.Media.DISPLAY_NAME,
                                MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.Media._ID,
                                MediaStore.Images.Media.MIME_TYPE
                        },
                        null,
                        null,
                        MediaStore.Images.Media.DATE_ADDED);
                if (c != null) {
                    while (c.moveToNext()) {
                        //获取图片路径
                        String path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
                        //获取照片名称
                        String name = c.getString(c.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //获取照片时间
                        long time = c.getLong(c.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                        //获取图片类型
                        String mimeType = c.getString(c.getColumnIndex(MediaStore.Images.Media.MIME_TYPE));

                        String folderName = FileUtils.getFolderName(path);    //文件夹名称

                        //过滤未下载完成或者不存在的文件
                        if (!"downloading".equals(getExtensionName(path)) && checkImgExists(path)) {
                            //用于展示相册初始化界面
                            images.add(new Image(path, time, name, mimeType));

                            if (folders.containsKey(folderName)) {
                                List<Image> data = folders.get(folderName);
                                data.add(new Image(path, time, name, mimeType));
                            } else {
                                List<Image> data = new ArrayList<>();
                                data.add(new Image(path, time, name, mimeType));
                                folders.put(folderName, data);
                            }
                        }

                    }
                    c.close();
                }
                Collections.reverse(images);
                callBack.onSuccess(images, folders);
            }
        }).start();
    }

    /**
     * 获取手机中所有视频的信息
     */
    public static void loadAudioFromSDCard(final Context context, final SelectViewModel viewModel) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, List<Video>> folders = new HashMap<>();//所有照片
                Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                String[] proj = {MediaStore.Video.Thumbnails._ID
//                        , MediaStore.Video.Thumbnails.DATA
//                        , MediaStore.Video.Media.DURATION
//                        , MediaStore.Video.Media.SIZE
//                        , MediaStore.Video.Media.DISPLAY_NAME
//                        , MediaStore.Video.Media.DATE_MODIFIED};
                Cursor mCursor = context.getContentResolver().query(mImageUri,
                        null,
                        null,
                        null,
                        MediaStore.Video.Media.DATE_ADDED);
                if (mCursor != null) {
                    while (mCursor.moveToNext()) {
                        // 获取视频的路径
                        String path = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
                        if (!FileUtils.isExists(path)) {
                            continue;
                        }

                        int id = mCursor.getInt(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
                        String name = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                        String resolution = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.RESOLUTION)); //分辨率
                        long size = mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));// 大小
                        long duration = mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                        long date = mCursor.getLong(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED));//修改时间
                        if (size < 0) {
                            //某些设备获取size<0，直接计算
                            size = new File(path).length() / 1024;
                        }

                        //提前生成缩略图，再获取：http://stackoverflow.com/questions/27903264/how-to-get-the-video-thumbnail-path-and-not-the-bitmap
                        MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, null);
                        String[] projection = {MediaStore.Video.Thumbnails._ID, MediaStore.Video.Thumbnails.DATA};
                        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI
                                , projection
                                , MediaStore.Video.Thumbnails.VIDEO_ID + "=?"
                                , new String[]{id + ""}
                                , null);
                        String thumbPath = "";
                        while (cursor.moveToNext()) {
                            thumbPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
                        }
                        cursor.close();
                        // 获取该视频的父路径名
                        String dirPath = FileUtils.getFolderName(path);
                        //存储对应关系
                        if (folders.containsKey(dirPath)) {
                            List<Video> data = folders.get(dirPath);
                            data.add(new Video(id, path, name, resolution, size, date, duration, thumbPath));
                        } else {
                            List<Video> data = new ArrayList<>();
                            data.add(new Video(id, path, name, resolution, size, date, duration, thumbPath));
                            folders.put(dirPath, data);
                        }
                    }
                    mCursor.close();
                }
                viewModel.getVideoFolders().postValue(folders);
            }
        }).start();
    }

    private static final String TAG = "FileManager";

//    /**
//     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
//     *
//     * @param images 图片集合
//     * @return 将全部图片拆分到文件夹中
//     */
//    private static ArrayList<ImgFolder> splitFolder(ArrayList<Image> images) {
//        ArrayList<ImgFolder> folders = new ArrayList<>();
//        folders.add(new ImgFolder("全部图片", images));
//        if (images != null && !images.isEmpty()) {
//            for (int i = 0; i < images.size(); i++) {
//                String path = images.get(i).getPath();      //图片路径
//                String folderName = FileUtils.getFolderName(path);    //文件夹名称
//                if (CommonUtils.isNotEmptyString(folderName)) {
//                    ImgFolder folder = getImgFolder(folderName, folders);
//                    folder.addImage(images.get(i));
//                }
//            }
//        }
//        return folders;
//    }
//
//    /**
//     * 根据通过getFolderName(path)获取到的文件夹名称来创建文件夹
//     *
//     * @param folderName getFolderName(path)获取的文件夹名称
//     * @param folders    已经创建的文件夹
//     * @return 当前文件夹的名称
//     */
//    private static ImgFolder getImgFolder(String folderName, ArrayList<ImgFolder> folders) {
//        if (!folders.isEmpty()) {
//            for (int i = 0; i < folders.size(); i++) {
//                ImgFolder imgFolder = folders.get(i);
//                if (folderName.equals(imgFolder.getName())) {
//                    return imgFolder;
//                }
//            }
//        }
//        ImgFolder newFolder = new ImgFolder(folderName);
//        folders.add(newFolder);
//        return newFolder;
//    }

    public static Uri getImageContentUri(Context context, String path) {
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            // 如果图片不在手机的共享图片数据库，就先把它插入。
            if (new File(path).exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, path);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    public interface DataCallBack {
        //        void onSuccess(ArrayList<ImgFolder> folders);
        void onSuccess(List<Image> images, HashMap<String, List<Image>> folders);

        void onFile();
    }
}
