package com.demo.reactor;

import java.nio.channels.SelectionKey;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class EventTypeFactory {
    public static EventType getBy(SelectionKey selectionKey) {
        if (selectionKey.isAcceptable()) {
            return EventType.ACCEPT;
        } else if (selectionKey.isReadable()) {
            return EventType.READ;
        } else if (selectionKey.isWritable()) {
            return EventType.WRITE;
        }

        return EventType.DEFAULT;
    }
}
