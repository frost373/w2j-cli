package top.thinkin.wjcli.core;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by frost on 18/1/11.
 */
public class Command {
    /**
     * 根命令
     */
    public String rootCommand;

    /**
     * 命令
     */
    public String command;


    /**
     * 帮助
     */
    public String help;

    /**
     * 方法缓存
     */
    public Method method;

    public boolean ask;

    public CommonType commonType;


    public Map<String,Value> values = new LinkedHashMap<String,Value>();

    public static class Value{
        /**
         *命令行名称
         */
        public String name;


        /**
         *命令行名称
         */
        public Class clazz;

        /**
         * 方法参数名称
         */
        public String param;

        /**
         * 是否必须
         */
        public boolean req = false;

        /**
         * 帮助文档
         */
        public String help;

        public Command create(Class clazz){

            return null;
        }
    }
}
