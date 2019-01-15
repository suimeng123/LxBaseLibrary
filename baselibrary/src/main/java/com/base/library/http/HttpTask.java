package com.base.library.http;

import java.util.HashMap;

/**
 * com.lx.baselibrary.http
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/17.
 * http请求任务类
 */

public class HttpTask {

    private Thread thread;
    private Object mParams;
    private String mUrl;
    private String method;


    public HttpTask(String method, String url, Object params) {
        this.mUrl = url;
        this.method = method;
        this.mParams = params;
    }

    /** 发起http请求 **/
    public void sendHttp(final IHttpHelper helper) {
        thread = new Thread( new Runnable() {
            @Override
            public void run() {
                if(IHttpHelper.POST.equals(method)) {
                    helper.doPost(mUrl,mParams);
                } else {
                    helper.doGet(mUrl, (HashMap<String, Object>) mParams);
                }
            }
        });
        thread.start();
    }

    /** 中断结束Thread **/
    public void interrruptThread() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
        }
    }
}
