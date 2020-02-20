package com.fr.memroy.imagefolder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fr.mediafile.bean.Image;
import com.fr.mediafile.imageselect.ImageSelectActivity;
import com.fr.mediafile.utils.CommonUtils;
import com.fr.mediafile.utils.ImageSelector;
import com.fr.memroy.R;
import com.fr.memroy.data.room.AppDataBase;
import com.fr.memroy.data.room.dao.ImageFolderDao;
import com.fr.memroy.data.room.entity.ImageFolderEntity;

import java.util.List;

public class AddFolderActivity extends AppCompatActivity implements View.OnClickListener {
    private AppDataBase appDataBase;
    private ImageFolderDao imageFolderDao;
    private ImageView ivAddCover;
    private EditText etName;
    private EditText etMessage;
    private Button btCertain;
    private static final int REQUEST_CODE = 2;
    private String imagePath;
    private String name;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);
        ivAddCover = findViewById(R.id.add_cover);
        etName = findViewById(R.id.et_name);
        etMessage = findViewById(R.id.et_message);
        btCertain = findViewById(R.id.bt_certain);
        ivAddCover.setOnClickListener(this);
        btCertain.setOnClickListener(this);

        appDataBase = AppDataBase.getInstance(this);
        imageFolderDao = appDataBase.getImageFolderDao();
    }

    private static final String TAG = "AddFolderActivity";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_cover:
                ImageSelectActivity.startActivity(AddFolderActivity.this, REQUEST_CODE, 1);
                break;
            case R.id.bt_certain:
                if (initImageFolder()) {
                    Log.d(TAG, "onClick: " + name + imagePath);
                    ImageFolderEntity imageFolderEntity = new ImageFolderEntity(name, imagePath, " ");
                    imageFolderDao.insertImageFolder(imageFolderEntity);
                    finish();
                }
                break;
        }
    }

    private boolean initImageFolder() {
        name = etName.getText().toString();
        message = etMessage.getText().toString();
        if (imagePath != null && !name.isEmpty()) {
            ImageFolderEntity entity = new ImageFolderEntity(name, imagePath, message);
            return true;
        }
        CommonUtils.ToastShort(this, "请补全信息再确定");
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
