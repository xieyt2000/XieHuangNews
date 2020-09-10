package com.java.xieyuntong.backend;

public class BasicFunction {

    private static boolean isHalfWidth(char c) {
        return c <= '\u00FF' || '\uFF61' <= c && c <= '\uFFDC' || '\uFFE8' <= c && c <= '\uFFEE';
    }

    private static int countHalfWidthLen(String str) {
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isHalfWidth(str.charAt(i)))
                res += 1;
            else res += 2;
        }
        return res;
    }

    public static String getPrefixAbs(String str, int halfLen) {
        if (countHalfWidthLen(str) <= halfLen)
            return str;
        StringBuilder sb = new StringBuilder();
        int curHalLen = 0;
        for (int i = 0; i < str.length(); i++) {
            if (isHalfWidth(str.charAt(i)))
                curHalLen += 1;
            else curHalLen += 2;
            if (curHalLen >= halfLen - 3)
                break;
            sb.append(str.charAt(i));
        }
        sb.append("...");
        return sb.toString();
    }
}
