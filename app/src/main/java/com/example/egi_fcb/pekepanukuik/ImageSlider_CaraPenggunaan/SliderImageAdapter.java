package com.example.egi_fcb.pekepanukuik.ImageSlider_CaraPenggunaan;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.egi_fcb.pekepanukuik.R;

/**
 * Created by Egi FCB on 16/10/2016.
 */

public class SliderImageAdapter extends PagerAdapter {

    Context mContext;

    SliderImageAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return sliderImagesId.length;
    }

    private int[] sliderImagesId = new int[]{
            R.drawable.carapenggunaan1, R.drawable.carapenggunaan2, R.drawable.carapenggunaan3,
            R.drawable.carapenggunaan4, R.drawable.carapenggunaan5, R.drawable.carapenggunaan6,
            R.drawable.carapenggunaan7, R.drawable.carapenggunaan8 };

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == ((ImageView) obj);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int i) {
        ImageView mImageView = new ImageView(mContext);
        mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mImageView.setImageResource(sliderImagesId[i]);
        ((ViewPager) container).addView(mImageView, 0);
        return mImageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int i, Object obj) {
        ((ViewPager) container).removeView((ImageView) obj);
    }
}
