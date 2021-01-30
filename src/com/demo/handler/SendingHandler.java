package com.demo.handler;

import com.demo.channel.ReactorChannel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class SendingHandler extends AbsHandler<ByteBuffer> {
    @Override
    public void handle(ReactorChannel channel, ByteBuffer data) throws IOException {
        SocketChannel socketChannel = (SocketChannel) channel.getSelectableChannel();
        if (socketChannel != null) {
            socketChannel.write(data);
        }
    }

    @Override
    public Class<ByteBuffer> getEntityClass() {
        return ByteBuffer.class;
    }
}
