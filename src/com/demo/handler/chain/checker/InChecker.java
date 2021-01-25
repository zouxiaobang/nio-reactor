package com.demo.handler.chain.checker;

import com.demo.handler.chain.FilterProcessor;
import com.demo.handler.decode.Decoder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xb.zou
 * @date 2021/1/25
 * @option
 */
public class InChecker implements Checker {
    @Override
    public void check(List<FilterProcessor> processors) throws Exception {
        List<FilterProcessor> decoder = processors.stream().filter(processor -> processor instanceof Decoder).collect(Collectors.toList());

        // 这里有漏洞，例如包含3个转换器：
        // Decoder(ByteBuffer -> String) - Encoder(String -> ByteBuffer) - Decoder(ByteBuffer -> String)
        // 类似ABA，只是没有任何意义
        if (decoder.size() > 1) {
            throw new IllegalArgumentException("不支持包含多个转换器");
        }
    }
}
