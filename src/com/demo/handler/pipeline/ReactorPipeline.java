package com.demo.handler.pipeline;

import com.demo.handler.chain.FilterProcessor;

import java.util.List;

/**
 * @author zouxiaobang
 * @date 2021/1/30
 */
@FunctionalInterface
public interface ReactorPipeline {
    List<FilterProcessor> getProcessors();
}
