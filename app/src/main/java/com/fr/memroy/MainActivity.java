package com.fr.memroy;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.fr.MyApplication;
import com.fr.memroy.imagefolder.AddFolderActivity;
import com.fr.memroy.imagefolder.ImageFolderFragment;
import com.fr.memroy.utils.CommonUtils;
import com.fr.mypermission.Permission;
import com.fr.mypermission.PermissionListener;
import com.fr.mypermission.PermissionUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ImageFolderFragment.ImageFragmentListener {

    private CardView cardView;
    private TextView addIVFolder;
    private FragmentContainerView imageContainerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CommonUtils.setStatusBar(this, getColor(R.color.bg_home));

        cardView = findViewById(R.id.card_view);
        addIVFolder = findViewById(R.id.tv_add_ivfolder);
        imageContainerView = findViewById(R.id.fragment_image_folder);

        cardView.setOnClickListener(this);
        addIVFolder.setOnClickListener(this);

        initImageFragment();
    }

    public void initImageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ImageFolderFragment fragment = new ImageFolderFragment();
        fragment.setImageListener(this);
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_image_folder,fragment)
                .commit();
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
                                PermissionUtils.goSetting(MyApplication.getInstance());
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

    @Override
    public void notifyData() {
        cardView.setVisibility(View.GONE);
        addIVFolder.setVisibility(View.VISIBLE);
        imageContainerView.setVisibility(View.VISIBLE);
    }
}
