package com.onejava.hjshell.core;

public class WjException  extends Exception{

    public WjException(String message)
    {
        super(message);
    }

    public static WjException  create(String message, Object... args){
        String[] strs = new String[args.length];

        for(int i=0;i<args.length;i++){
            strs[i] = String.valueOf(args[i]);
        }

        String m = String.format(message,strs);

        return new WjException(m);
    }
}
