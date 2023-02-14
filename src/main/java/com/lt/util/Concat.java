package com.lt.util;

/**
 * 合并数组
 *
 * @param firstArray  第一个数组
 * @param secondArray 第二个数组
 * @return 合并后的数组
 */
public class Concat {

    public static byte[] concat(byte[] firstArray, byte[] secondArray) {
        if (firstArray == null || secondArray == null) {
            if (firstArray != null)
                return firstArray;
            return secondArray;
        }
        byte[] bytes = new byte[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, bytes, 0, firstArray.length);
        System.arraycopy(secondArray, 0, bytes, firstArray.length, secondArray.length);
        return bytes;
    }
}
