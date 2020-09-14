package com.ray.study.smaple.sb.schedule.quartz.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolHelper
 *
 * @author qlgu
 * @date 2019/12/9 13:58
 **/
public class ThreadPoolHelper {

    public static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    public static final int MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2 + 10;
    public static final long WAITING_MILLISECONDS = 100L;
    public static final int BLOCKING_QUEUE_SIZE = 1000;

    private static volatile ExecutorService threadPool = null;

    static {
        if (threadPool == null) {
            synchronized (ThreadPoolHelper.class) {
                if (threadPool == null) {
                    BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE);
                    threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, WAITING_MILLISECONDS, TimeUnit.MILLISECONDS, blockingQueue);
                }
            }
        }
    }

    /**
     * @return
     */
    public static ExecutorService getThreadPool() {
        return threadPool;
    }
}
