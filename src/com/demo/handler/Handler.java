package com.demo.handler;

import com.demo.channel.ReactorChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface Handler<T> {
    void handle(ReactorChannel channel, T data);

    Class<T> getEntityClass();
}
