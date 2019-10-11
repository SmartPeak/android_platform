package com.pos.sdkdemo.base;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import com.basewin.log.LogUtil;
import com.basewin.widgets.IndicatorView;
import com.pos.sdkdemo.R;

import java.util.ArrayList;

/**
 * author liudy
 */
public abstract class BaseActivityGuide extends Activity implements OnPageChangeListener {

    protected int[] mImgBgRes = null;

    private ViewPager mPager;

    private boolean mIsFirst;

    private Editor mEdit;

    private IndicatorView mIndicator;

    private View mLayout;

    private ArrayList<View> mAllLayout;

    private int selectPage = 0;

    private int oldScrollState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        PrepareImgs();
        mAllLayout = new ArrayList<View>();
        for (int i = 0; i < mImgBgRes.length; i++) {
            mLayout = getLayoutInflater().inflate(R.layout.activity_guidepage, null);
            mAllLayout.add(mLayout);
        }
        mIndicator = (IndicatorView) findViewById(R.id.indicatorView1);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setOnPageChangeListener(this);
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public Object instantiateItem(View container, int position) {
               LogUtil.i(getClass(), "instantiateItem " + position);
                View layout = mAllLayout.get(position);
                layout.setBackgroundResource(mImgBgRes[position]);
                mPager.addView(layout);
                return layout;
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                LogUtil.i(getClass(), "destroyItem " + position);
                View layout = mAllLayout.get(position);
                mPager.removeView(layout);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                LogUtil.i(getClass(),  "isViewFromObject ");
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                LogUtil.i(getClass(),  "getCount ");
                return mAllLayout.size();
            }
        });
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }
    abstract protected void PrepareImgs();
    abstract protected void onFinishGuide();
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub
        LogUtil.i(getClass(),  "onPageScrollStateChanged " + arg0);
        if (oldScrollState == 1 && arg0 == 0) {
            if (selectPage == (mImgBgRes.length-1)) {
                onFinishGuide();
            }
        }
        else
        {
            oldScrollState = arg0;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // TODO Auto-generated method stub
        LogUtil.i(getClass(), "onPageScrolled " + position + " " + positionOffset);
        mIndicator.refreshIndictor(position, positionOffset, mImgBgRes.length);
    }

    @Override
    public void onPageSelected(int arg0) {
        LogUtil.i(getClass(),  "onPageSelected " + arg0);
        selectPage = arg0;
        // TODO Auto-generated method stub
    }
}
