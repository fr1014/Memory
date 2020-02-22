package com.fr.memroy.imagefolder.listfolder;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.imageselect.CustomItemDecoration;
import com.fr.memroy.R;
import com.fr.memroy.base.BaseActivity;
import com.fr.memroy.data.room.dao.ImageFolderDao;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.rx.RxSchedulers;
import com.fr.memroy.rx.SimpleConsumer;

import io.reactivex.Observable;

public class ManageIvFolderActivity extends BaseActivity implements ListFolderAdapter.ListFolderListener {
    private RecyclerView recyclerView;
    private ListFolderAdapter adapter;
    private ImageFolderDao imageFolderDao;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_iv_folder;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_folder);
        adapter = new ListFolderAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new CustomItemDecoration());
        adapter.setListener(this);
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
    public void delete(ImageFolderEntity imageFolderEntity) {
        Observable.just(imageFolderEntity)
                .compose(RxSchedulers.applyIO())
                .subscribe(new SimpleConsumer<ImageFolderEntity>() {
                    @Override
                    public void accept(ImageFolderEntity imageFolderEntity) {
                        imageFolderDao.deleteImageFolder(imageFolderEntity);
                    }
                });
    }
}
