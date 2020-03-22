package com.fr.mediafile.imageselect.activity;


import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.R;
import com.fr.mediafile.bean.Video;
import com.fr.mediafile.imageselect.SelectViewModel;
import com.fr.mediafile.imageselect.videos.VideoAdapter;
import com.fr.mediafile.utils.FileManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoSelectActivity extends BaseActivity<SelectViewModel> {
    private Toolbar mToolbar;
    private RecyclerView rvVideo;
    private VideoAdapter adapter;
    private List<Video> videos = new ArrayList<>(); //全部视频


    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_select;
    }

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        rvVideo = findViewById(R.id.rv_video);
        initToolBar(mToolbar);
        adapter = new VideoAdapter(this);
        rvVideo.setLayoutManager(new GridLayoutManager(this, 4));
        rvVideo.setAdapter(adapter);
    }

    @Override
    protected void initToolBar(Toolbar toolbar) {
        super.initToolBar(toolbar);
    }

    @Override
    protected void initData() {
        initVM();
        FileManager.loadAudioFromSDCard(this, viewModel);
        initVMLiveData();
    }

    private static final String TAG = "VideoSelectActivity";

    private void initVMLiveData() {
        viewModel.getVideoFolders().observe(this, new Observer<HashMap<String, List<Video>>>() {
            @Override
            public void onChanged(HashMap<String, List<Video>> folders) {
                for (HashMap.Entry<String, List<Video>> listEntry : folders.entrySet()) {
//                    Log.d(TAG, "onChanged: " + listEntry.getValue().toString());
                    videos.addAll(listEntry.getValue());
                }
                adapter.setVideos(videos);
            }
        });
    }

    private void initVM() {
        viewModel = new ViewModelProvider(this).get(SelectViewModel.class);
    }
}
