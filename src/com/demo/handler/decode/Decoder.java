package com.demo.handler.decode;

import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface Decoder<T> {
    T decode(Translator<ByteBuffer, T> translator, ByteBuffer data);
}
