/*
 * iNamik Text Tables for Java
 * 
 * Copyright (C) 2016 David Farrell (DavidPFarrell@yahoo.com)
 *
 * Licensed under The MIT License (MIT), see LICENSE.txt
 */
package top.thinkin.wjcli.show.texttable.grid;

import top.thinkin.wjcli.show.texttable.Cell;
import top.thinkin.wjcli.show.texttable.GridTable;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class Util
{
    public static void print(GridTable g) {
        print(g, System.out);
    }

    public static void print(GridTable g, OutputStream out) {
        print(g, new PrintWriter(out));
    }

    public static void print(GridTable g, Writer out) {
        print(g, new PrintWriter(out));
    }

    public static void print(GridTable g, PrintWriter out) {
        // Apply final padding to ensure grid prints properly
        //
        g = g
            .apply(Cell.Functions.TOP_ALIGN)
            .apply(Cell.Functions.LEFT_ALIGN)
            ;

        // Convert the grid to a cell
        // then iterate over the lines and print
        //
        for (String line: g.toCell()) {
            out.println(line);
        }
    }

    public static String asString(GridTable g) {
        StringWriter out = new StringWriter();
        print(g, out);
        return out.toString();
    }

}
