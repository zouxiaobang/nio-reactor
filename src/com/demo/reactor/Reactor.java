package com.demo.reactor;

import com.demo.channel.ReactorChannel;

import java.io.IOException;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface Reactor {
    void accept(ReactorThread subReactorThread, ReactorChannel channel) throws IOException;

    void handleIo(ReactorThread reactorThread, ReactorChannel channel);
}
