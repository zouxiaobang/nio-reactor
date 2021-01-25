package com.demo.channel;

import java.io.IOException;

/**
 * @author xb.zou
 * @date 2021/1/25
 * @option
 */
public interface Ioer {
    void read() throws IOException;

    void write() throws IOException;
}
