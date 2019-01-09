package com.lx.baselibrary.http.parser;

/**
 * com.lx.baselibrary.http
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/20.
 * 结果解析器
 */

public interface IParser {
    Object getData();
    int getCode();
    String getMessage();
    boolean parser(String result, Class clazz);
}
