package com.base.library.model;

import java.text.SimpleDateFormat;

/**
 * com.lx.simplepass.model
 * SimplePass
 * Created by lixiao2
 * 2019/3/4.
 */

public class BaseDate {
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int year;
    private int month;
    private int day;
    // 当前日期 格式是yyyy-MM-dd
    private String date;
    // 农历年
    private String lunarYear;
    // 农历月
    private String lunarMonth;
    // 农历日
    private String lunarDay;
    // 农历日期
    private String lunarDate;
    // 是否是闰月
    private boolean leap;
    // 节假日啥的提示
    private String tip;
    // 是否是今天
    private boolean isToday;
    // 是否点击选中
    private boolean isClicked;
    // 是否是当前月
    private boolean isCurrentMonth;
    // 星期几
    private int week;
    // 当前天在当前月的第几周（从1开始）
    private int weekOfMonth;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLunarYear() {
        return lunarYear;
    }

    public void setLunarYear(String lunarYear) {
        this.lunarYear = lunarYear;
    }

    public String getLunarMonth() {
        return lunarMonth;
    }

    public void setLunarMonth(String lunarMonth) {
        this.lunarMonth = lunarMonth;
    }

    public String getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(String lunarDay) {
        this.lunarDay = lunarDay;
    }

    public String getLunarDate() {
        return lunarDate;
    }

    public void setLunarDate(String lunarDate) {
        this.lunarDate = lunarDate;
    }

    public boolean isLeap() {
        return leap;
    }

    public void setLeap(boolean leap) {
        this.leap = leap;
    }

    public boolean isToday() {
        return isToday;
    }

    public void setToday(boolean today) {
        isToday = today;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }

    public void setCurrentMonth(boolean currentMonth) {
        isCurrentMonth = currentMonth;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeekOfMonth() {
        return weekOfMonth;
    }

    public void setWeekOfMonth(int weekOfMonth) {
        this.weekOfMonth = weekOfMonth;
    }
}
