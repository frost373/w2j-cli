package top.thinkin.wjcli.core;

import top.thinkin.wjcli.util.FileUtil;
import top.thinkin.wjcli.util.StrUtil;

import java.util.HashMap;

public class HTMLConfig {
    protected boolean needLogin = false;

    protected String HTML;
    protected String HTML_URL = "index.html";

    protected String POST_URL = "";
    protected String BANNER2 ="\\\\";
    protected String BANNER =
                    "┏┳┳┓┏┉┓　　┏┓　　　┏┉┉┓┏┓┏┓┏┉┉┓┏┓　┏┓　\\n" +
                    "┋┋┋┋┣┉┋┏┓┋┋┏┉┓┋┉┉┫┋┗┛┋┋　┉┫┋┋　┋┋　\\n" +
                    "┋　　┋┋┉┫┋┗┛┋┗┉┛┣┉┉┋┋┏┓┋┋　┉┫┋┗┓┋┗┓\\n" +
                    "┗┉┉┛┗┉┛┗┉┉┛　　　┗┉┉┛┗┛┗┛┗┉┉┛┗┉┛┗┉┛\\n" +
                    "JavaWeb Command-line Framework,Simple and easy to use\\n" +
                    "         Version:Alpha-0.0.1  Designed by DB.Frost\\n" +
                    " ╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯╰≈╯";

    protected String URL_css = "https://cdn.bootcss.com/jquery.terminal/1.11.3/css/jquery.terminal.css";
    protected String URL_jquery = "https://cdn.bootcss.com/jquery/1.7.1/jquery.min.js";
    protected String URL_mousewheel = "https://cdn.bootcss.com/jquery-mousewheel/3.1.2/jquery.mousewheel.min.js";
    protected String URL_terminal = "https://cdn.bootcss.com/jquery.terminal/1.11.3/js/jquery.terminal.min.js";
    protected String COLOR;
    protected String BACKGROUND;

    public HTMLConfig setCss(String url) {
        this.URL_css = url;
        return this;
    }

    public HTMLConfig needLogin(boolean needLogin) {
        this.needLogin = needLogin;
        return this;
    }

    public HTMLConfig setPostUrl(String url) {
        this.POST_URL = url;
        return this;
    }

    public HTMLConfig setjquery(String url) {
        this.URL_jquery = url;
        return this;
    }


    public HTMLConfig setM_js(String url) {
        this.URL_mousewheel = url;
        return this;
    }

    public HTMLConfig setT_js(String url) {
        this.URL_terminal = url;
        return this;
    }

    public static HTMLConfig cteate() {
        return new HTMLConfig();
    }

    public HTMLConfig build() throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("POST_URL", POST_URL);
        map.put("BANNER", BANNER);
        map.put("URL_css", URL_css);
        map.put("URL_jquery", URL_jquery);
        map.put("URL_mousewheel", URL_mousewheel);
        map.put("URL_terminal", URL_terminal);
        if(!needLogin){
            map.put("LOGIN_T", "/*");
            map.put("LOGIN_F", "*/");
        }else{
            map.put("LOGIN_T", "");
            map.put("LOGIN_F", "");
        }
        String url = FileUtil.getAbsolutePath(HTML_URL, null);
        String temp = FileUtil.readToString(url);
        HTML = StrUtil.format(temp, map);
        return this;
    }

    public String html() {
        return HTML;
    }

    public static void main(String[] args) throws Exception {
        String url = FileUtil.getAbsolutePath("index.html", null);
        String temp = FileUtil.readToString(url);
        System.out.println(temp);
    }

}
