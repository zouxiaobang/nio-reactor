package com.demo.handler.encode;

import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class StringEncoder extends AbsEncoder<String> {
    @Override
    public ByteBuffer encode(Translator<ByteBuffer, String> translator, String data) {
        return null;
    }
}
