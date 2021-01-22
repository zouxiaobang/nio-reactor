package com.demo.channel;

import com.demo.reactor.EventType;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface ReactorChannel {

    EventType getEventType();

    void read();

    void write();
}
