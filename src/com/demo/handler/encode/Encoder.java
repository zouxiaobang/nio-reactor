package com.demo.handler.encode;

import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface Encoder<T> {
    ByteBuffer encode(Translator<T, ByteBuffer> translator, T data);
}