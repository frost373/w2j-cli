package top.thinkin.wjcli.core;

import top.thinkin.wjcli.core.tools.ObjectTools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frost on 18/1/13.
 */
public class RootCommand {
    private  Map<String,Command> commandMap = new LinkedHashMap<String, Command>();


    public String name;

    public String help;

    public Object object;

    protected RootCommand(Object object,String name,String help){
        this.object =object;
        this.name = name;
        this.help = help;
    }

    public void setCommand(Command command){

        if(command == null){
            return;
        }
        commandMap.put(command.command,command);
    }

    public List<Command> getCommands(){
        List<Command> list = new ArrayList<Command>();
        for (String key : commandMap.keySet()) {
            list.add(commandMap.get(key));
        }
        return list;
    }

    public Command getCommand(String command){
       return commandMap.get(command);
    }

    public static RootCommand init(Object obj, String name, String help) {
        return new RootCommand(obj,name,help);

    }


    public static RootCommand create(Object obj) throws WjException {
        Class<?> clazz = obj.getClass();
        if(!clazz.isAnnotationPresent(HJRoot.class)){
            return null;
        }
        HJRoot root = clazz.getAnnotation(HJRoot.class);
        RootCommand rootCommand =  RootCommand.init(obj,root.name(),root.help());
        Method[]  methods =  ObjectTools.getMethodsDirectly(obj.getClass(),false);
        for(int i=0;i<methods.length;i++){
            Method method =  methods[i];
            if(!method.isAnnotationPresent(HJCommand.class)){
                continue;
            }

            HJCommand hjCommand =  method.getAnnotation(HJCommand.class);
            Command command = new Command();
            command.method = method;
            command.command = hjCommand.name();
            command.help = hjCommand.help();
            command.ask = hjCommand.ask();
            Annotation[][] annotations = method.getParameterAnnotations();

            Class<?>[] parameterTypes = method.getParameterTypes();

            if(annotations!=null){
                for(int j=0;j<annotations.length;j++){
                    for(int k=0;k<annotations[j].length;k++){
                        Annotation annotation   = annotations[j][k];
                        if((annotation instanceof HJValue)){
                            HJValue hjvalue = (HJValue) annotation;
                            Command.Value value =  new Command.Value();
                            value.name = hjvalue.name();
                            value.help = hjvalue.help();
                            value.req = hjvalue.req();
                            command.values.put(value.name,value);
                        }

                        if((annotation instanceof HJContext)){
                            Command.Value value =  new Command.Value();
                            value.name = Constants.CONTEXT;
                            command.values.put(value.name,value);
                        }

                    }
                }
            }

            if(command.values.size()!=parameterTypes.length){
                throw new WjException("所有参数都必须有注解");
            }
            int k=0;
            for(Command.Value value:command.values.values()){
                value.clazz = parameterTypes[k];
                k++;
            }

            rootCommand.setCommand(command);
        }
        return  rootCommand;
    }
}
