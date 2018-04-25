import com.onejava.hjshell.core.*;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by frost on 18/1/11.
 */
@HJRoot(name="hello",help = "hello world")
public class HelloTest {
@HJCommand(name = "hello",help = "do some thing")
    public String get(
            @HJValue(name="id",help = "用户的ID")
            String id,
            @HJValue(name="name",help = "用户的名字")
            String name
    ){
        return "hello";
    }

    @HJCommand(name = "Good",help = "do some thing")
    public String get2(
            @HJValue(name="fuck",help = "用户的ID")
                    String id,
            @HJValue(name="喵！！！",help = "用户的名字")
                    boolean name
    ){
        return "hello";
    }
    public static void main(String args[]) throws WjException, InvocationTargetException, IllegalAccessException {
        HelloTest test = new HelloTest();
        CommandManage commandManage = CommandManage.create().add(test);
        System.out.println(commandManage.getPromptJson());
        System.out.println(commandManage.handleCommand("hello hello -id 123 -name miao"));

        //commandManage.
    }
}
