package com.demo.handler.thread;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public abstract class AbsThreadExecutor implements ThreadExecutor {
    protected int threadCount;

    protected AbsThreadExecutor(int threadCount) {
        this.threadCount = threadCount;
    }

}
