package com.fr.memroy.imagefolder.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fr.memroy.R;

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
    private List<String> imagePaths;

    public ImageViewpagerAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        imagePaths = new ArrayList<>();
    }

    public void setData(List<String> imagePaths) {
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public ImageViewpagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_viewpager_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewpagerAdapter.ViewHolder holder, int position) {
        if (imagePaths != null) {
            Glide.with(context)
                    .load(imagePaths.get(position))
                    .into(holder.imageView);
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return imagePaths != null ? imagePaths.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
