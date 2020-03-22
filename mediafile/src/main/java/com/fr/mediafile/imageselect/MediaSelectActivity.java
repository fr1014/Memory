package com.fr.mediafile.imageselect;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.fr.mediafile.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MediaSelectActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tab;
    private String[] tabs = {"视频", "图片"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_select);

        viewPager2 = findViewById(R.id.pager);
        tab = findViewById(R.id.tab_layout);

        setStatusBarColor();

        tabViewPager2();
    }

    private void tabViewPager2() {
        CollectionAdapter adapter = new CollectionAdapter(this);
        adapter.setTabs(tabs);
        viewPager2.setAdapter(adapter);

        new TabLayoutMediator(tab, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();
    }

    /**
     * 修改状态栏颜色
     */
    private void setStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.rvFolder));
    }


}
