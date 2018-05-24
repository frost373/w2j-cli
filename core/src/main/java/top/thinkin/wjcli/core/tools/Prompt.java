package top.thinkin.wjcli.core.tools;

import top.thinkin.wjcli.core.Command;
import top.thinkin.wjcli.core.Constants;
import top.thinkin.wjcli.core.RootCommand;
import top.thinkin.wjcli.util.SmallJson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prompt {

    Map<String,List<Ary>> map= new HashMap<String,List<Ary>>();


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
       return SmallJson.getJson(map);
    }

    public  Prompt append(RootCommand rootCommand){
        String name = rootCommand.name;
        List<Ary> arys = new ArrayList<Ary>();

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
