package com.demo.handler;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.FilterProcessor;

/**
 * @author zouxiaobang
 * @date 2021/1/27
 */
public abstract class AbsHandler<T> implements Handler<T>, FilterProcessor {
    @Override
    public void filter(FilterContext context, FilterChain chain) throws Exception {
        handle(context.getReactorChannel(), (T) context.getData());
        chain.filter(context);
    }
}
