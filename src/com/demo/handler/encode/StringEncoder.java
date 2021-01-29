package com.demo.handler.encode;

import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class StringEncoder extends AbsEncoder<String> {
    public StringEncoder(Translator<String, ByteBuffer> translator) {
        super(translator);
    }

    @Override
    public ByteBuffer encode(Translator<String, ByteBuffer> translator, String data) {
        return translator.translate(data);
    }
}
