package com.lt.SerialPport;

import com.lt.util.Hex;
import com.lt.util.Multithreading;
import com.lt.util.ReadFromPort;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.BlockingQueue;

import static com.lt.util.blockingQueue.getMsgQueue;

/**
 * @aythor yi
 * @data 2023/2/10  10:03:48
 * @Description
 */
public class SerialPortMonitor implements SerialPortEventListener {
    // 检测系统中可用的通讯端口类
    static CommPortIdentifier portId;
    // 枚举类型
    static Enumeration<?> portList;
    // RS485串口
    static SerialPort serialPort;
    // 输入输出流
    static InputStream inputStream;
    static OutputStream outputStream;
    static String appName = "";
    //    延迟时间
    static int timeout = 1000;
    //    波比率
    static int Baudrate = 9600;
    //    数据位
    static int dataBits = SerialPort.DATABITS_8;
    //    停止位
    static int stopBits = SerialPort.STOPBITS_1;
    //    奇偶校验
    static int parity = SerialPort.PARITY_NONE;


    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                readSerialData();
                break;
            default:
                break;
        }
    }

    private void readSerialData() {
        byte[] readBuffer;
        int availableBytes;
        BlockingQueue<String> msgQueue = getMsgQueue();
        try {
            availableBytes = inputStream.available();
            Hex hex = new Hex();
            ReadFromPort readFromPort = new ReadFromPort();
            while (availableBytes > 0) {
                readBuffer = readFromPort.readFromPort(serialPort);
                String needData = hex.printHexString(readBuffer);
                System.out.println(new Date() + "真实收到的数据为：-----" + needData);
                byte[] data = needData.getBytes("gbk");
                System.out.println("收到字节数：" + data.length);
                System.out.print("输出要发送的数据:");
                availableBytes = inputStream.available();
                msgQueue.add(needData);
            }
        } catch (IOException ignored) {
        }
    }

    // 初始化串口
    public int init() {
        // 获取系统中所有的通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        // 循环通讯端口
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            System.out.println("设备类型：---->" + portId.getPortType());
            System.out.println("设备名称：---->" + portId.getName());
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL && portId.getName().startsWith(appName)) {
                try {
                    serialPort = (SerialPort) portId.open(portId.getName(), timeout);
                    inputStream = serialPort.getInputStream();
                    outputStream = serialPort.getOutputStream();
                    serialPort.addEventListener(this);
                    serialPort.notifyOnDataAvailable(true);
                    serialPort.setSerialPortParams(Baudrate, dataBits, stopBits, parity);
                    return 1;
                } catch (PortInUseException | IOException | TooManyListenersException | UnsupportedCommOperationException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        }
        return 0;
    }

    public void sendData(byte[] data) {
        try {
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        SerialPortMonitor serialPortMonitor = new SerialPortMonitor();
        BlockingQueue<String> msgQueue = getMsgQueue();
        Multithreading multithreading = new Multithreading();
        multithreading.start();
        int initResult = serialPortMonitor.init();
        if (initResult == 1) {
            try (Scanner sc = new Scanner(System.in)) {

                System.out.print("输出要发出的数据: ");
                int number = sc.nextInt();
                byte[] data1 = new byte[number];
                System.out.println("输入字节组：");
                while (true) {
                    String input = sc.nextLine();
                    if (input.equals("exit|")) {
                        break;
                    }
                    byte[] data = input.getBytes("gbk");
                    System.out.println("发出字节数：" + data.length);
                    outputStream.write(data, 0, data.length);
                    System.out.print("输出要发出的数据: ");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (serialPort != null) {
                    serialPort.close();
                }
            }
        } else {
            return;
        }
    }

}
