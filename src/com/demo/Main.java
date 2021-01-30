package com.demo;

import com.demo.handler.TestStringHandler;
import com.demo.handler.decode.StringDecoder;
import com.demo.handler.translation.DefaultByteBufferToStringTranslator;
import com.demo.reactor.impl.WorkThreadsReactor;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        testWorkThreadsReactor();
    }

    private static void testWorkThreadsReactor() throws IOException {
        WorkThreadsReactor workThreadsReactor = new WorkThreadsReactor();
        workThreadsReactor.setReactorPipeline(() -> Arrays.asList(new StringDecoder(new DefaultByteBufferToStringTranslator()), new TestStringHandler()));
        workThreadsReactor.bind(8090);
    }

    private static void testMultipleReactor() throws IOException {

    }
}
