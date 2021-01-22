package com.demo.handler.encode;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.FilterProcessor;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public abstract class AbsEncoder<T> implements Encoder<T>, FilterProcessor {
    @Override
    public void filter(FilterContext context, FilterChain chain) {
        // TODO set the encode() data to context's response
    }
}
