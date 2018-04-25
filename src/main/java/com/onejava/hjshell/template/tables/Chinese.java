package com.onejava.hjshell.template.tables;

public class Chinese {
    public static int string_length(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }

        int chineseLength = chineseLength(value);

        valueLength = valueLength-(chineseLength/5);

        return valueLength;
    }

    private static int chineseLength(String value) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < value.length(); i++) {
            String temp = value.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 1;
            } else {
            }
        }
        return valueLength;
    }
}
