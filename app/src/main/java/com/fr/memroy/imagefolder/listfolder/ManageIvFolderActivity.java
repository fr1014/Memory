package com.fr.memroy.imagefolder.listfolder;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.imageselect.CustomItemDecoration;
import com.fr.memroy.R;
import com.fr.memroy.base.BaseActivity;
import com.fr.memroy.data.room.dao.ImageFolderDao;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.rx.RxSchedulers;
import com.fr.memroy.rx.SimpleConsumer;

import java.util.List;

import io.reactivex.Observable;

public class ManageIvFolderActivity extends BaseActivity implements ListFolderAdapter.ListFolderListener, View.OnClickListener {
    private ListFolderAdapter adapter;
    private ImageFolderDao imageFolderDao;
    private ConstraintLayout manageLayout;
    private TextView tvManage;
    private TextView tvUpdate;
    private TextView tvDelete;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_iv_folder;
    }

    @Override
    protected void initView() {
        initToolbar();
        initRecyclerView();
        initManageView();
    }

    private void initManageView() {
        manageLayout = findViewById(R.id.cl_manage);
        tvUpdate = findViewById(R.id.tv_update);
        tvDelete = findViewById(R.id.tv_delete);
        tvUpdate.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        tvManage = findViewById(R.id.tv_manage);

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

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.rv_folder);
        adapter = new ListFolderAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CustomItemDecoration());
        adapter.setListener(this);

        tvManage.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        imageFolderDao = dataBase.getImageFolderDao();

        imageFolderDao
                .getAllImageFoldersLive()
                .observe(this, imageFolderEntities -> {
                    adapter.setImageFolders(imageFolderEntities);
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void delete(List<ImageFolderEntity> imageFolderEntities) {
        Observable.just(imageFolderEntities)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<List<ImageFolderEntity>>() {
                    @Override
                    public void accept(List<ImageFolderEntity> imageFolderEntities) {
                        for (ImageFolderEntity imageFolderEntity : imageFolderEntities) {
                            imageFolderDao.deleteImageFolder(imageFolderEntity);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_manage:
                adapter.setSelectVisible(true);
                manageLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_update:
                break;
            case R.id.tv_delete:
                if (adapter.delete()) {
                    adapter.setSelectVisible(false);
                    manageLayout.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}
