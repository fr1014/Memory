package com.fr.mediafile.imageselect.videos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.R;
import com.fr.mediafile.bean.Video;
import com.fr.mediafile.imageselect.images.FolderAdapter.FolderViewHolder;
import com.fr.mediafile.utils.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 创建时间:2020/3/19
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderViewHolder> {
    private Context context;
    private HashMap<String, List<Video>> videoFolders;
    private List<String> vidFolderNames;
    private int mSelectPosition = 0;

    public FolderAdapter(Context context) {
        this.context = context;
        videoFolders = new HashMap<>();
        vidFolderNames = new ArrayList<>();
    }

    public void setVideoFolders(HashMap<String, List<Video>> videoFolders) {
        this.videoFolders = videoFolders;
        for (HashMap.Entry<String, List<Video>> stringListEntry : videoFolders.entrySet()) {
            String key;
            key = (String) ((HashMap.Entry) stringListEntry).getKey();
            vidFolderNames.add(key);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FolderViewHolder holder, int position) {
        final String name = vidFolderNames.get(position);
        List<Video> videos = videoFolders.get(name);
        if (videos != null) {
            int num = videos.size();

            String thumbPath = videos.get(0).getThumbPath();
            holder.tvName.setText(name);
            holder.tvNum.setText("(" + num + ")");
            holder.ivSelected.setVisibility(mSelectPosition == position ? View.VISIBLE : View.INVISIBLE);

            GlideUtils.load(thumbPath, holder.ivFirst);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectPosition = holder.getAdapterPosition();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return vidFolderNames == null ? 0 : vidFolderNames.size();
    }

}
