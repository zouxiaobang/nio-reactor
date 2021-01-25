package com.demo.channel;

import com.demo.handler.chain.FilterChain;
import com.demo.reactor.EventType;

import java.io.IOException;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface ReactorChannel {

    FilterChain getFilterChain();

    EventType getEventType();

    Ioer getIoer();
}
