package com.fr.memroy.imagefolder;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.fr.MyApplication;
import com.fr.memroy.R;
import com.fr.memroy.data.room.AppDataBase;
import com.fr.memroy.data.room.dao.ImageFolderDao;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.imagefolder.banner.Banner;
import com.fr.memroy.imagefolder.banner.ImageViewpagerAdapter;
import com.fr.memroy.imagefolder.banner.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

public class ImageFolderFragment extends Fragment {
    private ViewPager2 viewPager2;
    private ImageViewpagerAdapter adapter;
    private LiveData<List<ImageFolderEntity>> imageFolderLive;
    private AppDataBase appDataBase;
    private ImageFolderDao imageFolderDao;
    private ImageFragmentListener listener;

    public ImageFolderFragment() {
        // Required empty public constructor
    }

    public void setImageListener(ImageFragmentListener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewpager_image, container, false);
        viewPager2 = view.findViewById(R.id.viewpager2);

        appDataBase = MyApplication.getInstance().getDataBase();
        imageFolderDao = appDataBase.getImageFolderDao();
        imageFolderLive = imageFolderDao.getAllImageFoldersLive();

        adapter = new ImageViewpagerAdapter(this.getContext());
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter);
        new Banner(viewPager2)
                .setPageMargin(30, 25)
                .setPageTransformer(new ScaleInTransformer());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageFolderLive.observe(getViewLifecycleOwner(), new Observer<List<ImageFolderEntity>>() {
            @Override
            public void onChanged(List<ImageFolderEntity> imageFolderEntities) {

                if (imageFolderEntities.size() != 0) {
                    listener.notifyData();
                    viewPager2.setVisibility(View.VISIBLE);
                    List<String> imagePaths = new ArrayList<>();
                    for (ImageFolderEntity entity : imageFolderEntities) {
                        imagePaths.add(entity.getImagePath());
                    }
                    adapter.setData(imagePaths);
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public interface ImageFragmentListener {
        void notifyData();
    }
}
