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

    public static String flowStart(Class<?> clazz,String prompt,String nextNode,String say){

        if(!clazz.isAnnotationPresent(HJRoot.class)){
            new WjException("clazz is wrong");
        }
        HJRoot root = clazz.getAnnotation(HJRoot.class);
        String rootCmd = root.name();

        String head =  StrUtil.padEnd("flowstart",20,' ');
        if(StrUtil.isEmpty(prompt)){
            prompt = "";
        }
        return head+StrUtil.join(" #!*!# ",rootCmd,prompt,nextNode,say);
    }

    public static String flowNext(String nextNode,String say){
        String head =  StrUtil.padEnd("flownext",20,' ');

        return head+ StrUtil.join(" #!*!# ",nextNode,say);
    }

    public static String flowOver(String say){
        String head =  StrUtil.padEnd("flowover",20,' ');
        return head+ StrUtil.join(" #!*!# ",say);
    }
}
