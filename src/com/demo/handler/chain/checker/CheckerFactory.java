package com.demo.handler.chain.checker;

import com.demo.handler.chain.FilterContext;

/**
 * @author xb.zou
 * @date 2021/1/25
 * @option
 */
public class CheckerFactory {
    public static Checker getCheckerByBound(int bound) {
        if (bound == FilterContext.IN_BOUND) {
            return new InChecker();
        } else {
            return new OutChecker();
        }
    }
}
