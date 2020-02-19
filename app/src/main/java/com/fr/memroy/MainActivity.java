package com.fr.memroy;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.fr.memroy.imagefolder.AddFolderActivity;
import com.fr.memroy.imagefolder.banner.Banner;
import com.fr.memroy.imagefolder.banner.ImageViewpagerAdapter;
import com.fr.memroy.imagefolder.banner.ScaleInTransformer;
import com.fr.memroy.room.AppDataBase;
import com.fr.memroy.room.dao.ImageFolderDao;
import com.fr.memroy.room.entity.ImageFolderEntity;
import com.fr.memroy.utils.CommonUtils;
import com.fr.mypermission.Permission;
import com.fr.mypermission.PermissionListener;
import com.fr.mypermission.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView cardView;
    private Context mContext;
    private LiveData<List<ImageFolderEntity>> imageFolderLive;
    private AppDataBase appDataBase;
    private ImageFolderDao imageFolderDao;
    private ViewPager2 viewPager2;
    private ImageViewpagerAdapter adapter;
    private TextView addIVFolder;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonUtils.setStatusBar(this, getColor(R.color.bg_home));

        cardView = findViewById(R.id.card_view);
        viewPager2 = findViewById(R.id.viewpager2);
        addIVFolder = findViewById(R.id.tv_add_ivfolder);
        mContext = this;

        appDataBase = AppDataBase.getInstance(this);
        imageFolderDao = appDataBase.getImageFolderDao();
        imageFolderLive = imageFolderDao.getAllImageFoldersLive();

        adapter = new ImageViewpagerAdapter(mContext);
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager2.setAdapter(adapter);
        new Banner(viewPager2)
                .setPageMargin(30, 25)
                .setPageTransformer(new ScaleInTransformer());

        cardView.setOnClickListener(this);
        addIVFolder.setOnClickListener(this);

        imageFolderLive.observe(this, new Observer<List<ImageFolderEntity>>() {
            @Override
            public void onChanged(List<ImageFolderEntity> imageFolderEntities) {
                if (imageFolderEntities.size() != 0) {
                    cardView.setVisibility(View.GONE);
                    addIVFolder.setVisibility(View.VISIBLE);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view:
                Permission.with(MainActivity.this)
                        .requestCode(100)
                        .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .callBack(new PermissionListener() {
                            @Override
                            public void onPermit(int requestCode, String... permission) {
                                //已获得权限
                                Intent intent = new Intent(MainActivity.this, AddFolderActivity.class);
                                startActivity(intent);
                            }

                            @Override
                            public void onCancel(int requestCode, String... permission) {
                                PermissionUtils.goSetting(mContext);
                            }
                        }).send();
                break;
            case R.id.tv_add_ivfolder:
                //已获得权限
                Intent intent = new Intent(MainActivity.this, AddFolderActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permission.onRequestPermissionResult(requestCode, permissions, grantResults);
    }
}
