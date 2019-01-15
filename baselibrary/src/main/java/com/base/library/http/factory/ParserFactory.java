package com.base.library.http.factory;


import com.base.library.utils.ReflexUtil;

/**
 * com.lx.baselibrary.http.factory
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/21.
 */

public class ParserFactory implements IParserFactory {

    @Override
    public Object createParser(Class parserClass) {
        return ReflexUtil.getObject(parserClass);
    }
}
