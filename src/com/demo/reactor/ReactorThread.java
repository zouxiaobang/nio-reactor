package com.demo.reactor;

import com.demo.channel.ReactorChannel;

import java.io.IOException;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface ReactorThread {
    String getThreadName();

    void doStart();

    void doStop();

    void register(int port) throws IOException;

    void dispatchEvent(ReactorChannel channel) throws IOException;

    void onAccepted(ReactorChannel channel) throws IOException;

    void onRead(ReactorChannel channel);
}
