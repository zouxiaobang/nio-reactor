package com.demo.handler;

import com.demo.channel.ReactorChannel;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class ByteBufferHandler extends AbsHandler<ByteBuffer> {
    @Override
    public void handle(ReactorChannel channel, ByteBuffer data) {

    }

    @Override
    public Class<ByteBuffer> getEntityClass() {
        return ByteBuffer.class;
    }
}
