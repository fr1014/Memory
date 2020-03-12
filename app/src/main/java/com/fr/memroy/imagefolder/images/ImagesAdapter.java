package com.fr.memroy.imagefolder.images;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.memroy.R;
import com.fr.memroy.data.room.entity.ImageEntity;
import com.fr.memroy.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/3/11
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {
    private Context context;
    private List<ImageEntity> imageEntities;
    private LayoutInflater inflater;

    public ImagesAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        imageEntities = new ArrayList<>();
    }

    public void setImageEntities(List<ImageEntity> imageEntities) {
        this.imageEntities = imageEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_image_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageView imageView = holder.imageView;
        String path = imageEntities.get(holder.getAdapterPosition()).getImage().getPath();
        GlideUtils.load(path, imageView);
    }

    @Override
    public int getItemCount() {
        return imageEntities != null ? imageEntities.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
