package com.demo.handler.thread;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class ThreadExecutorFactory {
    public static ThreadExecutor createThreadExecutor(int threadCount) {
        if (threadCount <= 1) {
            return new SingleThreadExecutor();
        } else {
            return new ThreadPoolExecutor(threadCount);
        }
    }
}
