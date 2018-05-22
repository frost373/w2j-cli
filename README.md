# W2J-CLI

JavaWeb Command-line Framework, help you easily build a command line JavaWeb System.  
It can be easily combined with any Java framework , and no other dependency.Even you don't need create a HTML page.



## Why need it
- Some functions are provided to professional and do not want to invest in developing front-end.
- Quick development of a simple management system
-  provided some tools by whe Web
- Quick,simple,fun and so on



## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

JDK 1.6+ 

## Some examples
create a class and write annotations for root command,commands,parameters

```
@HJRoot(name="task",help = "task related operation")
public class TaskTest {
    @HJCommand(name = "list",help = "get the list of task")
    public String list(
            @HJValue(name="start",help = "start time. example：2017-12-1")
                    String start,
            @HJValue(name="end",help = "end time. example：2018-12-1")
                    String end
    ) throws WjException {
        TextTable table =  TextTable.create();
        table.config("id");
        table.config("name");
        table.config("status");
        table.config("createTime");

        table.add(Arrays.asList("1","task-1","open","2017-12-1"));
        table.add(Arrays.asList("2","task-2","open","2017-12-2" ));

        return View.text(table.buildTable());
    }

    @HJCommand(name = "stop",ask = true,help = "stop the task")
    public String close(
            @HJValue(name="id",help = "the task id",req = true)
                    String id,
            @HJContext()
                    Context context

    ){
        return View.text("task is closed");
    }
}
```
#### Base
Base input and output,Text-table, Confirm,Auto-Complete

![image](https://raw.githubusercontent.com/wiki/frost373/w2j-cli/img/base.gif)


#### Help
Based on annotations, W2J-CLI could automatically generate the help documents
![image](https://raw.githubusercontent.com/wiki/frost373/w2j-cli/img/help.gif)


#### Login
 W2J-CLI have provided a built-in login module.

```
public class YesLogin implements WJLogin<Context> {

    public final static String AUTH = "LX2F8rdCA2wKel9yR42";

    public  String login(String root, String pass, Context context) {
        if("root".equals(root)&&"pass".equals(pass)){
            return View.OK(AUTH);
        }else{
            return View.error("error");
        }
    }

    public  boolean filter(String auth, Context context) {

        if(AUTH.equals(auth)){
            return true;
        }else{
            return false;
        }
    }
}
```
![image](https://raw.githubusercontent.com/wiki/frost373/w2j-cli/img/login.gif)

#### Animation


#### Script








## Authors

* **Dong Bin** - *Initial work* - [w2j-cli](https://github.com/frost373/w2j-cli.git)

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Hat tip to anyone who's code was used
* Inspiration
* etc
