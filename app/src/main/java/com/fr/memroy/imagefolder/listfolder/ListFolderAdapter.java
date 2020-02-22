package com.fr.memroy.imagefolder.listfolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fr.memroy.R;
import com.fr.memroy.data.room.entity.ImageFolderEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/2/22
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ListFolderAdapter extends RecyclerView.Adapter<ListFolderAdapter.ViewHolder> {
    private Context context;
    private List<ImageFolderEntity> imageFolderEntities;
    private LayoutInflater inflater;
    private ListFolderListener listener;

    public ListFolderAdapter(Context context) {
        this.context = context;
        imageFolderEntities = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setImageFolders(List<ImageFolderEntity> imageFolderEntities) {
        this.imageFolderEntities = imageFolderEntities;
    }

    public void setListener(ListFolderListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListFolderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_manage_ivfolder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFolderAdapter.ViewHolder holder, int position) {
        ImageFolderEntity imageFolderEntity = imageFolderEntities.get(position);
        holder.textView.setText(imageFolderEntity.getName());
        Glide
                .with(context)
                .load(imageFolderEntity.getImagePath())
                .into(holder.imageView);
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.delete(imageFolderEntities.get(holder.getAdapterPosition()));
                return true;
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

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_cover);
            textView = itemView.findViewById(R.id.tv_folder);
        }
    }

    public interface ListFolderListener{
        void delete(ImageFolderEntity imageFolderEntity);
    }
}
