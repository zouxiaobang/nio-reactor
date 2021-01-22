package com.demo.channel;

import com.demo.reactor.EventType;

import java.nio.channels.SelectableChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class NioReactorChannel implements ReactorChannel {
    private final SelectableChannel selectableChannel;

    private NioReactorChannel(SelectableChannel selectableChannel) {
        this.selectableChannel = selectableChannel;
    }

    public static NioReactorChannel ofChannel(SelectableChannel selectableChannel) {
        return new NioReactorChannel(selectableChannel);
    }

    @Override
    public EventType getEventType() {
        return null;
    }

    @Override
    public void read() {

    }

    @Override
    public void write() {

    }
}
