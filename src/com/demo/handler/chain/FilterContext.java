package com.demo.handler.chain;

import com.demo.channel.ReactorChannel;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class FilterContext {
    public final static int IN_BOUND = 1;
    public final static int OUT_BOUND = 2;

    private Object data;
    private int bound;
    private ReactorChannel reactorChannel;

    public int getBound() {
        return bound;
    }

    public void setBound(int bound) {
        this.bound = bound;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ReactorChannel getReactorChannel() {
        return reactorChannel;
    }

    public void setReactorChannel(ReactorChannel reactorChannel) {
        this.reactorChannel = reactorChannel;
    }
}
