package com.lx.baselibrary.http;

import java.io.File;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * com.lx.baselibrary.http
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/17.
 */

public interface IHttpHelper {
    String POST = "post";
    String GET = "get";
    /** post请求方式 **/
    void doPost(String url);
    /** post请求方式 params请求参数 可以放入文件类型参数 **/
    void doPost(String url, Object params);
    /** get请求方式 **/
    void doGet(String url);
    /** get请求方式 自动将HashMap键值对参数转为url后面带的参数形式 **/
    void doGet(String url, HashMap<String, Object> params);
    /** 取消发起的http请求 **/
    void cancleHttp();
}
