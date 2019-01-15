package com.base.library.http.httputil;

import com.base.library.http.HttpTask;
import com.base.library.http.IHttpHelper;
import com.base.library.http.parser.IParser;

import java.util.HashMap;

/**
 * com.lx.baselibrary.http.IHttpUtil
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/21.
 */

public interface IHttpUtil {
    void doPost(String url);
    void doPost(String url, Object params, Class resultClass, int flag);
    void doGet(String url);
    void doGet(String url, HashMap<String, Object> params, Class resultClass, int flag);
    void cancleHttp();
    void cancleTask();
    HttpTask getHttpTask();
    IHttpHelper getHttpHelper();
    IParser getParser();
}
