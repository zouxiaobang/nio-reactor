package com.demo.handler.translation;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class DefaultStringToByteBufferTranslator implements Translator<String, ByteBuffer> {
    @Override
    public ByteBuffer translate(String data) {
        return ByteBuffer.wrap(data.getBytes(StandardCharsets.UTF_8));
    }
}
