package com.base.library.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.base.library.model.BaseDate;
import com.base.library.utils.CalendarUtils;
import com.base.library.views.CalendarLayout;

import java.util.HashMap;
import java.util.List;

/**
 * com.lx.simplepass.adapter
 * SimplePass
 * Created by lixiao2
 * 2019/3/5.
 */

public class CalendarPageAdapter extends PagerAdapter {
    /** 缓存所有初始化过的数据 **/
    private HashMap<Integer, List<BaseDate>> mDatas = new HashMap<>();
    private static final String TAG = "CalendarPageAdapter";
    private Context mContext;

    /** 最后一次点击的日期item项 **/
    private CalendarLayout lastClickedDate = null;

    /** 回调监听 **/
    private CalendarLayout.OnDateItemListener mListener;
    public CalendarPageAdapter(Context context, CalendarLayout.OnDateItemListener l) {
        mContext = context;
        mListener = l;
    }

    @Override
    public int getCount() {
        return CalendarUtils.MAX_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        /** 初始化布局 **/
        int nowPosition = position - CalendarUtils.MAX_COUNT / 2;
        final CalendarLayout view = new CalendarLayout(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);

        /** 初始化数据 并做缓存处理 **/
        List<BaseDate> list = mDatas.get(position);
        if (list != null) {
            view.setCalendarData(list);
        } else {
            List<BaseDate> newLists = CalendarUtils.getDesignatDays(nowPosition);
            view.setCalendarData(newLists);
            mDatas.put(position, newLists);
        }
        /** 设置标题日期 **/
        view.setTitle(CalendarUtils.getYearAndMonth(nowPosition));

        /** 更新pageadapter三个缓存页面的 日期农历显示数据 **/
        if (lastClickedDate == view) {
            for (int i = 0; i < lastClickedDate.getDates().size(); i++) {
                if (lastClickedDate.getDates().get(i).isClicked()) {
                    lastClickedDate.getmTvContent().setText(lastClickedDate.getDates().get(i).getLunarDate());
                } else {
                    lastClickedDate.getmTvContent().setText("");
                }
            }
            /** 刷新页面数据 **/
            lastClickedDate.getAdapter().notifyDataSetChanged();
        }

        view.addDateItemListener(new CalendarLayout.OnDateItemListener() {
            @Override
            public void onclick(BaseDate baseDate) {

                /** 移除之前点击的日期的选中状态 **/
                if (lastClickedDate != null) {
                    for (int i = 0; i < lastClickedDate.getDates().size(); i++) {
                        lastClickedDate.getDates().get(i).setClicked(false);
                        lastClickedDate.getmTvContent().setText("");
                    }
                    /** 刷新页面数据 **/
                    lastClickedDate.getAdapter().notifyDataSetChanged();
                }

                /** 点击后的选中状态 **/
                for (int i = 0; i < mDatas.get(position).size(); i++) {
                    if (baseDate == mDatas.get(position).get(i)) {
                        mDatas.get(position).get(i).setClicked(true);
                    } else {
                        mDatas.get(position).get(i).setClicked(false);
                    }
                }
                /** 刷新页面数据 **/
                view.getAdapter().notifyDataSetChanged();
                view.getmTvContent().setText(baseDate.getLunarDate());
                /** 设置回调 **/
                mListener.onclick(baseDate);
                lastClickedDate = view;
            }
        });

        container.addView(view);

        return view;

    }

//    @Override
//    public int getItemPosition(Object object) {
//        return POSITION_NONE;
//    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        /** 移除即将销毁的布局 不然可能会报错 **/
        container.removeView((View) object);
    }
}
