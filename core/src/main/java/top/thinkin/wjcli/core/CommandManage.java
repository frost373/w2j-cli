package top.thinkin.wjcli.core;

import top.thinkin.wjcli.core.login.WJLogin;
import top.thinkin.wjcli.core.tools.Prompt;
import top.thinkin.wjcli.script.ScriptKits;
import top.thinkin.wjcli.util.StrUtil;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by frost on 18/1/12.
 * 命令管理器
 */
public class CommandManage{
    private Map<String,RootCommand> map = new HashMap<String, RootCommand>();

    private String promptJson;

    private boolean needLogin = false;

    private WJLogin wjLogin;

    private CommandManage(){

    }

    public boolean needLogin(){
        return needLogin;
    }

    public void init() throws Exception {
        ScriptKits.init();
    }

    public static CommandManage config() throws Exception {
        CommandManage commandManage = new CommandManage();
        commandManage.init();
        return commandManage;
    }

    public CommandManage setLogin(WJLogin wjLogin) throws WjException {
        this.wjLogin = wjLogin;
        needLogin = true;
        return this;
    }

    public CommandManage add(Object object) throws WjException {
        RootCommand rootCommand =  RootCommand.create(object);
        if(rootCommand == null){
            return this;
        }
        map.put(rootCommand.name,rootCommand);
        return this;
    }

    public CommandManage add(List<Object> objects) throws WjException {
        for(Object object:objects){
            RootCommand rootCommand =  RootCommand.create(object);
            if(rootCommand == null){
                continue;
            }
            map.put(rootCommand.name,rootCommand);
        }
        return this;
    }


    public String getHelp() throws WjException {

        return View.HTML(Helper.getTableHelp(map));
    }
    public String getHelp(String baseCommandStr) throws WjException {
        baseCommandStr = baseCommandStr.substring(Constants.HELP_CLI.length(),baseCommandStr.length());
        baseCommandStr = baseCommandStr.trim();
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
        }

        RootCommand rootCommand = map.get(rootCommandStr);
        if(rootCommand == null){
            return View.error("root command is not exit");
        }
        Command command = rootCommand.getCommand(commandStr);
        if(command == null){
            return View.HTML(Helper.getTableHelp(rootCommand));
        }

        return View.HTML(Helper.getTableHelp(command));
    }


    public String getPromptJson(){
        Prompt prompt = Prompt.create();
        for (RootCommand value : map.values()) {
            prompt.append(value);
        }
        promptJson = prompt.getJson();
        return promptJson;
    }

    public <T> String login(String baseCommandStr,T context) throws Exception{
        String command = StrUtil.removePrefixIgnoreCase(baseCommandStr, Constants.LOGIN_CLI);
        List<String> slices =  Arrays.asList(command.split("\\s+"));
        Map<String, String> map =  getArguments(slices);
        return  wjLogin.login(map.get("login"),map.get("pass"),context);
    }

    public <T> String  handleCommand(String baseCommandStr,T context,String auth) throws Exception {

        //baseCommandStr=baseCommandStr.replaceAll("\\s+"," ");
        boolean ask = false;

        if(baseCommandStr.startsWith(Constants.LOGIN_CLI)){
            return this.login(baseCommandStr,context);
        }

        if(needLogin && !wjLogin.filter(auth,context)){
            return View.out();
        }

        if(StrUtil.EMPTY.equals(baseCommandStr)){
            return "";
        }
        if(Constants.INFO_CLI.equals(baseCommandStr)){
           return this.getPromptJson();
        }

        if(Constants.HELP_CLI.equals(baseCommandStr)){
            return this.getHelp();
        }



        if(baseCommandStr.startsWith(Constants.HELP_CLI)){
            return this.getHelp(baseCommandStr);
        }

        if(baseCommandStr.startsWith(Constants.ASK_CLI)){
            baseCommandStr = StrUtil.removePrefixIgnoreCase(baseCommandStr, Constants.ASK_CLI);
            ask = true;
        }

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
        if(rootCommand == null){
            return View.error("root command is not exit");
        }
        Command command = rootCommand.getCommand(commandStr);
        if(command == null){
            return View.error("command is not exit");
        }

        if(command.ask && !ask){
            return View.ask(baseCommandStr);
        }

        String result;

        try {
            List<Object> values =  getCommand(command,pramas,context);
            Method method = command.meotd;
            Object[] arrays= (Object[]) values.toArray();
            result= String.valueOf(method.invoke(rootCommand.object,arrays));
        } catch (WjException e) {
            result = View.error(e.getMessage());
        }catch (Exception e) {
            throw e;
        }

        return result;
    }

    public <T> List<Object> getCommand(Command command1, List<String> slices, T context) throws WjException {


        Map<String, String> arguments = getArguments(slices);
        List<Object> values = new ArrayList<Object>();
        Map<String,Command.Value> pramas =  command1.values;
        for(Command.Value value:pramas.values()){

            if(Constants.CONTEXT.equals(value.name)){
                values.add(context);
                continue;
            }

            if(!arguments.containsKey(value.name)){
                //如果值不存在，boolean类型为false，其他类型为null
                if(value.req){//必须要有
                    throw  WjException.create("The parameter {} is necessary!",value.name);
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
                        values.add( Boolean.parseBoolean(arguments.get(value.name)));
                    } catch (Exception e) {
                        throw WjException.create("The parameter {} must be Boolean!",value.name);
                    }
                }if(value.clazz.equals(String.class)){//String类型
                    try {
                        values.add( arguments.get(value.name));
                    } catch (Exception e) {
                        throw WjException.create("The parameter {} must be Boolean!",value.name);
                    }
                }
                else if(value.clazz.equals(Integer.class)){//整数类型
                    try {
                        values.add( Integer.parseInt(arguments.get(value.name)));
                    } catch (Exception e) {
                        throw WjException.create("The parameter {} must be Integer!",value.name);
                    }
                }else if(value.clazz.equals(Long.class)){//Long类型
                    try {
                        values.add( Long.parseLong(arguments.get(value.name)));
                    } catch (Exception e) {
                        throw WjException.create("The parameter {} must be Long!",value.name);
                    }
                }
            }


        }

        return values;
    }

    private Map<String, String> getArguments(List<String> slices) {
        Map<String,String> arguments = new HashMap<String,String>();

        for(int i=0;i<slices.size();i++){
            String key = slices.get(i);
            if(key.startsWith("-")){
                if(slices.size()==1){
                    arguments.put(key,null);
                }else{
                    if(i<(slices.size()-1)){
                        String nextKey = slices.get(i+1);
                        if(!nextKey.startsWith("-")){
                            i++;
                            arguments.put(key.substring(1,key.length()),nextKey);
                        }
                    }
                }
            }
        }
        return arguments;
    }

    public Command getCommand(String command, List<String> pramas) {
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
