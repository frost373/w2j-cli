package com.onejava.hjshell.template.table;

import com.onejava.hjshell.core.WjException;
import com.onejava.hjshell.template.tables.SimpleTable;

import java.util.ArrayList;
import java.util.List;

public class HtmlTable {

    private List<CellConfig> ChellConfigs = new ArrayList<CellConfig>();
    private List<List<Object>> rows = new ArrayList<List<Object>>();

    static class CellConfig {
        Integer width;
        Integer max;
        String name;

        CellConfig(Integer width,
                   Integer max,
                   String name) {
            this.width = width;
            this.max = max;
            this.name = name;
        }
    }

    public static HtmlTable create() {
        return new HtmlTable();
    }

    public HtmlTable config(Integer width,
                            Integer maxLineNumber,
                            String name) {
        CellConfig cellConfig = new CellConfig(width, maxLineNumber, name);
        ChellConfigs.add(cellConfig);
        return this;
    }

    public HtmlTable add(List<Object> list) throws WjException {
        if (list.size() != ChellConfigs.size()) {
            throw new WjException("Cell number is wrong!");
        }
        rows.add(list);
        return this;
    }


    public String buildTable() {
        SimpleTable s = SimpleTable.of();
        s.nextRow();

        for (CellConfig cell : ChellConfigs) {
             s.nextCell().addLine(cell.name);
        }

        StringBuffer sb = new StringBuffer();

        sb.append("<style type=\"text/css\">\n" +
                ".tg  {border-collapse:collapse;border-spacing:0;border-color:#ccc;}\n" +
                ".tg td{user-select: text;font-family:Arial, sans-serif;font-size:14px;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#fff;}\n" +
                ".tg th{user-select: text;font-family:Arial, sans-serif;font-size:14px;font-weight:normal;padding:10px 5px;border-style:solid;border-width:1px;overflow:hidden;word-break:normal;border-color:#ccc;color:#333;background-color:#f0f0f0;}\n" +
                ".tg .tg-rjr5{background-color:#343434;color:#34ff34;border-color:#ffffff}\n" +
                ".tg .tg-bk4q{font-weight:bold;background-color:#656565;color:#34ff34;border-color:#ffffff}\n" +
                "</style>" +
                "<table class=\"tg\">\n");
        sb.append("<tr>\n");
        for (CellConfig cell : ChellConfigs) {
            sb.append("<th class=\"tg-rjr5\">").append(cell.name).append("</th>\n");
        }
        sb.append("</tr>\n");
        for (List<Object> row : rows) {
            sb.append("<tr>\n");
            int i = 0;

            for (Object cell : row) {
                CellConfig cellConfig = ChellConfigs.get(i);
                String value = String.valueOf(cell);
                sb.append("<th class=\"tg-bk4q\">").append(value).append("</th>\n");
                i++;
            }
            sb.append("</tr>\n");
        }
        sb.append("</table>");
        return sb.toString();
    }
}
