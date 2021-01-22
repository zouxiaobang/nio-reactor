package com.demo.handler.decode;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.FilterProcessor;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public abstract class AbsDecoder<T> implements Decoder<T>, FilterProcessor {
    @Override
    public void filter(FilterContext context, FilterChain chain) {
        // TODO set the decode() data to context's request
    }
}
