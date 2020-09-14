package com.ray.study.smaple.sb.schedule.quartz.util.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * description
 *
 * @author r.shi 2020/09/10 13:48
 */
public class ThreadPoolExecutorHelper {

    private static final ThreadPoolExecutor EXECUTOR =  new ThreadPoolExecutor(
            100,
            50,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(500));

    public static ExecutorService getThreadPool() {
        return EXECUTOR;
    }
}
