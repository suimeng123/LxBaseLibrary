package com.lx.baselibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Set;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/11.
 * SharedPreferences存储
 */

public class SharedPreferencesHelper {
    private SharedPreferences sp = null;

    public SharedPreferencesHelper (Context context, String fileName) {
        sp = getSharedPreferences(context, fileName);
    }
    public SharedPreferencesHelper (Activity activity) {
        sp = getSharedPreferences(activity);
    }
    public SharedPreferencesHelper(Context context) {
        sp = getDefaultSharedPreferences(context);
    }


    /**
     * 方式一
     * 通过Context获取SharedPreferences,需要自己设置xml文件名
     * @param context 上下文
     * @param fileName 存储的xml文件名
     * @return
     */
    public static SharedPreferences getSharedPreferences (Context context, String fileName) {
        return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    /**
     * 方式二
     * 通过Activity获取SharedPreferences,默认用当前Activity的类名作为SharedPreferences的xml文件名
     * @param activity
     * @return
     */
    public static SharedPreferences getSharedPreferences (Activity activity) {
        return activity.getPreferences(Context.MODE_PRIVATE);
    }

    /**
     * 方式三
     * 通过PreferenceManager获取SharedPreferences,使用当前包名作为前缀来命名SharedPreferences的xml文件名
     * @param context
     * @return
     */
    public static SharedPreferences getDefaultSharedPreferences (Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    /** 通过键值对设置数据缓存 apply方式 **/
    public void setInfoApply(String name, Object value) {
        SharedPreferences.Editor editor = getEditor(name, value);
        editor.apply();
    }

    /** 通过键值对设置数据缓存 commit方式 **/
    public void setInfoCommit(String name, Object value) {
        SharedPreferences.Editor editor = getEditor(name, value);
        editor.commit();
    }

    /** 根据不同的参数类型，获取相应的Editor **/
    private SharedPreferences.Editor getEditor(String name, Object value) {
        checkSPNull();
        SharedPreferences.Editor editor = null;
        if (value instanceof String) {
            editor = sp.edit().putString(name, (String) value);
        } else if (value instanceof Integer) {
            editor = sp.edit().putInt(name, (Integer) value);
        } else if (value instanceof Boolean) {
            editor = sp.edit().putBoolean(name, (Boolean) value);
        } else if (value instanceof Float) {
            editor = sp.edit().putFloat(name, (Float) value);
        } else if (value instanceof Long) {
            editor = sp.edit().putLong(name, (Long) value);
        } else if (value instanceof Set) {
            editor = sp.edit().putStringSet(name, (Set<String>) value);
        }
        return editor;
    }

    /** 获取int类型数据 **/
    public int getIntInfo(String name) {
        checkSPNull();
        return sp.getInt(name, 0);
    }

    /** 获取String类型数据 **/
    public String getStringInfo(String name) {
        checkSPNull();
        return sp.getString(name, "");
    }

    /** 获取Boolean类型数据 **/
    public Boolean getBooleanInfo(String name) {
        checkSPNull();
        return sp.getBoolean(name, false);
    }

    /** 获取FLoat类型数据 **/
    public Float getFloatInfo(String name) {
        checkSPNull();
        return sp.getFloat(name, 0f);
    }

    /** 获取Long类型数据 **/
    public Long getLongInfo(String name) {
        checkSPNull();
        return sp.getLong(name, 0);
    }

    /** 获取Set类型数据 **/
    public Set getSetInfo(String name) {
        checkSPNull();
        return sp.getStringSet(name, null);
    }

    /** 判断sp是否为空，为空抛异常 **/
    private void checkSPNull() {
        if (sp == null) {
            throw new RuntimeException("SharedPreferences is not initialize");
        }
    }
}
