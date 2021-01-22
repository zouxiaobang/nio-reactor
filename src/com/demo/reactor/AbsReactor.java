package com.demo.reactor;

import com.demo.channel.ReactorChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public abstract class AbsReactor implements Reactor {
    @Override
    public void accept(ReactorThread subReactorThread, ReactorChannel channel) {

    }

    @Override
    public void handleIo(ReactorThread reactorThread, ReactorChannel channel) {

    }

    private void read() {

    }

    private void write() {
        // write to buff -> ReactorChannel write()
    }
}
