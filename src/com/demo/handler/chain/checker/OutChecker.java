package com.demo.handler.chain.checker;

import com.demo.handler.chain.FilterProcessor;
import com.demo.handler.encode.Encoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xb.zou
 * @date 2021/1/25
 * @option
 */
public class OutChecker implements Checker {
    @Override
    public void check(List<FilterProcessor> processors) throws Exception {
        List<FilterProcessor> encoder = processors.stream().filter(processor -> processor instanceof Encoder).collect(Collectors.toList());

        if (encoder.size() > 1) {
            throw new IllegalArgumentException("不支持包含多个转换器");
        }
    }
}
