package com.fr.memroy.imagefolder.images;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.bean.Image;
import com.fr.mediafile.imageselect.ImageSelectActivity;
import com.fr.mediafile.utils.ImageSelector;
import com.fr.memroy.R;
import com.fr.memroy.base.BaseVMActivity;
import com.fr.memroy.data.room.entity.ImageEntity;
import com.fr.memroy.utils.GlideUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ViewImageActivity extends BaseVMActivity<ImageViewModel> implements View.OnClickListener {
    private static final int REQUEST_CODE = 1;
    private int imageId;
    private String folderName;
    private String folderPath;
    private TextView textView;
    private ImageView imageView;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ImagesAdapter adapter;

    @Override
    protected void initBundle() {
        Bundle bundle = getIntent().getBundleExtra("folder");
        if (bundle != null) {
            imageId = bundle.getInt("image_id");
            folderName = bundle.getString("folder_name");
            folderPath = bundle.getString("folder_path");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_image;
    }

    @Override
    protected void initView() {
        textView = findViewById(R.id.folder_name);
        imageView = findViewById(R.id.iv_folder);
        textView.setText(folderName);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(this);
        GlideUtils.load(folderPath, imageView);

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view);
        adapter = new ImagesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    @Override
    protected ImageViewModel initViewModel() {
        return new ViewModelProvider(this).get(ImageViewModel.class);
    }

    private static final String TAG = "ViewImageActivity";

    @Override
    protected void initData(Bundle savedInstanceState) {
        viewModel.getAllImagesLive(imageId).observe(this, new Observer<List<ImageEntity>>() {
            @Override
            public void onChanged(List<ImageEntity> imageEntities) {
                for (ImageEntity imageEntity : imageEntities) {
                    Log.d(TAG, "onChanged: " + imageEntity.toString());
                    adapter.setImageEntities(imageEntities);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatingActionButton:
                ImageSelectActivity.startActivity(this, REQUEST_CODE, 10);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<Image> images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                    ImageEntity[] imageEntities = new ImageEntity[images.size()];
                    for (int index = 0; index < images.size(); index++) {
                        imageEntities[index] = new ImageEntity(imageId, images.get(index));
                    }
                    viewModel.insert(imageEntities);
                }
                break;
        }
    }
}
