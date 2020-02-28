package com.fr.memroy.imagefolder.banner;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.viewpager2.widget.ViewPager2;

import com.fr.memroy.R;
import com.fr.memroy.base.BaseVMFragment;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.imagefolder.listfolder.ImageFolderViewModel;

import java.util.ArrayList;
import java.util.List;

public class ImageFolderFragment extends BaseVMFragment {
    private ViewPager2 viewPager2;
    private ImageViewpagerAdapter adapter;
    private ImageFragmentListener listener;
    private ImageFolderViewModel imageFolderViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_viewpager_image;
    }

    @Override
    protected void initView(View view) {
        viewPager2 = view.findViewById(R.id.viewpager2);
        adapter = new ImageViewpagerAdapter(this.getContext());
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter);
        new Banner(viewPager2)
                .setPageMargin(30, 25)
                .setPageTransformer(new ScaleInTransformer());
    }

    @Override
    protected void initData() {
        Activity activity = this.getActivity();
        if (activity!=null){
            imageFolderViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ImageFolderViewModel.class);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageFolderViewModel.getAllImageFoldersLive().observe(getViewLifecycleOwner(), imageFolderEntities -> {

            if (imageFolderEntities.size() != 0) {
                listener.notifyData();
                viewPager2.setVisibility(View.VISIBLE);
                List<String> imagePaths = new ArrayList<>();
                for (ImageFolderEntity entity : imageFolderEntities) {
                    imagePaths.add(entity.getImagePath());
                }
                adapter.setData(imagePaths);
                adapter.notifyDataSetChanged();
            }else {
                viewPager2.setVisibility(View.GONE);
                listener.notifyNoData();
            }
        });
    }

    public interface ImageFragmentListener {
        void notifyData();
        void notifyNoData();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ImageFragmentListener){
            listener = (ImageFragmentListener) context;
        }else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
