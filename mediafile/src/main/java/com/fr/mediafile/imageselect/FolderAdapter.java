package com.fr.mediafile.imageselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.R;
import com.fr.mediafile.bean.Image;
import com.fr.mediafile.utils.GlideUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 创建时间：2020/2/12
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private Context mContext;
    private int mSelectPosition = 0;
    HashMap<String, List<Image>> folders;
    List<String> folderNames;
    private SelectFolderListener listener;

    public FolderAdapter(Context context, SelectFolderListener listener) {
        this.mContext = context;
        this.listener = listener;
        folders = new HashMap<>();
        folderNames = new ArrayList<>();
    }

    public void setFolders(LinkedHashMap<String, List<Image>> folders) {
        this.folders = folders;
        for (HashMap.Entry<String, List<Image>> stringListEntry : folders.entrySet()) {
            String key;
            key = (String) ((HashMap.Entry) stringListEntry).getKey();
            folderNames.add(key);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FolderAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FolderAdapter.FolderViewHolder holder, int position) {
        final List<Image> images = folders.get(folderNames.get(position));
        if (images != null) {
            int num = images.size();
            final String name = folderNames.get(position);
            String imgPath = images.get(0).getPath();
            holder.tvName.setText(name);
            holder.tvNum.setText("(" + num + ")");
            holder.ivSelected.setVisibility(mSelectPosition == position ? View.VISIBLE : View.INVISIBLE);

            GlideUtils.load(imgPath, holder.ivFirst);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectPosition = holder.getAdapterPosition();
                    listener.selectFolder(name, images);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return folders != null ? folders.size() : 0;
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFirst;
        private ImageView ivSelected;
        private TextView tvName;
        private TextView tvNum;

        public FolderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFirst = itemView.findViewById(R.id.iv_fist);
            ivSelected = itemView.findViewById(R.id.iv_selected);
            tvName = itemView.findViewById(R.id.tv_name);
            tvNum = itemView.findViewById(R.id.tv_num);
        }
    }

    public interface SelectFolderListener {
        void selectFolder(String name, List<Image> images);
    }
}
