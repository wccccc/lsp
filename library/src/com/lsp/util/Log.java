package com.lsp.util;

public class Log {
    private static final boolean LOG_ENABLE=true;
    public static void d(String tag,String text){
        if(LOG_ENABLE){
            System.out.println(tag+"---->"+text);
        }
    }
    public static void e(String tag,String text){
        if(LOG_ENABLE){
            System.err.println(tag+"---->"+text);
        }
    }
}
