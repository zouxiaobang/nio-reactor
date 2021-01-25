package com.demo.handler.chain.impl;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.FilterProcessor;
import com.demo.handler.chain.checker.Checker;
import com.demo.handler.chain.checker.CheckerFactory;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class CheckFilterProcessor implements FilterProcessor {
    @Override
    public void filter(FilterContext context, FilterChain chain) throws Exception {
        Checker checker = CheckerFactory.getCheckerByBound(context.getBound());
        checker.check(chain.getFilterProcessors());
        chain.filter(context);
    }
}
