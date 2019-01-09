package com.lx.baselibrary.http;

/**
 * com.lx.baselibrary.http
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/20.
 */

public interface IRequestHeader<T> {
    void setConnectTimeOut(int time);
    void setWriteTimeOut(int time);
    void setReadTimeOut(int time);
    void setCallTimeOut(int time);
    int getConnectTimeOut();
    int getWriteTimeOut();
    int getReadTimeOut();
    int getCallTimeOut();
}
