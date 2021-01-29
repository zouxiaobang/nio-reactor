package com.demo.handler.decode;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.FilterProcessor;
import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public abstract class AbsDecoder<T> implements Decoder<T>, FilterProcessor {
    private final Translator<ByteBuffer, T> translator;

    public AbsDecoder(Translator<ByteBuffer, T> translator) {
        this.translator = translator;
    }

    @Override
    public void filter(FilterContext context, FilterChain chain) throws Exception {
        if (context.getBound() != FilterContext.IN_BOUND) {
            throw new IllegalArgumentException("数据流动方向异常。");
        }
        T decode = decode(translator, (ByteBuffer) context.getData());
        context.setData(decode);
        chain.filter(context);
    }
}
