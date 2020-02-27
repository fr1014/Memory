package com.fr.memroy.imagefolder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.fr.mediafile.bean.Image;
import com.fr.mediafile.imageselect.ImageSelectActivity;
import com.fr.mediafile.utils.ImageSelector;
import com.fr.memroy.Constants;
import com.fr.memroy.R;
import com.fr.memroy.base.BaseActivity;
import com.fr.memroy.data.room.dao.ImageFolderDao;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.rx.RxSchedulers;
import com.fr.memroy.rx.SimpleConsumer;
import com.fr.memroy.utils.CommonUtils;

import java.util.List;

import io.reactivex.Observable;

public class AddFolderActivity extends BaseActivity implements View.OnClickListener {
    private ImageFolderDao imageFolderDao;
    private Toolbar toolbar;
    private ImageView ivAddCover;
    private EditText etName;
    private EditText etMessage;
    private Button btCertain;
    private static final int REQUEST_CODE = 2;
    private String imagePath;
    private String name;
    private String message;
    private Boolean isUpdate = false;  //true 为修改数据 false为插入数据
    private int updateId;

    public static void startActivity(Activity activity, int id, String imagePath, String name, String message) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.IMAGE_ID, id);
        bundle.putString(Constants.IMAGE_PATH, imagePath);
        bundle.putString(Constants.IMAGE_NAME, name);
        bundle.putString(Constants.IMAGE_MESSAGE, message);
        Intent intent = new Intent(activity, AddFolderActivity.class);
        intent.putExtra(Constants.IMAGE_BUNDLE, bundle);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_folder;
    }

    @Override
    protected void initView() {
        CommonUtils.setStatusBar(this, getColor(R.color.bg_black));
        toolbar = findViewById(R.id.toolbar);
        ivAddCover = findViewById(R.id.add_cover);
        etName = findViewById(R.id.et_name);
        etMessage = findViewById(R.id.et_message);
        btCertain = findViewById(R.id.bt_certain);
        ivAddCover.setOnClickListener(this);
        btCertain.setOnClickListener(this);

        initToolbar();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        imageFolderDao = dataBase.getImageFolderDao();
        initUpdateData();
    }

    private void initUpdateData() {
        Bundle bundle = getIntent().getBundleExtra(Constants.IMAGE_BUNDLE);
        if (bundle != null) {
            isUpdate = true;
            updateId = bundle.getInt(Constants.IMAGE_ID);
            imagePath = bundle.getString(Constants.IMAGE_PATH);
            name = bundle.getString(Constants.IMAGE_NAME);
            message = bundle.getString(Constants.IMAGE_MESSAGE);
            etName.setText(name);
            etMessage.setText(message);
            Glide.with(this)
                    .load(imagePath)
                    .into(ivAddCover);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_cover:
                ImageSelectActivity.startActivity(AddFolderActivity.this, REQUEST_CODE, 1);
                break;
            case R.id.bt_certain:
                if (initImageFolder()) {
                    ImageFolderEntity imageFolderEntity = new ImageFolderEntity(name, imagePath, message);
                    if (!isUpdate) {

                        Observable.just(imageFolderEntity)
                                .compose(RxSchedulers.applyIO())
                                .subscribe(new SimpleConsumer<ImageFolderEntity>() {
                                    @Override
                                    public void accept(ImageFolderEntity imageFolderEntity) {
                                        imageFolderDao.insertImageFolder(imageFolderEntity);
                                    }
                                });
                    } else {
                        imageFolderEntity.setId(updateId);
                        Observable.just(imageFolderEntity)
                                .compose(RxSchedulers.applyIO())
                                .subscribe(new SimpleConsumer<ImageFolderEntity>() {
                                    @Override
                                    public void accept(ImageFolderEntity imageFolderEntity) {
                                        imageFolderDao.updateImageFolder(imageFolderEntity);
                                    }
                                });
                    }
                    finish();
                }
                break;
        }
    }

    private boolean initImageFolder() {
        name = etName.getText().toString();
        message = etMessage.getText().toString();
        if (imagePath != null && !name.isEmpty()) {
            return true;
        }
        Toast.makeText(this, "请补全信息再确定", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE && data != null) {
                List<Image> images = data.getParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED);
                if (images != null) {
                    imagePath = images.get(0).getPath();
                    Glide.with(this)
                            .load(imagePath)
                            .into(ivAddCover);
                }
            }
        }
    }
}
