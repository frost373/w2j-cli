/*
 * iNamik Text Tables for Java
 * 
 * Copyright (C) 2016 David Farrell (DavidPFarrell@yahoo.com)
 *
 * Licensed under The MIT License (MIT), see LICENSE.txt
 */
package top.thinkin.wjcli.show.texttable.line;

import top.thinkin.wjcli.show.texttable.line.base.FunctionWithCharAndWidth;

public final class LeftAlign  extends FunctionWithCharAndWidth
{
    public static final LeftAlign INSTANCE = new LeftAlign();

    @Override
    public String apply(Character fill, Integer width, String line) {
        line = RightTruncate.INSTANCE.apply(width, line);
        line = RightPad     .INSTANCE.apply(fill, width, line);
        return line;
    }

}
