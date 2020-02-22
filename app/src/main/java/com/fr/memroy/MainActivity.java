package com.fr.memroy;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.fr.MyApplication;
import com.fr.memroy.base.BaseActivity;
import com.fr.memroy.imagefolder.AddFolderActivity;
import com.fr.memroy.imagefolder.banner.ImageFolderFragment;
import com.fr.memroy.imagefolder.listfolder.ManageIvFolderActivity;
import com.fr.mypermission.Permission;
import com.fr.mypermission.PermissionListener;
import com.fr.mypermission.PermissionUtils;

public class MainActivity extends BaseActivity implements View.OnClickListener, ImageFolderFragment.ImageFragmentListener {

    private CardView cardView;
    private TextView addIVFolder;
    private TextView tvIVFolder;
    private FragmentContainerView imageContainerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        cardView = findViewById(R.id.add_image);
        addIVFolder = findViewById(R.id.tv_add_ivfolder);
        tvIVFolder = findViewById(R.id.tv_image);
        imageContainerView = findViewById(R.id.fragment_image_folder);

        cardView.setOnClickListener(this);
        addIVFolder.setOnClickListener(this);
        tvIVFolder.setOnClickListener(this);

        initImageFragment();
    }

    @Override
    protected void initData() {
        initPermission();
    }

    private void initPermission() {
        Permission.with(MainActivity.this)
                .requestCode(100)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .callBack(new PermissionListener() {
                    @Override
                    public void onPermit(int requestCode, String... permission) {
                        //已获得权限
                    }

                    @Override
                    public void onCancel(int requestCode, String... permission) {
                        PermissionUtils.goSetting(MyApplication.getInstance());
                    }
                }).send();
    }

    public void initImageFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        ImageFolderFragment fragment = new ImageFolderFragment();
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_image_folder,fragment)
                .commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_image:
            case R.id.tv_add_ivfolder:
                startActivity(MainActivity.this, AddFolderActivity.class);
                break;
            case R.id.tv_image:
                startActivity(MainActivity.this, ManageIvFolderActivity.class);
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
