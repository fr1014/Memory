package com.fr.mediafile.imageselect;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fr.mediafile.R;
import com.fr.mediafile.bean.Video;
import com.fr.mediafile.utils.FileManager;

import java.util.HashMap;
import java.util.List;

public class MediaFragment extends Fragment {
    public static final String ARG_PARAM = "param";
    private SelectViewModel viewModel;

    private String mParam;

    public MediaFragment() {
        // Required empty public constructor
    }

    public static MediaFragment newInstance(String param) {
        MediaFragment fragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = this.getActivity();
        if (activity != null) {
            viewModel = new ViewModelProvider(activity).get(SelectViewModel.class);
            loadMedia(mParam, viewModel);
            initData();
        }

    }

    private static final String TAG = "MediaFragment";

    private void initData() {
        viewModel.getVideoFolders().observe(getViewLifecycleOwner(), new Observer<HashMap<String, List<Video>>>() {
            @Override
            public void onChanged(HashMap<String, List<Video>> folders) {
                for (HashMap.Entry<String, List<Video>> entry : folders.entrySet()) {
                    for (Video video : entry.getValue()) {
                        Log.d(TAG, "onChanged: " + video.toString());
                    }
                }
            }
        });
    }

    public void loadMedia(String type, SelectViewModel vm) {
        if ("视频".equals(type)) {
            FileManager.loadAudioFromSDCard(this.getContext(), vm);
        } else if ("图片".equals(type)) {
//            FileManager.loadImageFromSDCard();
        }
    }
}
