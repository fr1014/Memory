package com.fr.mediafile.imageselect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fr.mediafile.R;
import com.fr.mediafile.bean.Image;
import com.fr.mediafile.utils.CommonUtils;
import com.fr.mediafile.utils.FileManager;
import com.fr.mediafile.utils.ImageSelector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ImageSelectActivity extends AppCompatActivity implements View.OnClickListener, FileManager.DataCallBack, FolderAdapter.SelectFolderListener, ImageAdapter.SelectImageListener {
    private Context mContext;
    private ConstraintLayout mRootView;
    private Toolbar mToolbar;
    private TextView mTvFolder;     //文件夹名称
    private Button mDetermine;      //确定按钮
    private FolderAdapter mFolderAdapter;
    private RecyclerView mRvImage;
    private ImageAdapter mImageAdapter;
    private ImageSelectViewModel viewModel;
    private PopupWindow mPopupWindow;
    private int imageCount;        //选择图片的数量,小于等于0时不限选择的数量
    private List<Image> imagesSelected = new ArrayList<>();
    private List<Image> images;
    private LinkedHashMap<String,List<Image>> allFolders = new LinkedHashMap<>();

    /**
     * 启动图片选择器
     *
     * @param activity    用来启动图片选择器的activity
     * @param requestCode requestCode
     * @param maxCount    可选择图片的最大数量
     */
    public static void startActivity(Activity activity, int requestCode, int maxCount) {
        Intent intent = new Intent(activity, ImageSelectActivity.class);
        intent.putExtras(dataPackage(maxCount));
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 启动图片选择器
     *
     * @param fragment    用来启动图片选择器的fragment
     * @param requestCode requestCode
     * @param maxCount    可选择图片的最大数量
     */
    public static void startActivity(Fragment fragment, int requestCode, int maxCount) {
        Intent intent = new Intent(fragment.getActivity(), ImageSelectActivity.class);
        intent.putExtras(dataPackage(maxCount));
        fragment.startActivityForResult(intent, requestCode);
    }

    private static Bundle dataPackage(int maxCount) {
        Bundle bundle = new Bundle();
        bundle.putInt(ImageSelector.MAX_SELECT_COUNT, maxCount);
        return bundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_selecter);
        mRootView = findViewById(R.id.rootView);
        mRvImage = findViewById(R.id.rv_image);
        mToolbar = findViewById(R.id.toolbar);
        mTvFolder = findViewById(R.id.tv_folder);
        mDetermine = findViewById(R.id.bt_determine);
        mContext = this;

        initBundle();

        setStatusBarColor();

        initToolBar();

        initImageList();

        initVMLiveData();

        //扫描本地图片
        FileManager.loadImageFromSDCard(mContext, this);
    }

    private void initBundle() {
        Intent intent = getIntent();
        imageCount = intent.getIntExtra(ImageSelector.MAX_SELECT_COUNT, 0);
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //显示返回箭头
            getSupportActionBar().setDisplayShowTitleEnabled(false); //去除label
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        mTvFolder.setOnClickListener(this);
        mDetermine.setOnClickListener(this);
    }

    //展示文件夹列表 popupWindow + RecyclerView
    private void showPopupWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popupwindow_folder, null);
        RecyclerView rvFolder = view.findViewById(R.id.rv_folder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFolder.setLayoutManager(linearLayoutManager);
        //rv分割线
        CustomItemDecoration itemDecoration = new CustomItemDecoration();
        itemDecoration.setDividerColor(R.color.dividerColor);
        rvFolder.addItemDecoration(itemDecoration);

        rvFolder.setAdapter(mFolderAdapter);
        int height = mContext.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        mPopupWindow = new PopupWindow(mRootView, ViewGroup.LayoutParams.MATCH_PARENT, height * 4 / 5);
        mPopupWindow.setContentView(view);
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAsDropDown(mToolbar);
    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.rvFolder));
    }

    //初始化viewModel + LiveData观察数据
    private void initVMLiveData() {
        //创建ViewModel对象
        viewModel = new ViewModelProvider(this).get(ImageSelectViewModel.class);
        //创建用于更新UI的观察器
//        final Observer<List<ImgFolder>> folderObserver = new Observer<List<ImgFolder>>() {
//            @Override
//            public void onChanged(List<ImgFolder> folders) {
//
//                mFolderAdapter.setFolders(folders);
//                mFolderAdapter.notifyDataSetChanged();
//
//                mTvFolder.setVisibility(View.VISIBLE);
//                mTvFolder.setText(folders.get(0).getName());    //为TextView设置文件夹名称
//
//                mImageAdapter.setImages(folders.get(0).getImages());
//                mImageAdapter.notifyDataSetChanged();
//            }
//        };
        final Observer<LinkedHashMap<String, List<Image>>> imageObserver = new Observer<LinkedHashMap<String, List<Image>>>() {

            @Override
            public void onChanged(LinkedHashMap<String, List<Image>> stringListLinkedHashMap) {
                mFolderAdapter.setFolders(allFolders);
                mTvFolder.setVisibility(View.VISIBLE);
                mTvFolder.setText("全部图片");    //为TextView设置文件夹名称

                mImageAdapter.setImages(images);
            }
        };
        //观察LiveData，并以LifecycleOwner和观察者的身份传入此活动
//        viewModel.getFolders().observe(this, folderObserver);
        viewModel.getFolders().observe(this, imageObserver);
    }

    //扫描本地图片成功
//    @Override
//    public void onSuccess(ArrayList<ImgFolder> folders) {
//        viewModel.getFolders().postValue(folders);
//    }

    @Override
    public void onSuccess(List<Image> images, HashMap<String, List<Image>> folders) {
        this.images = images;
        Map<String,List<Image>> map = new HashMap<>();
        map.put("全部图片",images);
        allFolders.putAll(map);
        allFolders.putAll(folders);
        viewModel.getFolders().postValue(allFolders);
    }

    //扫描本地图片失败
    @Override
    public void onFile() {

    }

    //初始化图片列表
    private void initImageList() {
        //初始化folders的Adapter
        mFolderAdapter = new FolderAdapter(this, this);

        mRvImage.setLayoutManager(new GridLayoutManager(mContext, 4));
        mImageAdapter = new ImageAdapter(mContext, this);
        mImageAdapter.setCount(imageCount);
        mRvImage.setAdapter(mImageAdapter);
    }

    @Override
    public void selectFolder(String name, List<Image> images) {
        mPopupWindow.dismiss();
        mTvFolder.setText(name);
        mImageAdapter.setImages(images);
        mRvImage.scrollToPosition(0);       //RecyclerView回到起始位置
        mImageAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectImage(List<Image> imagesSelected) {
        this.imagesSelected = imagesSelected;
        int size = imagesSelected.size();
        if (imageCount != 0) {
            mDetermine.setText(String.format("确定(%d / %d)", size, imageCount));
        } else {
            mDetermine.setText(String.format("确定(%d)", size));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_folder) {
            showPopupWindow();
        } else if (id == R.id.bt_determine) {
            if (imagesSelected.size() == 0) {
                CommonUtils.ToastShort(this, "请选择图片");
            } else {
                setResult((ArrayList<Image>) imagesSelected);
                finish();
            }
        }
    }

    /**
     * 返回数据给启动此Activity的活动
     *
     * @param imagesSelected 已选择的图片
     */
    private void setResult(ArrayList<Image> imagesSelected) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(ImageSelector.IMAGE_SELECTED, imagesSelected);
        setResult(RESULT_OK, intent);
    }
}
