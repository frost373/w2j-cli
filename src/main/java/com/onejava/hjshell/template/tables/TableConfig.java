package com.onejava.hjshell.template.tables;

import com.onejava.hjshell.core.WjException;
import com.onejava.hjshell.template.tables.grid.Border;
import com.onejava.hjshell.template.tables.grid.Util;
import com.onejava.hjshell.util.ArrayUtil;
import com.onejava.hjshell.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class TableConfig {

    private List<CellConfig> ChellConfigs = new ArrayList<CellConfig>();
    private List<List<Object>> rows = new ArrayList<List<Object>>();

    static class CellConfig {
        Integer width;
        Integer maxLineNumber;
        String name;

        CellConfig(Integer width,
                   Integer maxLineNumber,
                   String name) {
            this.width = width;
            this.maxLineNumber = maxLineNumber;
            this.name = name;
        }
    }

    public static TableConfig  create() {
        return new TableConfig();
    }

    public TableConfig config(Integer width,
                              Integer maxLineNumber,
                              String name) {
        CellConfig cellConfig = new CellConfig(width, maxLineNumber, name);
        ChellConfigs.add(cellConfig);
        return this;
    }

    public TableConfig add(List<Object> list) throws WjException {
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


        for (List<Object> row : rows) {
            s.nextRow();
            int i = 0;

            for (Object cell : row) {
                CellConfig cellConfig = ChellConfigs.get(i);
                String value = String.valueOf(cell);
                Integer width = cellConfig.width;
                Integer maxLineNumber = cellConfig.maxLineNumber;

                if (width != null && value.length() > width) {
                    String[] strs = StringUtil.split(value, width);
                    if (maxLineNumber != null && strs.length > maxLineNumber) {
                        strs = ArrayUtil.resize(strs, maxLineNumber);
                    }
                    s.nextCell().addLines(strs);
                } else {
                    s.nextCell().addLine(String.valueOf(cell));
                }
                i++;

            }
        }
        GridTable g = s.toGrid();
        g = Border.of(Border.Chars.of('+', '-', '|')).apply(g);
        return Util.asString(g);
    }
}
