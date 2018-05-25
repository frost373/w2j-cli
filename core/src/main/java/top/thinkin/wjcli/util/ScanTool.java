package top.thinkin.wjcli.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ScanTool {

    public static List<File> getFiles(String rootPath, String path, Boolean subFolder, String suffix) throws Exception {
        List<File> files = new ArrayList<File>();
        if (path == null || "".equals(path)) {
            return files;
        }

        File file = new File(rootPath + "/" + path);
        if (file.exists()) {
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                if (subFile.isDirectory() && subFolder) {
                    List<File> list = getFiles(rootPath, path + "/" + subFile.getName(), subFolder, suffix);
                    files.addAll(list);
                } else {
                    String classname = subFile.getName();
                    if (classname.endsWith(suffix)) {
                        files.add(subFile);
                    }
                }
            }
        }
        return files;
    }


}
