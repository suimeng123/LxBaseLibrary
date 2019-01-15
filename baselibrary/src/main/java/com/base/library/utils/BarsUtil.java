package com.base.library.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 操作标题栏、状态栏或者导航栏工具类
 * 2018/12/5.
 */

public class BarsUtil {

    /** 隐藏标题栏(需要在setContentView方法前面调用才会生效) **/
    public static void hideTitleBar(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            /* 继承AppCompatActivity的Activity隐藏标题栏 */
            if(((AppCompatActivity)activity).getSupportActionBar() != null) {
                ((AppCompatActivity)activity).getSupportActionBar().hide();
            }
            return;
        }
        /** 普通的Activity隐藏标题栏 **/
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /** 设置状态栏透明 **/
    public static void setStatusBarTranslucent(Activity activity) {
        // android 4.4以下不支持状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //android 5.0以上 - android 6.0(不包括)半透明 但是国内大部分手机厂商都改为了全透明,android 6.0以上全透明
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // android 6.0以上支持状态栏颜色翻转
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            window.getDecorView().setSystemUiVisibility(flags);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // android4.4以上-android5.0(不包括)渐变透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /** 显示或隐藏状态栏 **/
    public static void setStatusBarVisible(Activity activity, boolean isShow) {
        int uiFlags;
        if (isShow) {
            uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        } else {
            uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }




    /** 设置导航栏透明 **/
    public static void setNavigationTranslucent(Activity activity) {
        // android 4.4以下不支持导航栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //android 5.0以上 - android 6.0(不包括)半透明 但是国内大部分手机厂商都改为了全透明,android 6.0以上全透明
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            int flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // android 6.0以上支持状态栏颜色翻转
                flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            }
            window.getDecorView().setSystemUiVisibility(flags);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setNavigationBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // android4.4以上-android5.0(不包括)渐变透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /** 设置显示或隐藏导航栏 **/
    public static  void setNavigationBarVisible(Activity activity, boolean isShow) {
        int uiFlags;
        if (isShow) {
            uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        } else {
            uiFlags = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(uiFlags);
    }
}
