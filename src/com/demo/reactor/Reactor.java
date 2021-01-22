package com.demo.reactor;

import com.demo.channel.ReactorChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface Reactor {
    void accept(ReactorThread reactorThread, ReactorChannel channel);

    void handleIo(ReactorThread reactorThread, ReactorChannel channel);
}
