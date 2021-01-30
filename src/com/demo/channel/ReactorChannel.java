package com.demo.channel;

import com.demo.reactor.EventType;

import java.nio.channels.SelectableChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface ReactorChannel {
    SelectableChannel getSelectableChannel();

    EventType getEventType();

    Ioer getIoer();
}
