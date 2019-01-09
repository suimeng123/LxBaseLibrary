package com.lx.baselibrary.http.parser;

import android.text.TextUtils;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * com.lx.baselibrary.http.parser
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/20.
 * 基础解析器
 */

public class BaseParser implements IParser {

    private Object mData = null;
    private int code = 0;
    private String message = "Network is error!";

    /** 服务端返回的主体数据名称 **/
    private String dataName = "data";
    /** 服务端返回状态数据名称 **/
    private String codeName = "retcode";
    /** 服务端返回的提示信息名称 **/
    private String messageName = "message";
    /** 服务端请求是否成功 **/
    private String stateName = "iSuccess";


    @Override
    public Object getData() {
        return mData;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean parser(String result, Class clazz) {
         if (TextUtils.isEmpty(result)){
            return false;
        }
        try {
            JSONObject object = JSONObject.parseObject(result);
            code = object.getInteger(getCodeName());
            message = object.getString(getMessageName());
            Object data = object.get(getDataName());
            if (clazz == null) {
                mData = data;
                return true;
            } else {
                if (data instanceof JSONObject) {
                    mData = new ArrayList<>(JSONArray.parseArray(object.getString(getDataName()), clazz));
                } else {
                    mData = JSON.parseObject(object.getString(getDataName()), clazz);
                }
            }
            return true;
        }catch (Exception e) {
            return false;
        }
    }


    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
