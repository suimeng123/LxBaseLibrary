package com.lx.baselibrary.views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

import com.lx.baselibrary.R;
import com.lx.baselibrary.utils.PhoneUtil;

/**
 * com.lx.baselibrary.views
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/21.
 */

public class ReflushLayout extends ViewGroup {
    private static final String TAG = "ReflushLayout";

    private View header, footer;

    /**
     * 手机屏幕宽度
     **/
    private int phoneWidth;

    /**
     * 手机屏幕高度
     **/
    private int phoneHeigth;

    /**
     * 头尾布局的高度dp
     **/
    private int flushHeight = 50;
    private int headerHeight;
    private int footerHeight;

    private Scroller mScroller;

    /**
     * 触发滑动的最小滑动距离
     **/
    private int mTouchSlop;

    private Context mContext;

    /**
     * 上下边距
     **/
    private int topBorder = 0;
    private int bottomBorder = 0;

    /**
     * 手指按下的Y轴坐标
     **/
    private int downY;

    private int lastY;

    //最大速度
    private int mMaxVelocity;
    // 速度计算器
    private VelocityTracker mVelocityTracker;

    private int mPointerId;

    public ReflushLayout(Context context) {
        this(context, null);
    }

    public ReflushLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflushLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ReflushLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

        header = LayoutInflater.from(context).inflate(R.layout.layout_header, null);
        footer = LayoutInflater.from(context).inflate(R.layout.layout_footer, null);

        headerHeight = footerHeight = PhoneUtil.dp2px((Activity) context, flushHeight);

        header.measure(MeasureSpec.makeMeasureSpec(1, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(headerHeight, MeasureSpec.EXACTLY));
        footer.measure(MeasureSpec.makeMeasureSpec(1, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(footerHeight, MeasureSpec.EXACTLY));


        // 滚动速度计算器
        mMaxVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();

        addView(header, 0);
        scrollTo(0, headerHeight);
        lastY = getScrollY();

        this.post(new Runnable() {
            @Override
            public void run() {
                addView(footer);
            }
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        phoneWidth = getMeasuredWidth();
        phoneHeigth = getMeasuredHeight();

        Log.i(TAG, "phoneWidth: " + phoneWidth + "; phoneHeigth: " + phoneHeigth + "; mesureWidth: " + getMeasuredWidth() + "; mesureHeight: " + getMeasuredHeight());
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int len = getChildCount();
        int top = 0;
        for (int m = 0; m < len; m++) {
            View child = getChildAt(m);
            child.layout(0, top, PhoneUtil.getDisplayMetrics((Activity) mContext).widthPixels, top + child.getMeasuredHeight());
            top += child.getMeasuredHeight();
        }
        bottomBorder = top;

        Log.i(TAG, "bottomBorder: " + bottomBorder);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) event.getY();
                lastY = getScrollY();
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mPointerId = event.getPointerId(0);
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = (int) Math.abs(downY - event.getY());
                if (dy > mTouchSlop) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
        return false;
    }
    private int type = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        obtainVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:

                if ((event.getY() - downY - lastY > -headerHeight && type != 1) || (type == 1 && ((int) (event.getY() - downY) / 3 - lastY) > -headerHeight)) {
                    // 如果是第一次正常状态或者处于阻力状态 时滑动的距离在阻力范围内
                    if (type == 0) {
                        // 如果之前是正常状态 需要将滚动的距离转化为阻力状态的距离
                        downY = (int) event.getY();
                        lastY = getScrollY();
                    }
                    scrollTo(0, -(int) (event.getY() - downY) / 3 + lastY);
                    type = 1;
                } else {
                    if (type == 1) {
                        // 如果之前是阻力状态，需要将滚动的距离转化为正常状态的距离
                        downY = (int) event.getY();
                        lastY = getScrollY();
                    }
                    scrollTo(0,  - (int) (event.getY() - downY - lastY));
                    type = 0;
                }

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (-getScrollY() <= -headerHeight / 2 && -getScrollY() > -headerHeight || (getScrollY() > 0 && bottomBorder <= phoneHeigth)) {
//                    scrollTo(0, headerHeight);
                    mScroller.startScroll(0, getScrollY(), 0,headerHeight - getScrollY(),200);
//                    lastY = headerHeight;
                } else if (-getScrollY() > -headerHeight / 2) {
//                    scrollTo(0, 0);
                    mScroller.startScroll(0, getScrollY(), 0,- getScrollY(),200);
//                    lastY = 0;
                } else if (bottomBorder > phoneHeigth && getScrollY() >= (bottomBorder - phoneHeigth)) {
                    mScroller.startScroll(0, getScrollY(), 0,bottomBorder - phoneHeigth - getScrollY(),200);
//                    lastY = bottomBorder - phoneHeigth;
                } else {
                    // 在最小值和最大高度之间的惯性滑动
                    //计算1000ms的速度
                    mVelocityTracker.computeCurrentVelocity(1000, mMaxVelocity);
                    //获取y在mPointerId上的的速度
                    final float velocityY = mVelocityTracker.getYVelocity(mPointerId);
                    // 滚动一段距离
                    mScroller.fling(0,getScrollY(),0,(int)-velocityY,0,0,headerHeight,bottomBorder - phoneHeigth - 10);
                    recycleVelocityTracker();
//                    lastY = getScrollY();
                }
                invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    // 完成刷新
    public void completeFlash() {
        mScroller.startScroll(0, getScrollY(), 0,headerHeight - getScrollY(),500);
        postInvalidate();
        lastY = headerHeight;
    }

    // 开始刷新
    public void startFlash() {
        mScroller.startScroll(0, getScrollY(), 0, - getScrollY(),500);
        postInvalidate();
        lastY = 0;
    }



    @Override
    public void computeScroll() {
        /** 实现内部平滑滚动 **/
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    /**
     * 创建新的速度监视对象
     *
     * @param event 滑动事件
     */
    private void obtainVelocityTracker(MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 释放资源
     */
    private void recycleVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }
}
