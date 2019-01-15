package com.base.library.views;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * com.lx.baselibrary.views
 * LxBaseLibrary
 * Created by lixiao2
 * 2019/1/2.
 */

public class SliderScrollLayout extends ViewGroup {

    private Scroller mScroller;

    private Context mContext;

    private int mTouchSlop;

    public SliderScrollLayout(Context context) {
        this(context, null);
    }

    public SliderScrollLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SliderScrollLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SliderScrollLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init();
    }

    private void init() {
        mScroller = new Scroller(mContext);
        ViewConfiguration configuration = ViewConfiguration.get(mContext);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int len = getChildCount();
        for (int m = 0; m < len; m++) {
            
        }
    }


    @Override
    public void computeScroll() {
        /** 实现内部平滑滚动 **/
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
