package com.demo.handler.chain.impl;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.FilterProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 * 使用ArrayList装载处理器
 */
public class DefaultFilterChain implements FilterChain {
    private final List<FilterProcessor> processors;
    private int currentPosition = 0;

    private DefaultFilterChain(List<FilterProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void filter(FilterContext context) {
        if (currentPosition == processors.size()) {
            return;
        }
        ++ currentPosition;
        FilterProcessor processor = processors.get(currentPosition - 1);
        processor.filter(context, this);
    }


    static class Builder {
        private final List<FilterProcessor> processors = new ArrayList<>();

        public Builder() {
            processors.add(new CheckFilterProcessor());
        }

        public Builder addProcessor(FilterProcessor newProcessor) {
            processors.add(newProcessor);
            return this;
        }

        public Builder addProcessors(FilterProcessor... newProcessors) {
            processors.addAll(Arrays.asList(newProcessors));
            return this;
        }

        public DefaultFilterChain build() {
            return new DefaultFilterChain(processors);
        }
    }
}
