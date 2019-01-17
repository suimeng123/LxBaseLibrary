package com.base.library.http.callback;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;

import com.base.library.http.parser.BaseParser;
import com.base.library.http.parser.IParser;

import java.util.ArrayList;

/**
 * com.lx.baselibrary.http.callback
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/21.
 */

public class BaseCallBack implements ICallBack {
    /** 解析器 **/
    private IParser parser;
    private Handler handler;
    /** 返回结果对象Class **/
    private Class resultClass;
    /** 唯一标识这一次请求 作为handler中message的what参数值 **/
    private int flag = 0;

    public BaseCallBack(IParser parser, Handler handler, Class clazz, int flag) {
        this.parser = parser;
        this.handler = handler;
        this.resultClass = clazz;
        this.flag = flag;
    }
    public BaseCallBack(IParser parser, Handler handler, Class clazz) {
        this(parser,handler,clazz,0);
    }
    public BaseCallBack(IParser parser, Handler handler) {
        this(parser,handler,null,0);
    }
    @Override
    public void setInfo(Class clazz, int flag) {
        this.resultClass = clazz;
        this.flag = flag;
    }


    @Override
    public void success(String result) {
        boolean iSuccess = parser.parser(result, resultClass);
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        if (iSuccess) {
            if (resultClass != null) {
                if (parser.getData() instanceof ArrayList) {
                    bundle.putParcelableArrayList(((BaseParser) parser).getDataName(), (ArrayList<? extends Parcelable>) parser.getData());
                } else {
                    bundle.putParcelable(((BaseParser) parser).getDataName(), (Parcelable) parser.getData());
                }
            } else {
                bundle.putString(((BaseParser) parser).getDataName(), parser.getData().toString());
            }
            bundle.putString(((BaseParser) parser).getMessageName(), parser.getMessage());
            bundle.putInt(((BaseParser) parser).getCodeName(), parser.getCode());
            bundle.putBoolean(((BaseParser) parser).getStateName(), true);
        } else {
            bundle.putBoolean(((BaseParser) parser).getStateName(), false);
            bundle.putString(((BaseParser) parser).getMessageName(), "Parser is error!");
        }
        message.what = flag;
        message.setData(bundle);
        handler.sendMessage(message);
    }

    @Override
    public void fail(String result) {
        Message message = Message.obtain();
        Bundle bundle = new Bundle();
        bundle.putBoolean(((BaseParser) parser).getStateName(), false);
        bundle.putString(((BaseParser) parser).getMessageName(), result);
        message.what = flag;
        message.setData(bundle);
        handler.sendMessage(message);
    }
}
