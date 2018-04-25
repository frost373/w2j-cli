package com.onejava.hjshell.util;

public class StringUtil {

    public static final String EMPTY = "";

    /**
     * 补充字符串以满足最小长度 StrUtil.padEnd("1", 3, '0');//"100"
     *
     * @param str 字符串，如果为<code>null</code>，按照空串处理
     * @param minLength 最小长度
     * @param padChar 补充的字符
     * @return 补充后的字符串
     */
    public static String padEnd(CharSequence str, int minLength, char padChar) {
        if (null == str) {
            str = EMPTY;
        } else if (str.length() >= minLength) {
            return str.toString();
        }

        return str.toString().concat(repeat(padChar, minLength - str.length()));
    }

    /**
     * 重复某个字符
     *
     * @param c 被重复的字符
     * @param count 重复的数目，如果小于等于0则返回""
     * @return 重复字符字符串
     */
    public static String repeat(char c, int count) {
        if (count <= 0) {
            return EMPTY;
        }

        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }
    /**
     * 根据给定长度，将给定字符串截取为多个部分
     *
     * @param str 字符串
     * @param len 每一个小节的长度
     * @return 截取后的字符串数组
     */
    private static String[] splitByLength(String str, int len) {
        int partCount = str.length() / len;
        int lastPartCount = str.length() % len;
        int fixPart = 0;
        if (lastPartCount != 0) {
            fixPart = 1;
        }

        final String[] strs = new String[partCount + fixPart];
        for (int i = 0; i < partCount + fixPart; i++) {
            if (i == partCount + fixPart - 1 && lastPartCount != 0) {
                strs[i] = str.substring(i * len, i * len + lastPartCount);
            } else {
                strs[i] = str.substring(i * len, i * len + len);
            }
        }
        return strs;
    }


    /**
     * 根据给定长度，将给定字符串截取为多个部分
     *
     * @param str 字符串
     * @param len 每一个小节的长度
     * @return 截取后的字符串数组
     */
    public static String[] split(CharSequence str, int len) {
        if (null == str) {
            return new String[] {};
        }
        return splitByLength(str.toString(), len);
    }
}
