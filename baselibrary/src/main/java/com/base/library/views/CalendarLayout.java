package com.base.library.views;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.adapter.CalendarAdapter;
import com.base.library.model.BaseDate;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lx.simplepass.views
 * SimplePass
 * Created by lixiao2
 * 2019/3/4.
 * 单日历页面
 */

public class CalendarLayout extends LinearLayout {

    private GridView mGvContent;
    private TextView mTvTitle;
    private TextView mTvContent;
    private LinearLayout mLlWeek;

    private Context mContext;

    private String[] weeks = new String[]{"日","一","二","三","四","五","六"};

    private CalendarAdapter adapter;
    private List<BaseDate> dates = new ArrayList<>();

    public CalendarLayout(Context context) {
        this(context, null);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CalendarLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setBackgroundColor(ActivityCompat.getColor(context, R.color.white));
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        init(context);
    }

    /** 初始化 **/
    private void init(Context context) {
        /** 初始化 布局 **/
        View content = LayoutInflater.from(context).inflate(R.layout.view_calendar, null, false);
        mGvContent = content.findViewById(R.id.c_gv);
        mTvTitle = content.findViewById(R.id.c_title);
        mTvContent = content.findViewById(R.id.c_content);
        mLlWeek = content.findViewById(R.id.c_week);
        initWeek(context, mLlWeek);
//        mTvTitle.setText(CalendarUtils.getToday());
        addView(content);
    }

    /** 设置日历数据 **/
    public void setCalendarData(List<BaseDate> list) {
        if (list == null) {
            return;
        }
        dates.clear();
        dates.addAll(list);
        if (adapter == null) {
            adapter = new CalendarAdapter(mContext, dates, R.layout.griditem_calendar, mTvContent);
            mGvContent.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }

        mGvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onclick(dates.get(position));
            }
        });
    }

    /** 设置标题数据 **/
    public void setTitle(CharSequence str) {
        mTvTitle.setText(str);
    }

    /** 设置内容数据 **/
    public void setContent(CharSequence str) {
        mTvContent.setText(str);
    }

    /** 初始化星期几那一行数据 **/
    private void initWeek(Context context, LinearLayout parent) {
        if (parent == null) {
            return;
        }
        for (int i = 0; i < weeks.length; i++) {
            TextView textView = new TextView(context);
            textView.setText(weeks[i]);
            textView.setTextSize(18f);
            textView.getPaint().setFakeBoldText(true);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#000000"));
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            textView.setLayoutParams(params);
            parent.addView(textView);
        }
    }

    public interface OnDateItemListener {
        void onclick(BaseDate baseDate);
    }
    private OnDateItemListener listener;
    public void addDateItemListener(OnDateItemListener l) {
        this.listener = l;
    }

    public GridView getmGvContent() {
        return mGvContent;
    }

    public void setmGvContent(GridView mGvContent) {
        this.mGvContent = mGvContent;
    }

    public TextView getmTvTitle() {
        return mTvTitle;
    }

    public void setmTvTitle(TextView mTvTitle) {
        this.mTvTitle = mTvTitle;
    }

    public TextView getmTvContent() {
        return mTvContent;
    }

    public void setmTvContent(TextView mTvContent) {
        this.mTvContent = mTvContent;
    }

    public LinearLayout getmLlWeek() {
        return mLlWeek;
    }

    public void setmLlWeek(LinearLayout mLlWeek) {
        this.mLlWeek = mLlWeek;
    }

    public List<BaseDate> getDates() {
        return dates;
    }

    public void setDates(List<BaseDate> dates) {
        this.dates = dates;
    }

    public CalendarAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(CalendarAdapter adapter) {
        this.adapter = adapter;
    }
}
