package com.demo.channel;

import com.demo.reactor.EventType;

import java.io.IOException;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface ReactorChannel {

    EventType getEventType();

    void read() throws IOException;

    void write();
}
