package com.fr.mediafile.imageselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fr.mediafile.R;
import com.fr.mediafile.bean.ImgFolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间：2020/2/12
 * 作者：范瑞
 * 博客：https://www.jianshu.com/u/408f3c1b46a9
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderViewHolder> {
    private Context mContext;
    private List<ImgFolder> folders;
    private int mSelectPosition = 0;
    private SelectFolderListener listener;

    public FolderAdapter(Context context, SelectFolderListener listener) {
        this.mContext = context;
        folders = new ArrayList<>();
        this.listener = listener;
    }

    public void setFolders(List<ImgFolder> folders) {
        this.folders = folders;
    }

    @NonNull
    @Override
    public FolderAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_folder, parent, false);
        return new FolderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FolderAdapter.FolderViewHolder holder, int position) {
        final ImgFolder folder = folders.get(position);
        int num = folder.getImages().size();
        String name = folder.getName();
        String imgPath = folder.getImages().get(0).getPath();
        holder.tvName.setText(name);
        holder.tvNum.setText("(" + num + ")");
        holder.ivSelected.setVisibility(mSelectPosition == position ? View.VISIBLE : View.INVISIBLE);
        Glide.with(mContext)
                .load(imgPath)
                .into(holder.ivFirst);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectPosition = holder.getAdapterPosition();
                listener.selectFolder(folder);
            }
        });
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
        void selectFolder(ImgFolder folder);
    }
}
