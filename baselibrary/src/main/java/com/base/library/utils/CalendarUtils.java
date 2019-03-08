package com.base.library.utils;

import android.content.Context;

import com.base.library.model.BaseDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * com.lx.simplepass.utils
 * SimplePass
 * Created by lixiao2
 * 2019/3/4.
 * 日历工具类
 */

public class CalendarUtils {

    public final static int MAX_COUNT = 1000;

    /** 生成一个日历控件 **/
    public static void createCalendarView(Context context) {

    }

    /** 得到年月 0是当月 -1是前一月 1是下一月 类推 **/
    public static String getYearAndMonth(int temp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, temp);
        return getDate(calendar);
    }

    /** 得到某天的日期 0是今天 -1是昨天 1是明天依次类推 **/
    public static String getOneDay(int temp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, temp);
        return getDate(calendar);
    }
    private static String getDate(Calendar calendar) {
        return calendar.get(Calendar.YEAR) + "-" + getTwoData((calendar.get(Calendar.MONTH) + 1)) + "-" + getTwoData(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /** 补满两位 **/
    private  static String getTwoData(int time) {
        if (time == 0 || time > 9) {
            return time + "";
        }
        return "0" + time;
    }

    /** 得到这个月第一天是星期几(1-7) **/
    public static int getCurrentMonthFirstWeek(Calendar... c) {
        Calendar calendar = (c == null ? Calendar.getInstance() : c[0]);
        calendar.add(Calendar.MONTH, 0); // 设置为当前月
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置为当前月第一天
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /** 得到这个月最后一天是星期几(1-7) **/
    public static int getCurrentMonthLastWeek(Calendar... c) {
        Calendar calendar = (c == null ? Calendar.getInstance() : c[0]);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置为当前月最后一天
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /** 得到当前月天数 **/
    public static int getCurrentDays(Calendar... c) {
        Calendar calendar = (c == null ? Calendar.getInstance() : c[0]);
        calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置为当前月最后一天
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /** 得到当前页面所有日期 **/
    public static List<BaseDate> getCurrentMonthDays(Calendar calendar) {
        List<BaseDate> dates = new ArrayList<>();
        int firstWeek = getCurrentMonthFirstWeek(calendar);
        int days = getCurrentDays(calendar);
        int lastWeek = getCurrentMonthLastWeek(calendar);

        if (firstWeek > 1) {
            // 跳到上一个月
            calendar.add(Calendar.MONTH, -1);
            for (int i = firstWeek - 2; i > -1; i--) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - i);
                // 是否是今天
                if (isToday(calendar)) {
                    BaseDate baseDate = setInfo(calendar, true);
                    baseDate.setToday(true);
                    dates.add(baseDate);
                    continue;
                }
                dates.add(setInfo(calendar, false));
            }
        } else {
            calendar.add(Calendar.MONTH, -1);
        }

        // 跳到当前月
        calendar.add(Calendar.MONTH, 1);
        for (int i = 1; i < days + 1; i++) {
            calendar.set(Calendar.DAY_OF_MONTH, i);
            // 是否是今天
            if (isToday(calendar)) {
                BaseDate baseDate = setInfo(calendar, true);
                baseDate.setToday(true);
                dates.add(baseDate);
                continue;
            }
            dates.add(setInfo(calendar, true));
        }

        if (lastWeek < 7) {
            // 跳转到下一个月
            calendar.add(Calendar.MONTH, 1);
            for (int i = 1; i <= (7 - lastWeek); i++) {
                calendar.set(Calendar.DAY_OF_MONTH, i);
                // 是否是今天
                if (isToday(calendar)) {
                    BaseDate baseDate = setInfo(calendar, true);
                    baseDate.setToday(true);
                    dates.add(baseDate);
                    continue;
                }

                dates.add(setInfo(calendar, false));
            }
        }

        return dates;
    }

    private static boolean isToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
            return true;
        }
        return false;
    }

    /** 获取指定月的日期集合 temp为0是当前月-1是上个月1是下个月依次类推 **/
    public static List<BaseDate> getDesignatDays(int tempMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, tempMonth);
        return getCurrentMonthDays(calendar);
    }

    /** 获取指定月的日期集合 date为指定的日期 **/
    public static List<BaseDate> getDesignatDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getCurrentMonthDays(calendar);
    }

    /** 设置某天的日期数据并返回 **/
    private static BaseDate setInfo(Calendar calendar, boolean isCurrentMonth) {
        BaseDate baseDate = new BaseDate();
        baseDate.setYear(calendar.get(Calendar.YEAR));
        baseDate.setMonth(calendar.get(Calendar.MONTH) + 1);
        baseDate.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        baseDate.setDate(baseDate.simpleDateFormat.format(calendar.getTime()));
        baseDate.setCurrentMonth(isCurrentMonth);
        baseDate.setToday(false);
        baseDate.setClicked(false);
        baseDate.setWeek(calendar.get(Calendar.DAY_OF_WEEK));
        baseDate.setWeekOfMonth(calendar.get(Calendar.WEEK_OF_MONTH));

        // 设置农历参数
        LunarUtil lunar = new LunarUtil(calendar);
        baseDate.setLunarYear(lunar.getYear() + "");
        baseDate.setLunarMonth(LunarUtil.chineseNumber[lunar.getMonth() - 1]);
        baseDate.setLunarDay(LunarUtil.getChinaDayString(lunar.getDay()));
        baseDate.setLeap(lunar.isLeap());
        baseDate.setLunarDate(lunar.toString());
        return baseDate;
    }
}
