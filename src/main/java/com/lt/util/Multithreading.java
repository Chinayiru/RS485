package com.lt.util;

import java.util.concurrent.BlockingQueue;
import static com.lt.util.blockingQueue.getMsgQueue;

/**
 * @aythor yi
 * @data 2023/2/10 itime
 * @Description
 */
//初始
//public class Multithreading extends Thread{
//    BlockingQueue<String> msgQueue = getMsgQueue();
//    public void run() {
//        System.out.println("------------------处理线程正在运行------------------");
//        try {
//            while (true) {
//                if (msgQueue.size() > 0) {
//                    msgQueue.take();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
public class Multithreading extends Thread {
    BlockingQueue<String> msgQueue = getMsgQueue();

    public void run() {
        System.out.println("------------------处理线程正在运行------------------");
        try {
            String message;
            while (!Thread.currentThread().isInterrupted()) {
                message = msgQueue.poll();
                if (message != null) {
                    processMessage(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processMessage(String message) {
        // 这里写处理消息的逻辑
    }
}