package com.feisher.langlib.util;

/**
 * Created by george.yang on 2016-4-27.
 */
public class StringUtil {
    public static int string2int (String str) {
        return string2int(str,0);
    }
    public static int string2int (String str,int def) {
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
        }
        return def;
    }
}
