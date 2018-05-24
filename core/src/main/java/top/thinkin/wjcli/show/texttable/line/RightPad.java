/*
 * iNamik Text Tables for Java
 * 
 * Copyright (C) 2016 David Farrell (DavidPFarrell@yahoo.com)
 *
 * Licensed under The MIT License (MIT), see LICENSE.txt
 */
package top.thinkin.wjcli.show.texttable.line;

import top.thinkin.wjcli.show.texttable.Chinese;
import top.thinkin.wjcli.show.texttable.line.base.FunctionWithCharAndWidth;

public final class RightPad extends FunctionWithCharAndWidth
{
    public static final RightPad INSTANCE = new RightPad();

    @Override
    public String apply(Character fill, Integer width, String line) {
        if (line.length() >= width) {
            return line;
        }
        StringBuilder sb = new StringBuilder(width);
        sb.append(line);



        int trueWidth = Chinese.string_length(line);
        while (trueWidth < width) {
            sb.append(fill);
            trueWidth++;
        }
        return sb.toString();
    }


}
