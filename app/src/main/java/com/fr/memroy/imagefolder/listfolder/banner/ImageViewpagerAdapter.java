package com.fr.memroy.imagefolder.listfolder.banner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.memroy.R;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.imagefolder.images.ViewImageActivity;
import com.fr.memroy.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/2/17
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ImageViewpagerAdapter extends RecyclerView.Adapter<ImageViewpagerAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater mInflater;
    private List<ImageFolderEntity> folderEntities;

    public ImageViewpagerAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        folderEntities = new ArrayList<>();
    }

    public void setData(List<ImageFolderEntity> folderEntities) {
        this.folderEntities = folderEntities;
    }

    @NonNull
    @Override
    public ImageViewpagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_viewpager_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewpagerAdapter.ViewHolder holder, int position) {
        if (folderEntities != null) {
            GlideUtils.load(folderEntities.get(position).getImagePath(),holder.imageView);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageFolderEntity entity = folderEntities.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, ViewImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("image_id", entity.getId());
                bundle.putString("folder_path", entity.getImagePath());
                bundle.putString("folder_name", entity.getName());
                intent.putExtra("folder", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderEntities != null ? folderEntities.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
