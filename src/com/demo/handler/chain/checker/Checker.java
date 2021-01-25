package com.demo.handler.chain.checker;

import com.demo.handler.chain.FilterProcessor;

import java.util.List;

/**
 * @author xb.zou
 * @date 2021/1/25
 * @option
 */
public interface Checker {
    void check(List<FilterProcessor> processors) throws Exception;
}
