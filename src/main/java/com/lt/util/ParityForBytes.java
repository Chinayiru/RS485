package com.lt.util;

/**
 * @aythor yi
 * @data 2023/2/14 itime
 * @Description
 */
public class ParityForBytes {
    public static byte[] setParityForBytes(byte[] data, int start, int end) {
        for (int i = start; i < end; i++) {
            int numberOfOnes = 0;
            for (int j = 0; j < 8; j++) {
                if (((data[i] >> j) & 1) == 1) {
                    numberOfOnes++;
                }
            }
            if (numberOfOnes % 2 == 0) {
                data[i] = (byte) (data[i] | (1 << 7));
            } else {
                data[i] = (byte) (data[i] & ~(1 << 7));
            }
        }
        return data;
    }
}
