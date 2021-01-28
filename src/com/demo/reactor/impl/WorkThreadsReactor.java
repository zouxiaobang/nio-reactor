package com.demo.reactor.impl;

import com.demo.channel.ReactorChannel;
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

    class WorkerThreadsReactorThread extends NonBlockedReactorThread {
        public WorkerThreadsReactorThread() throws IOException {
            super();
        }

        public WorkerThreadsReactorThread(int ioThreadsCount) throws IOException {
            super(ioThreadsCount);
        }

        @Override
        public void dispatchEvent(ReactorChannel channel) throws IOException {
            EventType eventType = channel.getEventType();
            if (eventType == EventType.ACCEPT) {
                reactors.accept(this, channel);
            } else if (eventType == EventType.READ || eventType == EventType.WRITE) {
                reactors.handleIo(this, channel);
            }
        }
    }
}
