package com.demo.handler;

import com.demo.channel.ReactorChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class TestStringHandler extends AbsHandler<String> {
    @Override
    public void handle(ReactorChannel channel, String data) {
        System.out.println("接收到数据: " + data);
    }

    @Override
    public Class<String> getEntityClass() {
        return String.class;
    }
}
