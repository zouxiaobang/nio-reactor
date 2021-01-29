package com.demo;

import com.demo.handler.TestStringHandler;
import com.demo.handler.chain.impl.DefaultFilterChain;
import com.demo.handler.decode.StringDecoder;
import com.demo.handler.translation.DefaultByteBufferToStringTranslator;
import com.demo.reactor.DefaultReactors;
import com.demo.reactor.impl.WorkThreadsReactor;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        testWorkThreadsReactor();
    }

    private static void testWorkThreadsReactor() throws IOException {
        WorkThreadsReactor workThreadsReactor = new WorkThreadsReactor();
        DefaultFilterChain filterChain = new DefaultFilterChain.Builder()
                .addProcessor(new StringDecoder(new DefaultByteBufferToStringTranslator()))
                .addProcessors(new TestStringHandler())
                .build();
        workThreadsReactor.setFilterChain(filterChain);
        workThreadsReactor.bind(8090);
    }

    private static void testMultipleReactor() throws IOException {

    }
}
