package com.demo.handler.encode;

import com.demo.handler.chain.FilterChain;
import com.demo.handler.chain.FilterContext;
import com.demo.handler.chain.FilterProcessor;
import com.demo.handler.translation.Translator;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public abstract class AbsEncoder<T> implements Encoder<T>, FilterProcessor {
    private final Translator<T, ByteBuffer> translator;

    public AbsEncoder(Translator<T, ByteBuffer> translator) {
        this.translator = translator;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void filter(FilterContext context, FilterChain chain) throws Exception {
        if (context.getBound() != FilterContext.OUT_BOUND) {
            throw new IllegalArgumentException("数据流动方向异常。");
        }
        ByteBuffer byteBuffer = encode(translator, (T) context.getData());
        context.setData(byteBuffer);
        chain.filter(context);
    }
}
