package com.base.library.http;

/**
 * com.lx.baselibrary.http
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/20.
 */

public class BaseIRequestHeader implements IRequestHeader {
    /** 连接超时时间 **/
    private int connectTimeOut = 15 * 1000;
    /** 写入超时时间 **/
    private int writeTimeOut = 15 * 1000;
    /** 读取超时时间 **/
    private int readTimeOut = 15 * 1000;
    /** 响应超时时间 **/
    private int callTimeOut = 15 * 1000;

    @Override
    public void setConnectTimeOut(int time) {
        this.connectTimeOut = time;
    }

    @Override
    public void setWriteTimeOut(int time) {
        this.writeTimeOut = time;
    }

    @Override
    public void setReadTimeOut(int time) {
        this.readTimeOut = time;
    }

    @Override
    public void setCallTimeOut(int time) {
        this.callTimeOut = time;
    }

    @Override
    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    @Override
    public int getWriteTimeOut() {
        return writeTimeOut;
    }

    @Override
    public int getReadTimeOut() {
        return readTimeOut;
    }

    @Override
    public int getCallTimeOut() {
        return callTimeOut;
    }
}
