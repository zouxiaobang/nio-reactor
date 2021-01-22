package com.demo.handler.translation;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface Translator<L, N> {
    L getLastType();

    N translate(L data);
}
