package com.demo.reactor.impl;

import com.demo.channel.ReactorChannel;
import com.demo.handler.pipeline.ReactorPipeline;
import com.demo.reactor.DefaultReactors;
import com.demo.reactor.ReactorThread;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 */
public class MultipleReactor {
    private static final int DEFAULT_MAIN_COUNT = 1;
    private static final int DEFAULT_SUB_COUNT = 4;
    private ReactorThread[] mainReactorThreads;
    private ReactorThread[] subReactorThreads;
    private final DefaultReactors reactors;
    private int ioThreadsCount;
    private ReactorPipeline inPipeline;
    private ReactorPipeline outPipeline;

    public MultipleReactor() {
        this(0);
    }

    public MultipleReactor(int ioThreadsCount) {
        this.ioThreadsCount = ioThreadsCount;
        reactors = new DefaultReactors();
    }

    public void setIoThreadsCount(int ioThreadsCount) {
        this.ioThreadsCount = ioThreadsCount;
    }

    public void group() throws IOException {
        group(DEFAULT_MAIN_COUNT, DEFAULT_SUB_COUNT);
    }

    public void setReactorPipeline(ReactorPipeline inPipeline, ReactorPipeline outPipeline) {
        this.inPipeline = inPipeline;
        this.outPipeline = outPipeline;
    }

    public void group(int mainCount, int subCount) throws IOException {
        mainCount = mainCount <= 0 ? DEFAULT_MAIN_COUNT : mainCount;
        subCount = subCount <= 0 ? DEFAULT_SUB_COUNT : subCount;
        mainReactorThreads = new ReactorThread[mainCount];
        subReactorThreads = new ReactorThread[subCount];
        // 先实例化子Reactor
        buildSubReactorThreads(subCount);
        buildMainReactorThreads(mainCount);
    }

    private void buildMainReactorThreads(int mainCount) throws IOException {
        for (int i = 0; i < mainCount; i++) {
            mainReactorThreads[i] = new MainReactorThread();
        }
    }

    private void buildSubReactorThreads(int subCount) throws IOException {
        for (int i = 0; i < subCount; i++) {
            subReactorThreads[i] = new SubReactorThread(ioThreadsCount);
            ((NonBlockedReactorThread)subReactorThreads[i]).setReactorPipeline(inPipeline, outPipeline);
            subReactorThreads[i].doStart();
        }
    }

    class SubReactorThread extends NonBlockedReactorThread {
        public SubReactorThread(int ioThreadsCount) throws IOException {
            super(ioThreadsCount);
        }

        @Override
        public void dispatchEvent(ReactorChannel channel) throws IOException {
            reactors.handleIo(this, channel);
        }
    }

    class MainReactorThread extends NonBlockedReactorThread {
        AtomicInteger count = new AtomicInteger(0);

        public MainReactorThread() throws IOException {
        }

        @Override
        public void dispatchEvent(ReactorChannel channel) throws IOException {
            int index = count.incrementAndGet() % subReactorThreads.length;
            reactors.accept(subReactorThreads[index], channel);
        }
    }

    /**
     *
     * @param mainIndex 代表要启动的主Reactor的下标
     *                  负数代表全部启动
     */
    public void bind(int mainIndex, int port) throws IOException {
        if (isGroupNotExists()) {
            group();
        }
        if (mainIndex >= mainReactorThreads.length) {
            throw new IndexOutOfBoundsException("mainIndex不能超过总数");
        }
        if (mainIndex > 0) {
            ReactorThread mainReactorThread = mainReactorThreads[mainIndex];
            if (mainReactorThread != null) {
                mainReactorThread.doStart();
                mainReactorThread.register(port);
            }
            return;
        }
        for (ReactorThread mainReactorThread : mainReactorThreads) {
            if (mainReactorThread != null) {
                mainReactorThread.doStart();
                mainReactorThread.register(port);
            }
        }
    }

    /**
     *
     * @param mainIndex 代表要关闭的主Reactor的下标
     *                  负数代表全部启动
     */
    public void stop(int mainIndex) {
        if (isGroupNotExists()) {
            return;
        }
        if (mainIndex >= mainReactorThreads.length) {
            throw new IndexOutOfBoundsException("mainIndex不能超过总数");
        }
        if (mainIndex > 0) {
            ReactorThread mainReactorThread = mainReactorThreads[mainIndex];
            if (mainReactorThread != null) {
                mainReactorThread.doStop();
            }
            return;
        }
        for (ReactorThread mainReactorThread : mainReactorThreads) {
            if (mainReactorThread != null) {
                mainReactorThread.doStop();
            }
        }
    }

    private boolean isGroupNotExists() {
        if (mainReactorThreads == null) {
            return true;
        }
        return subReactorThreads == null;
    }
}
