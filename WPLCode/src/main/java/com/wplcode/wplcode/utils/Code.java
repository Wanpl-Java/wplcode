package com.wplcode.wplcode.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Code implements com.wplcode.wplcode.utils.CodeInterface {

    @Override
    public String getResult() {
            ByteArrayOutputStream baoStream = new ByteArrayOutputStream(1024);
            PrintStream cacheStream = new PrintStream(baoStream);//临时输出
            PrintStream oldStream = System.out;//缓存系统输出
            System.setOut(cacheStream);
            System.setOut(oldStream);//还原到系统输出
            String result = baoStream.toString();//存放控制台输出的字符串
            return result;
    }
}
