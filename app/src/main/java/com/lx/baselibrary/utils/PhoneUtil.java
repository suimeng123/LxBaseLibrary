package com.lx.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/5.
 * 获取手机特性相关的工具类
 */

public class PhoneUtil {

    /** 获取手机分辨率、宽高等相关的类 **/
    public static DisplayMetrics getDisplayMetrics(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /** dp转px **/
    public static int dp2px(Activity activity, int dpValue) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        return (int) (dpValue * dm.density + 0.5f);
    }

    /** px转dp **/
    public static int px2dp(Activity activity, int pxValue) {
        DisplayMetrics dm = getDisplayMetrics(activity);
        return (int) (pxValue / dm.density + 0.5f);
    }
}
