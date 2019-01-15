package com.base.library.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/14.
 * 反射工具类
 */

public class ReflexUtil {

    public static Object getObject(String className) {
        try {
            Class c = Class.forName(className);
            return c.newInstance();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getObject(Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param className 对象全路径类名
     * @param params    构造函数入参类型集合
     * @param values   构造函数参数集合
     * @return 获取当前对象实例
     */
    public static Object getObject(String className, Class[] params, Object[] values) {
        try {
            Constructor constructor = getConstructor(className, params);
            return constructor.newInstance(values);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz     对象Class
     * @param params    构造函数入参类型集合
     * @param values   构造函数参数集合
     * @return 获取当前对象实例
     */
    public static Object getObject(Class clazz, Class[] params, Object[] values) {
        try {
            Constructor constructor = getConstructor(clazz, params);
            return constructor.newInstance(values);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param className 对象全路径类名
     * @param params    构造函数入参类型集合
     * @return  获取当前对象构造函数
     */
    public static Constructor getConstructor(String className, Class[] params) {
        try {
            Class clazz = Class.forName(className);
            Constructor constructor = clazz.getDeclaredConstructor(params);
            return constructor;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz   对象Class
     * @param params  构造函数入参类型集合
     * @return  获取当前对象构造函数
     */
    public static Constructor getConstructor(Class clazz, Class[] params) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor(params);
            return constructor;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param object        当前实例对象
     * @param methodName    方法名称
     * @return  获取某个method
     */
    public static Method getMethod(Object object, String methodName) {
        try {
            return object.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用method方法
     * @param object        当前对象实例
     * @param methodName    调用方法名
     * @param value         方法入参的值
     */
    public static void invokeMethod(Object object, String methodName, Object[] value) {
        try {
            Method method = object.getClass().getDeclaredMethod(methodName);
            method.setAccessible(true);
            method.invoke(object,value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param object    当前对象实例
     * @param fieldName 属性的名称
     * @return  返回当前属性对象 object为null获取的是static属性对象
     */
    public static Object getFieldObject(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给对象属性赋值
     * @param object    当前对象实例
     * @param fieldName 属性的名称
     * @param value     给属性设置的值
     */
    public static void invokeFiled(Object object, String fieldName, Object value) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
