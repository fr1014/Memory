package com.fr.mediafile.imageselect;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fr.mediafile.bean.ImgFolder;

import java.util.List;

/**
 * 创建时间：2020/2/12
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class ImageSelectViewModel extends ViewModel {
    private MutableLiveData<List<ImgFolder>> folders;

    public MutableLiveData<List<ImgFolder>> getFolders(){
        if (folders == null){
            folders = new MutableLiveData<>();
        }
        return folders;
    }
}
