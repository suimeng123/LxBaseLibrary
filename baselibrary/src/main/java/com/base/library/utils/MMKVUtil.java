package com.base.library.utils;

import android.content.SharedPreferences;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencent.mmkv.MMKV;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Set;

/**
 * 腾讯MMKV存储数据
 * com.base.library.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2019/1/16.
 */

public class MMKVUtil {

    public final static int TYPE_BOOLEAN = 1;
    public final static int TYPE_STRING = 2;
    public final static int TYPE_INTEGER = 3;
    public final static int TYPE_FLOAT = 4;
    public final static int TYPE_DOUBLE = 5;
    public final static int TYPE_LONG = 6;
    public final static int TYPE_BYTE = 7;
    public final static int TYPE_PARCELABLE = 8;
    public final static int TYPE_SET = 9;
    public final static int TYPE_JSONARRAY = 10;

    /** 保存数据 **/
    public static void setValue(String name, Object value) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            if (value == null) {
                kv.encode(name, "");
            } else if (value instanceof Boolean) {
                kv.encode(name, (Boolean) value);
            } else if (value instanceof String) {
                kv.encode(name, value.toString());
            } else if (value instanceof Integer) {
                kv.encode(name, (int)value);
            } else if (value instanceof Float) {
                kv.encode(name, (float)value);
            } else if (value instanceof Double) {
                kv.encode(name, (double)value);
            } else if (value instanceof Long) {
                kv.encode(name, (long)value);
            } else if (value instanceof Byte) {
                kv.encode(name, (byte[]) value);
            } else if (value instanceof Parcelable) {
                kv.encode(name, (Parcelable)value);
            } else if (value instanceof Set) {
                kv.encode(name, (Set<String>) value);
            } else {
                kv.encode(name, value.toString());
            }
        } else {
            throw new RuntimeException("MMKV is NULL!");
        }
    }

    /** 取数据 **/
    public static Object getValue(String name, int type, Class... clazzs) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            switch (type) {
                case TYPE_BOOLEAN:
                    return kv.decodeBool(name);
                case TYPE_STRING:
                    return kv.decodeString(name);
                case TYPE_INTEGER:
                    return kv.decodeInt(name);
                case TYPE_FLOAT:
                    return kv.decodeFloat(name);
                case TYPE_DOUBLE:
                    return kv.decodeDouble(name);
                case TYPE_LONG:
                    return kv.decodeLong(name);
                case TYPE_BYTE:
                    return kv.decodeBytes(name);
                case TYPE_PARCELABLE:
                    return kv.decodeParcelable(name,clazzs[0]);
                case TYPE_SET:
                    return kv.decodeStringSet(name);
                case TYPE_JSONARRAY:
                    String arrayStr = kv.decodeString(name);
                    if (!TextUtils.isEmpty(arrayStr)) {
                        return new ArrayList<>(JSON.parseArray(arrayStr, clazzs[0]));
                    } else {
                        return null;
                    }
                default:
                    break;
            }
        } else {
            throw new RuntimeException("MMKV is NULL!");
        }
        return null;
    }

    /** 取数据带默认数据 **/
    public static Object getValue(String name, int type,Object defaultObject, Class... clazzs) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            switch (type) {
                case TYPE_BOOLEAN:
                    return kv.decodeBool(name, (Boolean) defaultObject);
                case TYPE_STRING:
                    return kv.decodeString(name, (String) defaultObject);
                case TYPE_INTEGER:
                    return kv.decodeInt(name, (Integer) defaultObject);
                case TYPE_FLOAT:
                    return kv.decodeFloat(name, (Float) defaultObject);
                case TYPE_DOUBLE:
                    return kv.decodeDouble(name, (Double) defaultObject);
                case TYPE_LONG:
                    return kv.decodeLong(name, (Long) defaultObject);
                case TYPE_BYTE:
                    return kv.decodeBytes(name);
                case TYPE_PARCELABLE:
                    return kv.decodeParcelable(name,clazzs[0], (Parcelable) defaultObject);
                case TYPE_SET:
                    return kv.decodeStringSet(name, (Set<String>) defaultObject);
                case TYPE_JSONARRAY:
                    String arrayStr = kv.decodeString(name, (String) defaultObject);
                    return new ArrayList<>(JSON.parseArray(arrayStr, clazzs[0]));
                default:
                    break;
            }
        } else {
            throw new RuntimeException("MMKV is NULL!");
        }
        return null;
    }

    /** 删除单个数据 **/
    public static void removeValue(String name) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
           SharedPreferences.Editor editor=  kv.remove(name);
           editor.commit();
        } else {
            throw new RuntimeException("MMKV is NULL!");
        }
    }

    /** 根据键删除单个数据 **/
    public static void removeValueForKey(String name) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            kv.removeValueForKey(name);
        } else {
            throw new RuntimeException("MMKV is NULL!");
        }
    }

    /** 根据一组键名删除多个数据 **/
    public static void removeValue(String[] names) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            kv.removeValuesForKeys(names);
        } else {
            throw new RuntimeException("MMKV is NULL!");
        }
    }

    /** 判断某个数据是否存在 **/
    public static boolean isExit(String name, int type, Class... clazzs) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            Object value = getValue(name, type, clazzs);
            if (value == null) {
                return false;
            } else {
                return true;
            }
        } else {
            throw new RuntimeException("MMKV is NULL!");
        }
    }
}
