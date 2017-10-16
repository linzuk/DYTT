package com.bzh.common.utils;

/**
 * Created by linzuk on 2017/10/16.
 */

public class UrlKit {

    private static boolean isWhitespace(int c){
        return c == ' ' || c == '\t' || c == '\n' || c == '\f' || c == '\r';
    }

    private static boolean isBlank(String string) {
        if (string == null || string.length() == 0)
            return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!isWhitespace(string.codePointAt(i)))
                return false;
        }
        return true;
    }

    public static String decodeUrl(String encodeUrl) {
        if (isBlank(encodeUrl)) {
            return encodeUrl;
        } else {
            return AesKit.decryptAES(encodeUrl.substring("thunder://".length()));
        }
    }

}
