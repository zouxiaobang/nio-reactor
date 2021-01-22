package com.demo.handler;

import com.demo.channel.ReactorChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class TestStringHandler implements Handler<String> {
    @Override
    public void handle(ReactorChannel channel, String data) {

    }

    @Override
    public Class<String> getEntityClass() {
        return String.class;
    }
}
