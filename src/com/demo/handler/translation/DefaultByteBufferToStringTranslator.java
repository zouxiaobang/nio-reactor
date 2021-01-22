package com.demo.handler.translation;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class DefaultByteBufferToStringTranslator implements Translator<ByteBuffer, String> {
    @Override
    public ByteBuffer getLastType() {
        return null;
    }

    @Override
    public String translate(ByteBuffer data) {
        return null;
    }
}