package com.fr.mediafile.imageselect.videos;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.R;
import com.fr.mediafile.bean.Video;
import com.fr.mediafile.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间:2020/3/21
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private List<Video> videos;

    public VideoAdapter(Context context) {
        this.context = context;
        videos = new ArrayList<>();
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }

    private static final String TAG = "VideoAdapter";

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideoViewHolder holder, int position) {
        if (videos != null) {
            Log.d(TAG, "onBindViewHolder: " + videos.size());
            String path = videos.get(position).getPath();
            GlideUtils.loadThumb(path,holder.imageView);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Video video = videos.get(holder.getAdapterPosition());
//                if (imagesSelected.contains(image)) {  //点击已添加的图片，取消选择
//                    imagesSelected.remove(image);
//                    countSelected--;
//                    holder.unSelect.setVisibility(View.VISIBLE);
//                    holder.selected.setVisibility(View.INVISIBLE);
//                    listener.selectImage(imagesSelected);
//                } else if (count == 0) {
//                    holder.unSelect.setVisibility(View.INVISIBLE);
//                    holder.selected.setVisibility(View.VISIBLE);
//                    imagesSelected.add(image);
//                    listener.selectImage(imagesSelected);
//                } else if (countSelected != count) {       //可以添加图片
//                    holder.unSelect.setVisibility(View.INVISIBLE);
//                    holder.selected.setVisibility(View.VISIBLE);
//                    imagesSelected.add(image);
//                    countSelected++;
//                    listener.selectImage(imagesSelected);
//                } else {
//                    CommonUtils.ToastShort(context, "只能选择" + count + "张图片");
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos == null ? 0 : videos.size();
    }

    static class VideoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageView unSelect;
        ImageView selected;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            unSelect = itemView.findViewById(R.id.iv_unSelect);
            selected = itemView.findViewById(R.id.iv_selected);
        }
    }
}
