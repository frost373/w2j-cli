import com.onejava.hjshell.template.table.HtmlTable;
import com.onejava.hjshell.template.tables.GridTable;
import com.onejava.hjshell.template.tables.SimpleTable;
import com.onejava.hjshell.template.tables.TableConfig;
import com.onejava.hjshell.template.tables.grid.Border;
import com.onejava.hjshell.template.tables.grid.Util;
import com.onejava.hjshell.core.WjException;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static com.onejava.hjshell.template.tables.Cell.Functions.*;

public class Tables {

    public static void main2(String args[]) throws WjException, InvocationTargetException, IllegalAccessException {

        TableConfig tableConfig =TableConfig.create().
                 config(20,null,"name")
                .config(null,null,"age")
                .add(Arrays.<Object>asList("同时，Hutool还提供了BeanUtil.toBean方法，此处并不是传Bean对象，而是Bean类，Hutool会自动调用默认构造方法创建对象",12))
                .add(Arrays.<Object>asList("二狗",17));
        System.out.println(tableConfig.buildTable());

    }


    public static void main(String args[]) throws WjException, InvocationTargetException, IllegalAccessException {

        HtmlTable tableConfig =HtmlTable.create().
                 config(null,null,"name")
                .config(null,null,"age")
                .add(Arrays.<Object>asList("同时，Hutool还提供了BeanUtil.toBean方法，此处并不是传Bean对象，而是Bean类，Hutool会自动调用默认构造方法创建对象",12))
                .add(Arrays.<Object>asList("二狗",17));
        System.out.println(tableConfig.buildTable());

    }
}
