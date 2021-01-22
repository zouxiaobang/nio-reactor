package com.demo.handler.chain;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface FilterChain {
    void filter(FilterContext context);
}
