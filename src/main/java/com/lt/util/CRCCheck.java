package com.lt.util;

import java.util.zip.CRC32;

/**
 * @aythor yi
 * @data 2023/2/13 itime
 * @Description
 */
public class CRCCheck {
    public static void main(String[] args) {
        byte[] data = {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C};
        int crc = getCRC(data);
        System.out.println("CRC: " + Integer.toHexString(crc));
    }

    public static int getCRC(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return (int) crc32.getValue();
    }
}
