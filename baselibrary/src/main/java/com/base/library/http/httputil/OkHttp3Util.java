package com.base.library.http.httputil;

import android.os.Handler;

import com.base.library.http.HttpTask;
import com.base.library.http.IHttpHelper;
import com.base.library.http.IRequestHeader;
import com.base.library.http.OKHttp3Helper;
import com.base.library.http.callback.BaseCallBack;
import com.base.library.http.callback.ICallBack;
import com.base.library.http.parser.BaseParser;
import com.base.library.http.parser.IParser;

import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * com.lx.baselibrary.utils
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/20.
 */

public class OkHttp3Util implements IHttpUtil {
    private IHttpHelper httpHelper;
    private IParser parser;
    private HttpTask httpTask;
    private ICallBack callBack;

    public OkHttp3Util(Handler handler){
        this(handler, null, null, false);
    }

    public OkHttp3Util(Handler handler, IRequestHeader header){
        this(handler, header, null, false);
    }

    public OkHttp3Util(Handler handler, IRequestHeader header, Interceptor interceptor, boolean isNetWork){
        this.parser = new BaseParser();
        this.callBack = new BaseCallBack(parser, handler);
        this.httpHelper = new OKHttp3Helper(header,interceptor,isNetWork,this.callBack);
    }

    public OkHttp3Util(Handler handler, IParser parser, ICallBack callBack){
        this(handler,parser,callBack, null, null, false);
    }

    public OkHttp3Util(Handler handler,IParser parser, ICallBack callBack, IRequestHeader header, Interceptor interceptor, boolean isNetWork){
        if (parser == null) {
            this.parser = new BaseParser();
        } else {
            this.parser = parser;
        }
        if (callBack == null) {
            this.callBack = new BaseCallBack(this.parser, handler);
        } else {
            this.callBack = callBack;
        }
        this.httpHelper = new OKHttp3Helper(header,interceptor,isNetWork,this.callBack);
    }

    @Override
    public void doPost(String url) {
        doPost(url, null, null, 0);
    }

    @Override
    public void doPost(String url, Object params, Class resultClass, int flag) {
        this.callBack.setInfo(resultClass, flag);
        httpTask = new HttpTask(IHttpHelper.POST, url,params);
        httpTask.sendHttp(httpHelper);
    }

    @Override
    public void doGet(String url) {
        doGet(url, null, null, 0);
    }

    @Override
    public void doGet(String url, HashMap<String, Object> params, Class resultClass, int flag) {
        this.callBack.setInfo(resultClass, flag);
        httpTask = new HttpTask(IHttpHelper.GET, url,params);
        httpTask.sendHttp(httpHelper);
    }

    @Override
    public void cancleHttp() {
        if (httpHelper != null) {
            httpHelper.cancleHttp();
        }
    }

    /** 取消当前请求任务 **/
    @Override
    public void cancleTask() {
        if (httpTask != null) {
            httpTask.interrruptThread();
        }
    }

    @Override
    public HttpTask getHttpTask() {
        return httpTask;
    }

    @Override
    public IHttpHelper getHttpHelper() {
        return httpHelper;
    }

    @Override
    public BaseParser getParser() {
        return (BaseParser)parser;
    }
}
