package com.demo.channel;

import java.io.IOException;

/**
 * @author xb.zou
 * @date 2021/1/25
 * @option
 */
public interface Ioer<T> {
    void read() throws IOException;

    void write(T data) throws Exception;
}
