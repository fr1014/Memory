package com.fr.memroy.imagefolder.listfolder.banner;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

/**
 * 创建时间:2020/2/17
 * 作者:fr
 * 邮箱:1546352238@qq.com
 */
public class Banner {
    private static final int NORMAL_COUNT = 2;

    private ViewPager2 viewPager2;
    private CompositePageTransformer compositePageTransformer;
    private int needPage =NORMAL_COUNT;

    public Banner(ViewPager2 viewPager2){
        this.viewPager2 = viewPager2;
        viewPager2.setPageTransformer(compositePageTransformer = new CompositePageTransformer());
    }

    /**
     * 设置一屏多页
     *
     * @param multiWidth 左右两边露出的宽度一致
     * @param pageMargin item与item之间的宽度
     */
    public Banner setPageMargin(int multiWidth,int pageMargin){
        return setPageMargin(multiWidth,multiWidth,pageMargin);
    }

    /**
     * 设置一屏多页
     *
     * @param tlWidth    左边页面显露出来的宽度
     * @param brWidth    右边页面露出来的宽度
     * @param pageMargin item与item之间的宽度
     */
    public Banner setPageMargin(int tlWidth, int brWidth, int pageMargin) {
        if (pageMargin != 0) {
            //通过ViewPager2的setPageTransformer方法来设置页面间距
            compositePageTransformer.addTransformer(new MarginPageTransformer(pageMargin));
        }
        if (tlWidth > 0 && brWidth > 0) {
            RecyclerView recyclerView = (RecyclerView) viewPager2.getChildAt(0);
            if (viewPager2.getOrientation() == ViewPager2.ORIENTATION_VERTICAL) {
                recyclerView.setPadding(0, tlWidth + Math.abs(pageMargin), 0, brWidth + Math.abs(pageMargin));
            } else {
                recyclerView.setPadding(tlWidth + Math.abs(pageMargin), 0, brWidth + Math.abs(pageMargin), 0);
            }
            recyclerView.setClipToPadding(false);
            setOffscreenPageLimit(1);
//            needPage += NORMAL_COUNT;
        }
        return this;
    }

    /**
     * @param transformer 页面间距的MarginPageTransformer
     */
    public Banner setPageTransformer(ViewPager2.PageTransformer transformer){
        compositePageTransformer.addTransformer(transformer);
        return this;
    }

    public Banner setOffscreenPageLimit(int limit) {
        viewPager2.setOffscreenPageLimit(limit);
        return this;
    }
}
