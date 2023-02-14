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
public class ReadFromPort {
    /**
     * 从串口读取数据
     *
     * @param serialPort 当前已建立连接的SerialPort对象
     * @return 读取到的数据
     */
    public byte[] readFromPort(SerialPort serialPort) {
        byte[] bytes = {};
        try (InputStream in = serialPort.getInputStream()) {
            // 缓冲区大小为一个字节
            byte[] readBuffer = new byte[1];
            int bytesNum = in.read(readBuffer);
            while (bytesNum > 0) {
                bytes = concat(bytes, readBuffer);
                bytesNum = in.read(readBuffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}
