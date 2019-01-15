package com.base.library.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/5.
 * 关于View或其子类操作的工具类
 */

public class ViewUtil {

    /** 为TextView设置drawable图片 type为1上 2下 3左 4右 source是图片资源 **/
    public static void setTextViewDrawable(Context context , TextView tv, int type, int source){
        if (source == 0) {
            // 为0 清空所有drawble
            tv.setCompoundDrawables(null, null, null, null);
            return;
        }
        Drawable nav_up = context.getResources().getDrawable(source);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        switch(type){
            case 1:
                tv.setCompoundDrawables(null, nav_up, null, null);
                break;
            case 2:
                tv.setCompoundDrawables(null, null, null, nav_up);
                break;
            case 3:
                tv.setCompoundDrawables(nav_up, null, null, null);
                break;
            case 4:
                tv.setCompoundDrawables(null, null, nav_up, null);
                break;
        }
    }
}
