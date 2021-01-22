package com.demo.handler.thread;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class SingleThreadExecutor extends AbsThreadExecutor {
    protected SingleThreadExecutor() {
        super(1);
    }

    @Override
    public void handle(Runnable runnable) {

    }
}
