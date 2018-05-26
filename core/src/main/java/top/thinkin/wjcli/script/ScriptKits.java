package top.thinkin.wjcli.script;

import top.thinkin.wjcli.util.FileUtil;
import top.thinkin.wjcli.util.ScanTool;
import top.thinkin.wjcli.util.StrUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptKits {
    public static final Map<String, String> SCRIPTS = new HashMap<String, String>();
    public final static String PATH = "/script";
    public final static List<String> BUILD_IN_SCRIPTS = Arrays.asList(
            "loop_cli.script"
    );

    public static void init() throws Exception {
        for (String script : BUILD_IN_SCRIPTS) {
            String value = FileUtil.readResourceToString(PATH + "/" + script, ScriptKits.class);
            String key = script.substring(0, script.lastIndexOf("."));
            SCRIPTS.put(key, value);
        }


        String url = FileUtil.getClassPath();
        List<File> files = ScanTool.getFiles(url, "/script", true, ".script");
        for (File file : files) {
            String fileName = file.getName();
            String key = fileName.substring(0, fileName.lastIndexOf("."));
            SCRIPTS.put(key, FileUtil.readToString(file));
        }
    }

    public static String LOOP_CLI(String cli, String stopPrefix, Long interval) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cli", cli);
        map.put("stop_prefix", stopPrefix);
        map.put("interval", interval);
        return getScript("loop_cli", map);
    }

    public static <T> String getScript(String key, Map<String, T> map) {
        String temp = SCRIPTS.get(key);
        if (StrUtil.isEmpty(temp)) {
            return "";
        }
        return StrUtil.format(temp, map);
    }
}
