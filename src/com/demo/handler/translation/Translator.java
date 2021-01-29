package com.demo.handler.translation;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface Translator<L, N> {
    N translate(L data);
}
