package com.lt.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @aythor yi
 * @data 2023/2/11 itime
 * @Description
 */
public class blockingQueue {
    // 堵塞队列用来存放读到的数据
    public static BlockingQueue<String> getMsgQueue() {
        return new LinkedBlockingQueue<String>();
    }
}
