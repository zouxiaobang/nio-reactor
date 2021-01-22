package com.demo.channel;

import com.demo.reactor.ReactorThread;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public abstract class NonBlockedReactorThread extends Thread implements ReactorThread {

    @Override
    public String getThreadName() {
        return null;
    }

    @Override
    public void doStart() {

    }

    @Override
    public void doStop() {

    }

    @Override
    public void register(ReactorChannel channel, int interestOperation) {

    }

    @Override
    public void run() {
        super.run();
    }
}
