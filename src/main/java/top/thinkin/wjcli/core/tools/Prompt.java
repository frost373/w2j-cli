package top.thinkin.wjcli.core.tools;

import com.alibaba.fastjson.JSON;
import top.thinkin.wjcli.core.Command;
import top.thinkin.wjcli.core.Constants;
import top.thinkin.wjcli.core.RootCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prompt {

    //Map<String,List<Ary>> map= new HashMap<String,List<Ary>>();

    Map<String,Object> map= new HashMap<String,Object>();

    public static class Ary{
        public String name;
        public List<String> options =new ArrayList<String>();
    }
    public static class AryCommand{
        public List<Ary> args;
    }

    public static Prompt create(){
        return new Prompt();
    }

    public String getJson(){
        return JSON.toJSONString(map);
    }

    public  Prompt append(RootCommand rootCommand){
        String name = rootCommand.name;
        List<Ary> arys = new ArrayList<Ary>();
        Map<String,Object> objects= new HashMap<String,Object>();

        List<Command> list = rootCommand.getCommands();
        for(Command command :list){
            Ary ary = new Ary();
            ary.name = command.command;
            Map<String, Command.Value> map= command.values;
            for (String key : map.keySet()) {
                if(Constants.CONTEXT.equals(key)){
                   continue;
                }
                ary.options.add(key);
            }
            arys.add(ary);
        }

        map.put(name,arys);
        return this;
    }
}
