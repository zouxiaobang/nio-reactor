package com.demo.handler.chain;

import java.nio.ByteBuffer;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class FilterContext {
    private ByteBuffer originalData;


    public ByteBuffer getOriginalData() {
        return originalData;
    }

    public void setOriginalData(ByteBuffer originalData) {
        this.originalData = originalData;
    }
}
