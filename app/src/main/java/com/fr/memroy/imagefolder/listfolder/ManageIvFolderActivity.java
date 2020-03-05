package com.fr.memroy.imagefolder.listfolder;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.imageselect.CustomItemDecoration;
import com.fr.memroy.R;
import com.fr.memroy.base.BaseVMActivity;
import com.fr.memroy.data.room.entity.ImageFolderEntity;

import java.util.List;

public class ManageIvFolderActivity extends BaseVMActivity<ImageFolderViewModel> implements ListFolderAdapter.ListFolderListener, View.OnClickListener {
    private ListFolderAdapter adapter;
    private ConstraintLayout manageLayout;
    private TextView tvManage;
    private TextView tvCancel;
    private TextView tvDelete;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_iv_folder;
    }

    @Override
    protected void initView() {
        initToolbar(findViewById(R.id.toolbar));
        initManageView();
        initRecyclerView();
    }

    private void initManageView() {
        tvManage = findViewById(R.id.tv_manage);
        manageLayout = findViewById(R.id.cl_manage);
        tvDelete = findViewById(R.id.tv_delete);
        tvCancel = findViewById(R.id.tv_cancel);
        tvDelete.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
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
    public ImageFolderViewModel initViewModel() {
        return new ViewModelProvider(this).get(ImageFolderViewModel.class);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        viewModel.getAllImageFoldersLive()
                .observe(this, imageFolderEntities -> {
                    adapter.setImageFolders(imageFolderEntities);
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void delete(List<ImageFolderEntity> imageFolderEntities) {
        viewModel.delete(imageFolderEntities);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_manage:
                adapter.setSelectVisible(true);
                tvManage.setVisibility(View.INVISIBLE);
                tvCancel.setVisibility(View.VISIBLE);
                manageLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_cancel:
                adapter.setSelectVisible(false);
                tvManage.setVisibility(View.VISIBLE);
                tvCancel.setVisibility(View.INVISIBLE);
                manageLayout.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_delete:
                if (adapter.delete()) {
                    tvManage.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.INVISIBLE);
                    adapter.setSelectVisible(false);
                    manageLayout.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }
}
