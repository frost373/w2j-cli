package com.onejava.hjshell.core;

import com.onejava.hjshell.util.StringUtil;

public class View {
    public static String HTML(String text){
       String head =  StringUtil.padEnd("html",20,' ');
        return head+text;
    }

    public static String OK(String text){
        String head =  StringUtil.padEnd("ok",20,' ');
        return head+text;
    }

    public static String text(String text){
        String head =  StringUtil.padEnd("text",20,' ');
        return head+text;
    }
}
