package top.thinkin.wjcli.core;

import top.thinkin.wjcli.show.table.HtmlTable;

import java.util.*;

public class Helper {
    public static String  getTableHelp(Map<String,RootCommand> commandMap) throws WjException {
        //先转成ArrayList集合
        ArrayList<Map.Entry<String, RootCommand>> list =
                new ArrayList<Map.Entry<String, RootCommand>>(commandMap.entrySet());
        Collections.sort(list,new Comparator<Map.Entry<String, RootCommand>>() {
            public int compare(Map.Entry<String, RootCommand> o1, Map.Entry<String, RootCommand> o2) {
                RootCommand r1= o1.getValue();
                RootCommand r2= o2.getValue();

                return r1.name.compareToIgnoreCase(r2.name);
            }
        });
        HtmlTable table = HtmlTable.create().
                config("100px",null,"Executable")
                .config("200px",null,"Describe")
                .config("250px",null,"Commands");
        for(Map.Entry<String, RootCommand> entry :list){
            RootCommand rootCommand = entry.getValue();
            List<Object> cells = new ArrayList<Object>();
            cells.add(rootCommand.name);
            cells.add(rootCommand.help);
            if(!rootCommand.flow){
                cells.add(getCommands(rootCommand.getCommands()));
            }else {
                cells.add("");
            }
            table.add(cells);
        }

        return "<br>"+table.buildTable()+"Use 'help  [Executable]' get more information<br>";
    }

    public static String  getTableHelp(RootCommand rootCommand) throws WjException {
        HtmlTable table = HtmlTable.create().
                config("100px",null,"Command")
                .config("200px",null,"Describe")
                .config("250px",null,"Parameters");
        List<Command> commands  = rootCommand.getCommands();
        for(Command command : commands){
            List<Object> cells = new ArrayList<Object>();
            cells.add(command.command);
            cells.add(command.help);
            cells.add(getParameters(command.values));
            table.add(cells);
        }

        return "<br>"+table.buildTable()+"Use 'help "+ rootCommand.name+" [command]' get more information<br>";
    }


    public static String  getTableHelp(Command command) throws WjException {
        Map<String,Command.Value> values = command.values;

        HtmlTable table = HtmlTable.create().
                config("100px",null,"Parameter")
                .config("200px",null,"Required")
                .config("250px",null,"Describe");

        Iterator<Map.Entry<String, Command.Value>> iterator= values.entrySet().iterator();
        while(iterator.hasNext())
        {
            List<Object> cells = new ArrayList<Object>();
            Map.Entry<String, Command.Value> entry = iterator.next();
            Command.Value value = entry.getValue();

            if(Constants.CONTEXT.equals(value.name)){
                continue;
            }

            cells.add(entry.getKey());

            if(value.req){
                cells.add("True");
            }else{
                cells.add("False");
            }
            cells.add(value.help);

            table.add(cells);

        }
        return "<br>"+table.buildTable()+"<br>";
    }

    private static String getCommands(List<Command> commands){
        StringBuffer sb = new StringBuffer();
        for(Command command : commands){
            sb.append(command.command).append("--").append(command.help).append("<br><br>");
        }
        return sb.toString();
    }

    private static String getParameters( Map<String,Command.Value> values){
        StringBuffer sb = new StringBuffer();
        Iterator<Map.Entry<String, Command.Value>> iterator= values.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<String, Command.Value> entry = iterator.next();
            Command.Value value = entry.getValue();

            if(Constants.CONTEXT.equals(value.name)){
                continue;
            }
            sb.append(value.name).append("--").append(value.help).append("<br><br>");
        }
        return sb.toString();
    }

}



