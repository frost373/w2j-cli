package top.thinkin.wjcli.util;

import top.thinkin.wjcli.core.tools.Prompt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by frost on 18/1/12.
 */
public class SmallJson {

    public static  String  ROOT_TEMP = "\"{$root}\":[{$args}]";
    public static  String  ARG_TEMP = "{\"name\":\"{$name}\",\"options\":[{$options}]}";


    public static String getJson(Map<String,List<Prompt.Ary>> map){
        String[] rootArray = new String[map.size()];
        int i=0;
        for (Map.Entry<String,List<Prompt.Ary>> entry : map.entrySet()) {
            Map root_map = new HashMap();
            root_map.put("$root",entry.getKey());
            root_map.put("$args",getArgs(entry.getValue()));

            String root = StrUtil.format(ROOT_TEMP,root_map);
            rootArray[i] = root;
            i++;
        }
        return StrUtil.join("","{",StrUtil.join(rootArray,","),"}");
    }

    private static String getArgs(List<Prompt.Ary> aries){
        String[] ariesArray = new String[aries.size()];
        int j = 0;
        for(Prompt.Ary ary:aries){
            Map root_map = new HashMap();
            root_map.put("$name",ary.name);
            List<String> options = ary.options;
            String[] optionArray = new String[options.size()];
            for(int i =0;i<options.size();i++){
                optionArray[i] =  StrUtil.join("","\"",options.get(i),"\"");
            }
            root_map.put("$options",StrUtil.join(optionArray,","));
            String str = StrUtil.format(ARG_TEMP,root_map);
            ariesArray[j] = str;
            j++;
        }
        return StrUtil.join(ariesArray,",");

    }
}
