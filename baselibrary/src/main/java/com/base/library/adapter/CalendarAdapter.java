package com.base.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.library.R;
import com.base.library.model.BaseDate;

import java.util.List;

/**
 * com.lx.simplepass.adapter
 * SimplePass
 * Created by lixiao2
 * 2019/3/5.
 */

public class CalendarAdapter extends CommonAdapter {

    private TextView mTVContent;
    public CalendarAdapter(Context context, List datas, int layoutId, TextView tv) {
        super(context, datas, layoutId);
        mTVContent = tv;
    }

    @Override
    public void convert(ViewHolder holder, Object o) {
        BaseDate item = (BaseDate) o;
        TextView day = holder.getView(R.id.tv_day);
        TextView lunday = holder.getView(R.id.tv_lunday);
        LinearLayout bg = holder.getView(R.id.ll_bg);
        day.setText(item.getDay() + "");
        lunday.setText(item.getLunarDay());

        if (item.isToday()) {
            // 是今天
            day.setTextColor(Color.parseColor("#ffffff"));
            lunday.setTextColor(Color.parseColor("#ffffff"));
            bg.setBackground(ActivityCompat.getDrawable(mContext, R.drawable.bg_calendar_today));
        } else {
            // 不是今天
            if (item.isClicked()) {
                // 点击选中
                day.setTextColor(Color.parseColor("#ffffff"));
                lunday.setTextColor(Color.parseColor("#ffffff"));
                bg.setBackground(ActivityCompat.getDrawable(mContext, R.drawable.bg_calendar_clicked));
                mTVContent.setText(item.getLunarDate());
            } else {
                // 没有点击选中
                if (item.getWeek() == 1 || item.getWeek() == 7 || !item.isCurrentMonth()) {
                    // 第一列和第七列 还有不是这个月的日期
                    day.setTextColor(Color.parseColor("#999999"));
                } else {
                    day.setTextColor(Color.parseColor("#000000"));
                }
                lunday.setTextColor(Color.parseColor("#999999"));
                bg.setBackground(null);
            }
        }

    }
}
