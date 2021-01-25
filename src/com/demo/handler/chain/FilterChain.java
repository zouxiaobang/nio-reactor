package com.demo.handler.chain;

import java.util.List;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public interface FilterChain {
    void filter(FilterContext context) throws Exception;

    List<FilterProcessor> getFilterProcessors();
}
