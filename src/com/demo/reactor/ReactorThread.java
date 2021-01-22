package com.demo.reactor;

import com.demo.channel.ReactorChannel;

import java.nio.channels.SelectionKey;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface ReactorThread {
    String getThreadName();

    void doStart();

    void doStop();

    void register(ReactorChannel channel, int interestOperation);

    void dispatchByKey(SelectionKey key, ReactorChannel channel);
}
