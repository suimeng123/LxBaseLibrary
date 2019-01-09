package com.lx.baselibrary.http.factory;

import com.lx.baselibrary.http.parser.IParser;

/**
 * com.lx.baselibrary.http.factory
 * LxBaseLibrary
 * Created by lixiao2
 * 2018/12/21.
 */

public interface IParserFactory {
    Object createParser(Class parserClass);
}
