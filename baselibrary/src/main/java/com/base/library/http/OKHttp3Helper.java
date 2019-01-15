package com.base.library.http;


import com.base.library.http.callback.ICallBack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * com.lx.baselibrary.http
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/17.
 * okhttp3网络请求操作类
 */

public class OKHttp3Helper implements IHttpHelper {
    /** 拦截器 **/
    private Interceptor mInterceptor;
    /** 网络拦截器 **/
    private Interceptor mNetworkInterceptor;
    /** 接口返回结果回调 **/
    private ICallBack mCallback;

    /** 请求头部参数信息 **/
    private IRequestHeader mRequestHeader;
    /** 超时时间单位 **/
    private static final TimeUnit TIMEUNIT = TimeUnit.SECONDS;

    /** json数据请求格式 **/
    private static final MediaType JSONTYPE = MediaType.parse("application/json; charset=utf-8");
    /** 文件数据请求格式 **/
    private static final MediaType FILETYPE = MediaType.parse("application/octet-stream");

    private Call mCall;

    public OKHttp3Helper(IRequestHeader requestHeader, Interceptor interceptor, boolean isNetworkInterceptor,ICallBack callBack) {
        if (requestHeader == null) {
            this.mRequestHeader = new BaseIRequestHeader();
        } else {
            this.mRequestHeader = requestHeader;
        }
        if (isNetworkInterceptor) {
            this.mInterceptor = interceptor;
        } else {
            this.mNetworkInterceptor = interceptor;
        }
        this.mCallback = callBack;
    }
    public OKHttp3Helper(ICallBack callBack) {
        this(new BaseIRequestHeader(),null, false, callBack);
    }


    public OkHttpClient getClient() {
        OkHttpClient.Builder builder =  new OkHttpClient().newBuilder().connectTimeout(mRequestHeader.getConnectTimeOut(),TIMEUNIT)
                .readTimeout(mRequestHeader.getReadTimeOut(),TIMEUNIT).writeTimeout(mRequestHeader.getWriteTimeOut(),TIMEUNIT)
                .callTimeout(mRequestHeader.getCallTimeOut(),TIMEUNIT);
        if (mInterceptor != null) {
            builder.addInterceptor(mInterceptor);
        }
        if (mNetworkInterceptor != null) {
            builder.addNetworkInterceptor(mNetworkInterceptor);
        }
        return builder.build();
    }

    @Override
    public void doPost(String url) {
        doPost(url,null);
    }

    @Override
    public void doPost(String url, Object params) {
        OkHttpClient client = getClient();
        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (params != null && params instanceof String) {
            /** 判断是json格式的参数 **/
            RequestBody body = RequestBody.create(JSONTYPE, (String)params);
            builder.addPart(body);
        } else if (params != null && params instanceof HashMap) {
            /** 判断是HashMap格式参数 **/
            for(Object key : ((HashMap) params).keySet()) {
                Object value = ((HashMap) params).get(key);
                if (value instanceof File) {
                    /** 如果是文件参数 **/
                    RequestBody fileBody = RequestBody.create(FILETYPE, (File)value);
                    builder.addPart(Headers.of("Content-Disposition", "form-data; name=\""+ key +"\";filename=\""+ ((File)value).getName()+"\""), fileBody);
                } else {
                    /** 其他类型参数 **/
                    builder.addFormDataPart((String) key, ((HashMap) params).get(key).toString());
                }
            }
        }
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        mCall = client.newCall(request);
        mCall.enqueue(okHttpCallBack);
    }

    @Override
    public void doGet(String url) {
        OkHttpClient client = getClient();
        Request request = new Request.Builder().get().url(url).build();
        mCall = client.newCall(request);
        mCall.enqueue(okHttpCallBack);
    }

    @Override
    public void doGet(String url, HashMap<String, Object> params) {
        doGet(url + getStringForMap(params));
    }

    /** 将HashMap中的参数转换为字符串形式 **/
    private String getStringForMap(HashMap<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        StringBuffer sb = new StringBuffer("?");
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                sb.append(key).append("=").append(params.get(key));
            } else {
                sb.append("&").append(key).append("=").append(params.get(key));
            }
        }
        return sb.toString();
    }

    @Override
    public void cancleHttp() {
        if (mCall != null && !mCall.isCanceled()) {
            mCall.cancel();
        }
    }

    /** 请求回调 **/
    private Callback okHttpCallBack = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mCallback.fail(e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            mCallback.success(response.body().string());
        }
    };
}
