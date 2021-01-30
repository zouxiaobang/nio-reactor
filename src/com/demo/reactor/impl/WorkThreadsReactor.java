package com.demo.reactor.impl;

import com.demo.channel.ReactorChannel;
import com.demo.handler.pipeline.ReactorPipeline;
import com.demo.reactor.DefaultReactors;
import com.demo.reactor.EventType;

import java.io.IOException;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class WorkThreadsReactor {
    private final NonBlockedReactorThread reactorThread;
    private final DefaultReactors reactors;

    public WorkThreadsReactor() throws IOException {
        this(0);
    }

    public WorkThreadsReactor(int ioThreadsCount) throws IOException {
        reactorThread = new WorkerThreadsReactorThread(ioThreadsCount);
        reactors = new DefaultReactors();
    }

    public void bind(int port) throws IOException {
        if (reactorThread != null) {
            reactorThread.register(port);
            reactorThread.doStart();
        }
    }

    public void stop() {
        if (reactorThread != null) {
            reactorThread.doStop();
        }
    }

    public void setReactorPipeline(ReactorPipeline inPipeline, ReactorPipeline outPipeline) {
        reactorThread.setReactorPipeline(inPipeline, outPipeline);
    }

    class WorkerThreadsReactorThread extends NonBlockedReactorThread {
        public WorkerThreadsReactorThread(int ioThreadsCount) throws IOException {
            super(ioThreadsCount);
        }

        @Override
        public void dispatchEvent(ReactorChannel channel) throws IOException {
            EventType eventType = channel.getEventType();
            if (eventType == EventType.ACCEPT) {
                reactors.accept(this, channel);
            } else if (eventType == EventType.READ) {
                reactors.handleIo(this, channel);
            }
        }
    }
}
