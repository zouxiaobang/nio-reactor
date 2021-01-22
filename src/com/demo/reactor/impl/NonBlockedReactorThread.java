package com.demo.reactor.impl;

import com.demo.channel.ReactorChannel;
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
    public void register() {

    }

    @Override
    public void onAccepted(ReactorChannel channel) {

    }

    @Override
    public void onRead(ReactorChannel channel) {

    }

    @Override
    public void onWritten(ReactorChannel channel) {

    }

    @Override
    public void run() {
        super.run();
    }
}
