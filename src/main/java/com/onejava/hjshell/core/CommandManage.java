package com.onejava.hjshell.core;

import com.onejava.hjshell.core.tools.Prompt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frost on 18/1/12.
 * 命令管理器
 */
public class CommandManage {
    private Map<String,RootCommand> map = new HashMap<String, RootCommand>();

    private String promptJson;


    private CommandManage(){

    }

    public static CommandManage  create(){
        return new CommandManage();
    }

    public CommandManage add(Object object) throws WjException {
        RootCommand rootCommand =  RootCommand.create(object);
        if(rootCommand == null){
            return this;
        }
        map.put(rootCommand.name,rootCommand);
        return this;
    }


    public String getPromptJson(){
        Prompt prompt = Prompt.create();
        for (RootCommand value : map.values()) {
            prompt.append(value);
        }
        promptJson = prompt.getJson();
        return promptJson;
    }

    public String handleCommand(String baseCommandStr) throws InvocationTargetException, IllegalAccessException {
        List<String> pramas = new ArrayList<String>();

        String [] arr = baseCommandStr.split("\\s+");
        String rootCommandStr  = null;
        String commandStr  = null;
        for(int i=0;i<arr.length;i++){
            if(i==0){
                rootCommandStr = arr[i];
            }
            if(i == 1){
                commandStr = arr[i];
            }
            pramas.add(arr[i]);
        }


        RootCommand rootCommand = map.get(rootCommandStr);

        Command command = rootCommand.getCommand(commandStr);


        String result;

        try {
            List<Object> values =  getCommand(command,pramas);
            Method method = command.meotd;
            Object[] arrays= (Object[]) values.toArray();
            result= String.valueOf(method.invoke(rootCommand.object,arrays));
        } catch (WjException e) {
            result = e.getMessage();
        }

        return result;
    }

    public List<Object> getCommand(Command command1,List<String> slices) throws WjException {


        Map<String,String> arguments = new HashMap<String,String>();

        for(int i=0;i<slices.size();i++){
            String key = slices.get(i);
            if(slices.size()==1){
                arguments.put(key,null);
            }else{
                if(i<slices.size()){
                    String nextKey = slices.get(i);
                    if(nextKey.startsWith("-")){
                        nextKey = nextKey.substring(0,1);
                        i++;
                        arguments.put(key,nextKey);
                    }
                }
            }

        }
        List<Object> values = new ArrayList<Object>();
        Map<String,Command.Value> pramas =  command1.values;
        for(Command.Value value:pramas.values()){
            if(!arguments.containsKey(value.name)){
                //如果值不存在，boolean类型为false，其他类型为null
                if(value.req){//必须要有
                    throw  WjException.create("The parameter %s is necessary!",value.name);
                }else{
                    if(value.clazz.equals(boolean.class)){//布尔类型
                        values.add(false);
                    }else{
                        values.add(null);
                    }
                }
            }else{
                if(value.clazz.equals(boolean.class)){//布尔类型
                    values.add(true);
                }else
                if(value.clazz.equals(Boolean.class)){//布尔类型
                    try {
                        Boolean.parseBoolean(arguments.get(value));
                    } catch (Exception e) {
                        throw WjException.create("The parameter %s must be Boolean!",value.name);
                    }
                }else if(value.clazz.equals(Integer.class)){//整数类型
                    try {
                        Integer.parseInt(arguments.get(value));
                    } catch (Exception e) {
                        throw WjException.create("The parameter %s must be Integer!",value.name);
                    }
                }else if(value.clazz.equals(Long.class)){//Long类型
                    try {
                        Long.parseLong(arguments.get(value));
                    } catch (Exception e) {
                        throw WjException.create("The parameter %s must be Long!",value.name);
                    }
                }
            }


        }

        return values;
    }

    public Command getCommand(String command,List<String> pramas){
        String [] arr = command.split("\\s+");
        String rootCommandStr  = null;
        String commandStr  = null;
       // List<String> pramas = new ArrayList<String>();
        for(int i=0;i<arr.length;i++){
            if(i==0){
                rootCommandStr = arr[i];
            }
            if(i == 1){
                commandStr = arr[i];
            }
            pramas.add(arr[i]);
        }


        RootCommand rootCommand = map.get(rootCommandStr);
        if(rootCommand != null){
            Command command1 = rootCommand.getCommand(commandStr);
            return  command1;
        }
        return null;
    }




}
