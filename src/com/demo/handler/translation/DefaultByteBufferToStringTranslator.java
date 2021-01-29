package com.demo.handler.translation;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class DefaultByteBufferToStringTranslator implements Translator<ByteBuffer, String> {
    @Override
    public String translate(ByteBuffer data) {
        byte[] buffer = new byte[data.limit()];
        data.get(buffer);
        return new String(buffer);
    }
}