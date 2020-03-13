package com.fr.memroy.imagefolder.listfolder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.utils.CommonUtils;
import com.fr.memroy.R;
import com.fr.memroy.data.room.entity.ImageFolderEntity;
import com.fr.memroy.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/2/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ListFolderManageAdapter extends RecyclerView.Adapter<ListFolderManageAdapter.ViewHolder> {
    private Context context;
    private List<ImageFolderEntity> imageFolderEntities;
    private LayoutInflater inflater;
    private ListFolderListener listener;
    private Boolean visibly = false;
    private List<ImageFolderEntity> manageFolders;

    public ListFolderManageAdapter(Context context) {
        this.context = context;
        imageFolderEntities = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        manageFolders = new ArrayList<>();
    }

    public void setImageFolders(List<ImageFolderEntity> imageFolderEntities) {
        this.imageFolderEntities = imageFolderEntities;
    }

    public void setListener(ListFolderListener listener) {
        this.listener = listener;
    }

    public void setSelectVisible(Boolean visibly) {
        this.visibly = visibly;
        notifyDataSetChanged();
    }

    public boolean delete() {
        if (manageFolders.size()==0){
            CommonUtils.ToastShort(context,"请选择需要删除的文件夹");
            return false;
        }
        listener.delete(manageFolders);
        notifyDataSetChanged();
        return true;
    }

    @NonNull
    @Override
    public ListFolderManageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_manage_ivfolder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFolderManageAdapter.ViewHolder holder, int position) {
        ImageFolderEntity imageFolderEntity = imageFolderEntities.get(position);
        holder.textView.setText(imageFolderEntity.getName());

        GlideUtils.load(imageFolderEntity.getImagePath(),holder.imageView);

        if (visibly) {
            holder.selected.setVisibility(View.GONE);
            holder.unSelected.setVisibility(View.VISIBLE);
        } else {
            holder.selected.setVisibility(View.GONE);
            holder.unSelected.setVisibility(View.GONE);
        }

        holder.selected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageFolderEntity folderEntity = imageFolderEntities.get(holder.getAdapterPosition());
                if (manageFolders.contains(folderEntity)) {
                    manageFolders.remove(folderEntity);
                    holder.unSelected.setVisibility(View.VISIBLE);
                    holder.selected.setVisibility(View.GONE);
                }
            }
        });

        holder.unSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageFolderEntity folderEntity = imageFolderEntities.get(holder.getAdapterPosition());
                if (!manageFolders.contains(folderEntity)) {
                    manageFolders.add(folderEntity);
                    holder.unSelected.setVisibility(View.GONE);
                    holder.selected.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = imageFolderEntity.getId();
                String imgPath = imageFolderEntity.getImagePath();
                String name = imageFolderEntity.getName();
                String message = imageFolderEntity.getMessage();
                AddFolderActivity.startActivity((Activity) context,id,imgPath,name,message);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageFolderEntities != null ? imageFolderEntities.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private ImageView selected;
        private ImageView unSelected;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_cover);
            textView = itemView.findViewById(R.id.tv_folder);
            selected = itemView.findViewById(R.id.iv_selected);
            unSelected = itemView.findViewById(R.id.iv_unSelect);
        }
    }

    public interface ListFolderListener {
        void delete(List<ImageFolderEntity> imageFolderEntities);
    }
}
