package com.lx.baselibrary.http.callback;

/**
 * com.lx.baselibrary.http
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/20.
 */

public interface ICallBack {
    /** 成功回调 **/
    void success(String result);
    /** 失败回调 **/
    void fail(String result);
    /** 设置返回的结果对象class 和每次请求的标识 **/
    void setInfo(Class clazz, int flag);
}
