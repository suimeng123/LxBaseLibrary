package com.base.library.app;

import android.app.Application;

import com.tencent.mmkv.MMKV;

/**
 * com.base.library.app
 * LxBaseLibrary
 * Created by lixiao2
 * 2019/1/16.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
    }
}
