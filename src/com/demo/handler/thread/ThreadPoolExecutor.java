package com.demo.handler.thread;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class ThreadPoolExecutor extends AbsThreadExecutor {
    public ThreadPoolExecutor(int threadCount) {
        super(threadCount);
    }

    @Override
    public void handle(Runnable runnable) {

    }
}
