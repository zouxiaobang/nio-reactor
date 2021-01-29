package com.demo.handler.decode;

import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class StringDecoder extends AbsDecoder<String> {
    public StringDecoder(Translator<ByteBuffer, String> translator) {
        super(translator);
    }

    @Override
    public String decode(Translator<ByteBuffer, String> translator, ByteBuffer data) {
        return translator.translate(data);
    }

}
