package top.thinkin.wjcli.core;

import top.thinkin.wjcli.util.StrUtil;

public class View {
    public static String HTML(String text){
       String head =  StrUtil.padEnd("html",20,' ');
        return head+text;
    }

    public static String OK(String text){
        String head =  StrUtil.padEnd("ok",20,' ');
        return head+text;
    }

    public static String error(String text){
        String head =  StrUtil.padEnd("error",20,' ');
        return head+text;
    }

    public static String text(String text){
        String head =  StrUtil.padEnd("text",20,' ');
        return head+text;
    }

    public static String script(String text){
        String head =  StrUtil.padEnd("script",20,' ');
        return head+text;
    }


    public static String out(){
        String head =  StrUtil.padEnd("out",20,' ');
        return head;
    }

    public static String ask(String text){
        String head =  StrUtil.padEnd("ask",20,' ');
        return head+text;
    }
}
