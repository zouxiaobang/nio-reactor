package com.demo.handler.decode;

import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class StringDecoder extends AbsDecoder<String> {
    @Override
    public String decode(Translator<String, ByteBuffer> translator, ByteBuffer data) {
        return null;
    }

}
