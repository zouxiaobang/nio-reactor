package com.demo.reactor.impl;

import com.demo.channel.NioReactorChannel;
import com.demo.channel.ReactorChannel;
import com.demo.handler.chain.FilterChain;
import com.demo.handler.thread.ThreadExecutor;
import com.demo.handler.thread.ThreadExecutorFactory;
import com.demo.reactor.EventTypeFactory;
import com.demo.reactor.ReactorThread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.FutureTask;

/**
 * @author zouxiaobang
 * @date 2021/1/22
 * 采用jdk的nio实现
 */
public abstract class NonBlockedReactorThread extends Thread implements ReactorThread {
    private static final int DEFAULT_THREAD_COUNT = 1;
    private String threadName;
    private final ThreadExecutor threadExecutor;
    private boolean isRunning;
    private final Selector selector;
    private FilterChain filterChain;
    private final Queue<Runnable> registerTasks = new LinkedList<>();

    public NonBlockedReactorThread() throws IOException {
        this(DEFAULT_THREAD_COUNT);
    }

    public NonBlockedReactorThread(int ioThreadCount) throws IOException {
        if (ioThreadCount <= 0) {
            ioThreadCount = DEFAULT_THREAD_COUNT;
        }
        threadExecutor = ThreadExecutorFactory.createThreadExecutor(ioThreadCount);
        selector = Selector.open();
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public String getThreadName() {
        return threadName;
    }

    @Override
    public void doStart() {
        if (!isRunning) {
            isRunning = true;
            start();
        }
    }

    @Override
    public void doStop() {
        if (isRunning) {
            isRunning = false;
        }
    }

    @Override
    public void register(int port) throws IOException {
        ServerSocketChannel selectableChannel = ServerSocketChannel.open();
        selectableChannel.configureBlocking(false);
        selectableChannel.bind(new InetSocketAddress(port));
        buildRegisterTask(selectableChannel, SelectionKey.OP_ACCEPT);
    }

    @Override
    public void run() {
        while (isRunning) {
            runRegisterTask();
            runListenEvent();
        }
    }

    @Override
    public void onAccepted(ReactorChannel channel) throws IOException {
        NioReactorChannel nioReactorChannel = toNioChannel(channel);
        SelectableChannel selectableChannel = nioReactorChannel.getSelectableChannel();
        if (selectableChannel != null) {
            accepted(selectableChannel);
        }
    }

    @Override
    public void onRead(ReactorChannel channel) {
        threadExecutor.handle(() -> {
            try {
                channel.getIoer().read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void onWritten(ReactorChannel channel) {
        threadExecutor.handle(() -> {
            try {
                channel.getIoer().write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setFilterChain(FilterChain filterChain) {
        this.filterChain = filterChain;
    }

    private void runRegisterTask() {
        Runnable registerTask;
        while ((registerTask = registerTasks.poll()) != null) {
            registerTask.run();
        }
    }

    private void runListenEvent() {
        try {
            selector.select(1000);
            handleEvents();
            selector.selectNow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private NioReactorChannel toNioChannel(ReactorChannel channel) {
        if (channel instanceof NioReactorChannel) {
            return (NioReactorChannel) channel;
        }
        throw new IllegalArgumentException("NonBlockedReactorThread实现只能采用NioReactorChannel");
    }

    private void buildRegisterTask(SelectableChannel selectableChannel, int interestOps) {
        FutureTask<SelectionKey> registerTask = new FutureTask<>(() -> {
            SelectionKey selectionKey = selectableChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.interestOps(interestOps);
            return selectionKey;
        });
        registerTasks.add(registerTask);
    }

    private void handleEvents() {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()) {
            SelectionKey selectionKey = iterator.next();
            iterator.remove();
            dispatchEvent(selectionKey);
        }
    }

    private void dispatchEvent(SelectionKey selectionKey) {
        if (selectionKey.isAcceptable() || selectionKey.isReadable() || selectionKey.isWritable()) {
            try {
                SelectableChannel selectableChannel = selectionKey.channel();
                selectableChannel.configureBlocking(false);
                NioReactorChannel nioReactorChannel = NioReactorChannel.ofChannel(selectableChannel).filterChain(filterChain);
                nioReactorChannel.setEventType(EventTypeFactory.getBy(selectionKey));
                dispatchEvent(nioReactorChannel);
                cancelListening(selectionKey, selectableChannel);
            } catch (IOException e) {
                selectionKey.cancel();
            }
        }
    }

    private void cancelListening(SelectionKey selectionKey, SelectableChannel selectableChannel) {
        if (!selectableChannel.isOpen()) {
            selectionKey.cancel();
        }
    }

    private void accepted(SelectableChannel selectableChannel) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectableChannel;
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        buildRegisterTask(socketChannel, SelectionKey.OP_READ | SelectionKey.OP_WRITE);
    }
}
