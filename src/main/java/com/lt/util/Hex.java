package com.lt.util;

import gnu.io.SerialPort;

import java.io.IOException;
import java.io.InputStream;

import static com.lt.util.Concat.concat;

/**
 * @aythor yi
 * @data 2023/2/10 itime
 * @Description
 */
public class Hex {
    //字符串转接符
    public String printHexString(byte[] readBuffer) {
        return bytesToHex(readBuffer).toUpperCase();
    }

//    优化
    public static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
