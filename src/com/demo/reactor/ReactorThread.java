package com.demo.reactor;

import com.demo.channel.ReactorChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface ReactorThread {
    String getThreadName();

    void doStart();

    void doStop();

    void register();

    void dispatchByKey(ReactorChannel channel);

    void onAccepted(ReactorChannel channel);

    void onRead(ReactorChannel channel);

    void onWritten(ReactorChannel channel);
}
