package com.lx.baselibrary.utils;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/17.
 * 线程相关工具类
 */

public class ThreadUtil {

    public static Thread startThread(String threadName, Runnable runnable) {
        Thread thread = new Thread(runnable, threadName);
        thread.start();
        return thread;
    }
}
